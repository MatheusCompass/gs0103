-- Consulta 1: Listar todos os usuários cadastrados
SELECT * FROM usuarios;

-- Consulta 2: Buscar usuário específico por email
SELECT * FROM usuarios 
WHERE email = 'exemplo@email.com';

-- Consulta 3: Listar dispositivos de um usuário específico
SELECT d.* 
FROM dispositivos d
INNER JOIN usuarios u ON d.usuario_id = u.id
WHERE u.email = 'exemplo@email.com';

-- Consulta 4: Consumo total de energia por dispositivo no último mês
SELECT 
    d.nome_dispositivo,
    SUM(c.consumo_kwh) AS consumo_total,
    AVG(c.consumo_kwh) AS consumo_medio
FROM consumo c
INNER JOIN dispositivos d ON c.dispositivo_id = d.id
WHERE c.data_leitura >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
GROUP BY d.id, d.nome_dispositivo
ORDER BY consumo_total DESC;

-- Consulta 5: Consumo diário de energia de um usuário
SELECT 
    DATE(c.data_leitura) AS data,
    SUM(c.consumo_kwh) AS consumo_diario
FROM consumo c
INNER JOIN dispositivos d ON c.dispositivo_id = d.id
WHERE d.usuario_id = 1
GROUP BY DATE(c.data_leitura)
ORDER BY data DESC;

-- Consulta 6: Dispositivos com maior consumo médio
SELECT 
    d.nome_dispositivo,
    d.tipo_dispositivo,
    AVG(c.consumo_kwh) AS consumo_medio
FROM dispositivos d
LEFT JOIN consumo c ON d.id = c.dispositivo_id
GROUP BY d.id
HAVING consumo_medio > 0
ORDER BY consumo_medio DESC
LIMIT 5;

-- Consulta 7: Verificar alertas de consumo acima do esperado
SELECT 
    u.nome,
    d.nome_dispositivo,
    c.consumo_kwh,
    c.data_leitura
FROM consumo c
INNER JOIN dispositivos d ON c.dispositivo_id = d.id
INNER JOIN usuarios u ON d.usuario_id = u.id
WHERE c.consumo_kwh > d.consumo_estimado * 1.2
ORDER BY c.data_leitura DESC;

-- Consulta 8: Relatório mensal de economia projetada
SELECT 
    u.nome AS usuario,
    COUNT(DISTINCT d.id) AS total_dispositivos,
    SUM(c.consumo_kwh) AS consumo_total,
    SUM(c.consumo_kwh * 0.65) AS custo_estimado_reais
FROM usuarios u
INNER JOIN dispositivos d ON u.id = d.usuario_id
INNER JOIN consumo c ON d.id = c.dispositivo_id
WHERE MONTH(c.data_leitura) = MONTH(CURDATE())
GROUP BY u.id;