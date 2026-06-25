CREATE TABLE cuota (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       matricula_id BIGINT NOT NULL,
                       concepto VARCHAR(100) NOT NULL, -- Ej: 'MATRÍCULA 2026', 'PENSIÓN MARZO'
                       monto DECIMAL(10,2) NOT NULL,
                       fecha_vencimiento DATE NOT NULL,
                       estado_pago VARCHAR(20) DEFAULT 'PENDIENTE', -- PENDIENTE, PAGADO, VENCIDO

    -- Auditoría Base
                       estado VARCHAR(20) DEFAULT 'ACTIVO',
                       eliminado BOOLEAN DEFAULT FALSE,
                       fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       usuario_creacion VARCHAR(50) NOT NULL,
                       version BIGINT DEFAULT 0,

                       FOREIGN KEY (matricula_id) REFERENCES matricula(id)
) ENGINE=InnoDB;