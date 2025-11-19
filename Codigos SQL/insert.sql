-- Inserção de dados para simulação

-- Inserindo Usuários
INSERT INTO usuarios (nome, email, senha, tipo_usuario) VALUES
('João Silva', 'joao.silva@email.com', 'senha123', 'cliente'),
('Maria Santos', 'maria.santos@email.com', 'senha456', 'cliente'),
('Pedro Oliveira', 'pedro.oliveira@email.com', 'senha789', 'cliente'),
('Ana Costa', 'ana.costa@email.com', 'senha321', 'administrador'),
('Carlos Souza', 'carlos.souza@email.com', 'senha654', 'cliente');

-- Inserindo Dispositivos
INSERT INTO dispositivos (usuario_id, nome_dispositivo, tipo_dispositivo, localizacao, status) VALUES
(1, 'Ar Condicionado Sala', 'ar_condicionado', 'Sala de Estar', 'ativo'),
(1, 'Geladeira Cozinha', 'geladeira', 'Cozinha', 'ativo'),
(2, 'Chuveiro Elétrico', 'chuveiro', 'Banheiro', 'ativo'),
(2, 'Máquina de Lavar', 'lavadora', 'Área de Serviço', 'ativo'),
(3, 'Ar Condicionado Quarto', 'ar_condicionado', 'Quarto', 'ativo'),
(3, 'Microondas', 'microondas', 'Cozinha', 'ativo'),
(5, 'Freezer', 'freezer', 'Garagem', 'inativo');

-- Inserindo Consumo de Energia
INSERT INTO consumo_energia (dispositivo_id, data_hora, consumo_kwh, custo_estimado) VALUES
(1, '2024-01-15 08:00:00', 2.5, 1.50),
(1, '2024-01-15 14:00:00', 3.2, 1.92),
(2, '2024-01-15 10:00:00', 1.8, 1.08),
(2, '2024-01-15 18:00:00', 2.1, 1.26),
(3, '2024-01-15 07:00:00', 4.5, 2.70),
(4, '2024-01-15 20:00:00', 0.8, 0.48),
(5, '2024-01-15 19:00:00', 2.8, 1.68),
(6, '2024-01-15 12:00:00', 0.5, 0.30);

-- Inserindo Alertas
INSERT INTO alertas (dispositivo_id, tipo_alerta, mensagem, data_hora, status_alerta) VALUES
(1, 'consumo_alto', 'Consumo acima da média nas últimas 3 horas', '2024-01-15 15:00:00', 'pendente'),
(3, 'manutencao', 'Dispositivo necessita de manutenção preventiva', '2024-01-15 08:00:00', 'pendente'),
(5, 'consumo_alto', 'Pico de consumo detectado', '2024-01-15 20:00:00', 'resolvido'),
(2, 'eficiencia', 'Dispositivo operando com baixa eficiência', '2024-01-15 11:00:00', 'pendente');

-- Inserindo Recomendações
INSERT INTO recomendacoes (usuario_id, dispositivo_id, tipo_recomendacao, descricao, economia_estimada, data_geracao) VALUES
(1, 1, 'ajuste_temperatura', 'Ajustar temperatura do ar condicionado para 24°C pode economizar energia', 15.00, '2024-01-15'),
(2, 3, 'troca_equipamento', 'Considere substituir por modelo mais eficiente', 50.00, '2024-01-15'),
(3, 5, 'horario_uso', 'Utilize o dispositivo fora do horário de pico', 20.00, '2024-01-15'),
(1, 2, 'manutencao', 'Realizar limpeza das serpentinas', 10.00, '2024-01-15');

-- Inserindo Metas de Economia
INSERT INTO metas_economia (usuario_id, meta_mensal_kwh, meta_custo, data_inicio, data_fim, status_meta) VALUES
(1, 150.00, 90.00, '2024-01-01', '2024-01-31', 'em_andamento'),
(2, 200.00, 120.00, '2024-01-01', '2024-01-31', 'em_andamento'),
(3, 100.00, 60.00, '2024-01-01', '2024-01-31', 'em_andamento'),
(5, 180.00, 108.00, '2024-01-01', '2024-01-31', 'em_andamento');