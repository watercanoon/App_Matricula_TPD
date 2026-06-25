-- Insertar Aula de prueba (Capacidad intencionalmente baja: 2 alumnos)
INSERT INTO aula (nivel, grado, seccion, capacidad_maxima, alumnos_matriculados, usuario_creacion)
VALUES ('SECUNDARIA', 'QUINTO', 'A', 2, 0, 'SISTEMA_TEST');

-- Insertar 3 Alumnos de prueba
INSERT INTO alumno (dni, nombres, apellidos, fecha_nacimiento, usuario_creacion)
VALUES
    ('70123456', 'Carlos Alberto', 'Gomez Perez', '2010-05-15', 'SISTEMA_TEST'),
    ('70654321', 'Maria Elena', 'Salas Rojas', '2010-08-22', 'SISTEMA_TEST'),
    ('70987654', 'Luis Fernando', 'Torres Silva', '2009-11-03', 'SISTEMA_TEST');
-- Usuario: admin / Clave: 123456
INSERT INTO usuario (username, password, email, usuario_creacion)
VALUES ('admin', '$2a$12$iOzsQB5pqkouwbp/T0XAnuT5D6.8D2XqqH3bCOS3g3YHkpct2xdqS', 'admin@escuela.edu.pe', 'SISTEMA_TEST');