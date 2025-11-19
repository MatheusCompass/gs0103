# Integração com Sensores IoT - Workhub

## Visão Geral
O sistema Workhub integra sensores IoT para monitoramento em tempo real das estações de trabalho, coletando dados de ocupação, temperatura e ruído.

## Tipos de Sensores

### 1. Sensor de Presença/Ocupação
- **Tipo:** Sensor PIR (Passive Infrared) ou Sensor de Pressão
- **Campo:** `occupied` (boolean)
- **O que mede:** Detecta se há uma pessoa utilizando a estação de trabalho
- **Frequência de leitura:** A cada 5 minutos (configurável)
- **Influência no sistema:**
  - Determina disponibilidade em tempo real das estações
  - Valida se reservas estão sendo efetivamente utilizadas
  - Alerta sobre uso não autorizado (ocupação sem reserva)
  - Gera dados para análise de utilização e otimização de espaços

### 2. Sensor de Temperatura
- **Tipo:** DHT22 ou DS18B20
- **Campo:** `temperatureC` (Double)
- **O que mede:** Temperatura ambiente em graus Celsius
- **Faixa ideal:** 20°C - 24°C
- **Influência no sistema:**
  - Monitora conforto térmico do ambiente
  - Alerta sobre condições inadequadas (muito quente ou frio)
  - Pode acionar automaticamente sistemas de climatização
  - Gera relatórios de qualidade ambiental
  - Ajuda usuários a escolherem estações mais confortáveis

### 3. Sensor de Ruído
- **Tipo:** Microfone MEMS ou Sensor de Som
- **Campo:** `noiseDb` (Integer)
- **O que mede:** Nível de ruído ambiente em decibéis (dB)
- **Faixa ideal:** 30-50 dB (ambiente de trabalho silencioso)
- **Influência no sistema:**
  - Monitora qualidade acústica do ambiente
  - Identifica áreas muito barulhentas (>60dB)
  - Ajuda na escolha de estações adequadas para trabalho focado
  - Alerta sobre violações de limites de ruído
  - Gera dados para melhoria do ambiente de trabalho

## Arquitetura de Integração

### Fluxo de Dados
```
[Sensores IoT na Estação]
        ↓
[Gateway/ESP32 com WiFi]
        ↓
[HTTP POST /sensors/readings]
        ↓
[API Workhub - SensorsController]
        ↓
[Validação e Processamento - SensorService]
        ↓
[Persistência - PostgreSQL]
        ↓
[Consulta via API - GET /sensors/last/{id}]
        ↓
[Dashboard/Frontend]
```

### Estrutura do Código

#### Model: `SensorReading.java`
```java
@Entity
@Table(name = "sensor_readings")
public class SensorReading {
    private Long id;
    private Workstations workstation;  // Estação vinculada
    private LocalDateTime timestamp;    // Momento da leitura
    private boolean occupied;           // Presença detectada
    private Double temperatureC;        // Temperatura em °C
    private Integer noiseDb;            // Ruído em dB
}
```

#### Service: `SensorService.java`
Responsável por processar e persistir as leituras dos sensores.

#### Controller: `SensorsController.java`
Expõe endpoints REST para receber e consultar dados dos sensores.

## Endpoints da API

### POST /sensors/readings
Recebe leituras dos sensores (simuladas ou reais).

**Request Body:**
```json
{
  "workstationId": 1,
  "occupied": true,
  "temperatureC": 23.5,
  "noiseDb": 48,
  "timestamp": "2025-11-19T10:30:00"
}
```

**Response:**
```json
{
  "id": 1,
  "workstationId": 1,
  "timestamp": "2025-11-19T10:30:00",
  "occupied": true,
  "temperatureC": 23.5,
  "noiseDb": 48
}
```

### GET /sensors/last/{workstationId}
Consulta a última leitura de uma estação específica.

**Response:**
```json
{
  "id": 1,
  "workstationId": 1,
  "timestamp": "2025-11-19T10:30:00",
  "occupied": true,
  "temperatureC": 23.5,
  "noiseDb": 48
}
```

## Simulação de Dados

Atualmente, os dados dos sensores são **mockados** para fins de demonstração e testes. 

### Exemplo de Payload Mockado:
```json
{
  "workstationId": 1,
  "occupied": true,
  "temperatureC": 22.5,
  "noiseDb": 45
}
```

### Dados de Teste Utilizados:
- **Ocupação:** true/false (presença detectada)
- **Temperatura:** 20.5°C - 24.5°C (faixa confortável)
- **Ruído:** 30-55 dB (ambiente silencioso a moderado)

## Evidências de Testes

Consulte os arquivos na pasta `testes_api/`:
- `teste1_post_sensor_reading.png` - Teste de envio de leitura de sensor
- `teste5_get_sensor.png` - Teste de consulta de última leitura

## Integração Futura (Produção)

Para ambiente de produção, os sensores físicos podem ser integrados usando:

### Hardware Sugerido:
- **Microcontrolador:** ESP32 ou Arduino com WiFi
- **Sensor de Presença:** HC-SR501 (PIR)
- **Sensor de Temperatura:** DHT22
- **Sensor de Ruído:** MAX4466 ou módulo de microfone

### Código de Integração (Exemplo ESP32):
```cpp
// Pseudocódigo para ESP32
void loop() {
  bool occupied = digitalRead(PIR_PIN);
  float temp = dht.readTemperature();
  int noise = analogRead(MIC_PIN);
  
  String payload = "{\"workstationId\":1,\"occupied\":" + 
                   String(occupied) + ",\"temperatureC\":" + 
                   String(temp) + ",\"noiseDb\":" + 
                   String(noise) + "}";
  
  http.POST("http://api.workhub.com/sensors/readings", payload);
  delay(300000); // 5 minutos
}
```

## Benefícios da Integração

1. **Monitoramento em Tempo Real:** Dados atualizados sobre todas as estações
2. **Otimização de Espaços:** Identificação de estações subutilizadas
3. **Conforto:** Usuários escolhem ambientes mais adequados
4. **Eficiência Energética:** Climatização baseada em ocupação real
5. **Análise de Dados:** Relatórios de utilização e qualidade ambiental
6. **Validação de Reservas:** Detecta reservas não utilizadas

## Próximos Passos

- [ ] Implementar alertas automáticos (temperatura/ruído fora do ideal)
- [ ] Dashboard em tempo real para visualização dos sensores
- [ ] Integração com hardware físico (ESP32)
- [ ] Machine Learning para prever padrões de ocupação
- [ ] API de notificações quando condições ambientais mudarem
