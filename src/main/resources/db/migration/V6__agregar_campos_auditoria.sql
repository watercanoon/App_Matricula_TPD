-- Agregar campos de auditoría de modificación a todas las tablas
ALTER TABLE usuario
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);

ALTER TABLE alumno
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);

ALTER TABLE aula
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);

ALTER TABLE matricula
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);

ALTER TABLE cuota
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);

ALTER TABLE pago
    ADD COLUMN fecha_modificacion DATETIME,
    ADD COLUMN usuario_modificacion VARCHAR(50);