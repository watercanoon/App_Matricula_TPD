-- Agregar columna rol a la tabla usuario
ALTER TABLE usuario ADD COLUMN rol VARCHAR(50) NOT NULL DEFAULT 'SECRETARIA';

-- Actualizar a nuestro usuario administrador de pruebas
UPDATE usuario SET rol = 'ADMINISTRADOR' WHERE username = 'admin';