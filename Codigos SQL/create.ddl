CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE workstations (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    location TEXT NOT NULL
);

CREATE TABLE reservations (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    cancelled BOOLEAN NOT NULL,
    workstation_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_workstation FOREIGN KEY (workstation_id) REFERENCES workstations (id)
);

CREATE TABLE sensor_readings (
    id BIGINT PRIMARY KEY,
    workstation_id BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    occupied BOOLEAN NOT NULL,
    temperature_c NUMERIC NOT NULL,
    noise_db SMALLINT NOT NULL,
    CONSTRAINT fk_workstation_sensor FOREIGN KEY (workstation_id) REFERENCES workstations (id)
);