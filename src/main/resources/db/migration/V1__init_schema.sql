-- TABLA USUARIOS (Seguridad)
CREATE TABLE usuario (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(50) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         estado VARCHAR(20) DEFAULT 'ACTIVO',
                         eliminado BOOLEAN DEFAULT FALSE,
                         fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         usuario_creacion VARCHAR(50) NOT NULL,
                         version BIGINT DEFAULT 0
) ENGINE=InnoDB;

-- TABLA ALUMNOS
CREATE TABLE alumno (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        dni VARCHAR(8) NOT NULL UNIQUE,
                        nombres VARCHAR(100) NOT NULL,
                        apellidos VARCHAR(100) NOT NULL,
                        fecha_nacimiento DATE NOT NULL,
                        estado VARCHAR(20) DEFAULT 'ACTIVO',
                        eliminado BOOLEAN DEFAULT FALSE,
                        fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        usuario_creacion VARCHAR(50) NOT NULL,
                        version BIGINT DEFAULT 0
) ENGINE=InnoDB;

-- TABLA AULA (Con control de concurrencia)
CREATE TABLE aula (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      nivel VARCHAR(20) NOT NULL,
                      grado VARCHAR(20) NOT NULL,
                      seccion VARCHAR(5) NOT NULL,
                      capacidad_maxima INT NOT NULL CHECK (capacidad_maxima > 0),
                      alumnos_matriculados INT NOT NULL DEFAULT 0,
                      estado VARCHAR(20) DEFAULT 'ACTIVO',
                      eliminado BOOLEAN DEFAULT FALSE,
                      fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      usuario_creacion VARCHAR(50) NOT NULL,
                      version BIGINT DEFAULT 0,
                      UNIQUE KEY uk_aula_grado_seccion (nivel, grado, seccion)
) ENGINE=InnoDB;

-- TABLA MATRICULA (Transaccional)
CREATE TABLE matricula (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           aula_id BIGINT NOT NULL,
                           alumno_id BIGINT NOT NULL,
                           anio_academico INT NOT NULL,
                           fecha_matricula DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           estado VARCHAR(20) DEFAULT 'REGISTRADA',
                           eliminado BOOLEAN DEFAULT FALSE,
                           fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           usuario_creacion VARCHAR(50) NOT NULL,
                           version BIGINT DEFAULT 0,
                           FOREIGN KEY (aula_id) REFERENCES aula(id),
                           FOREIGN KEY (alumno_id) REFERENCES alumno(id),
                           UNIQUE KEY uk_matricula_alumno_anio (alumno_id, anio_academico)
) ENGINE=InnoDB;