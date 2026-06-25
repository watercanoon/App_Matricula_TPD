CREATE TABLE pago (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      cuota_id BIGINT NOT NULL,
                      monto_pagado DECIMAL(10,2) NOT NULL,
                      metodo_pago VARCHAR(50) NOT NULL, -- Ej: EFECTIVO, YAPE, PLIN, TRANSFERENCIA
                      numero_operacion VARCHAR(100), -- Nullable si es en efectivo
                      fecha_pago DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Auditoría Base
                      estado VARCHAR(20) DEFAULT 'ACTIVO',
                      eliminado BOOLEAN DEFAULT FALSE,
                      fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      usuario_creacion VARCHAR(50) NOT NULL,
                      version BIGINT DEFAULT 0,

                      FOREIGN KEY (cuota_id) REFERENCES cuota(id),
                      UNIQUE KEY uk_pago_cuota (cuota_id) -- Regla estricta: 1 Pago por cada 1 Cuota (No pagos parciales por ahora)
) ENGINE=InnoDB;