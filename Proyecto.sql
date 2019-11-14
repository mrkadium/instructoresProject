DROP DATABASE IF EXISTS evaluacion_instructores;
CREATE DATABASE evaluacion_instructores;
USE evaluacion_instructores;

CREATE TABLE rol(
	idrol INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE usuario(
	idusuario INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    usuario VARCHAR(40) NOT NULL,
    nombres VARCHAR(40) NOT NULL,
    apellidos VARCHAR(40) NOT NULL,
    clave LONGTEXT NOT NULL,
    idrol INT(4) NOT NULL,
    FOREIGN KEY (idrol) REFERENCES rol(idrol)
);

CREATE TABLE facultad(
	idfacultad INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    facultad VARCHAR(100) NOT NULL,
    alias VARCHAR(20) NOT NULL,
    iddecano INT UNIQUE,
    FOREIGN KEY (iddecano) REFERENCES usuario(idusuario)
);

CREATE TABLE carrera(
	idcarrera INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    carrera VARCHAR(200) NOT NULL,
    idfacultad INT NOT NULL,
    codigo VARCHAR(6),
    FOREIGN KEY (idfacultad) REFERENCES facultad(idfacultad)
);

CREATE TABLE materia(
	idmateria INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    materia VARCHAR(60) NOT NULL,
    alias VARCHAR(10) NOT NULL,
    idcarrera INT NOT NULL,
    FOREIGN KEY (idcarrera) REFERENCES carrera(idcarrera)
);

CREATE TABLE tipo(
	idtipo INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(40) NOT NULL,
    tipo_input ENUM('RADIO', 'TEXT', 'CHECKBOX')
    -- ,guarda_valor BOOL
);

CREATE TABLE plantilla_test(
	idplantilla_test INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ciclo VARCHAR(6) NOT NULL,
    observacion VARCHAR(200)
);

CREATE TABLE plantilla_literal(
	idplantilla_literal INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    literal LONGTEXT NOT NULL,
    idtipo INT(4) NOT NULL,
    idplantilla_test INT(4) NOT NULL,
    FOREIGN KEY (idtipo) REFERENCES tipo(idtipo),
    FOREIGN KEY (idplantilla_test) REFERENCES plantilla_test(idplantilla_test)
);

CREATE TABLE test(
	idtest INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ciclo VARCHAR(6) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);

/*DROP TRIGGER bi_test_activo;
DELIMITER //
CREATE TRIGGER bi_test_activo
BEFORE INSERT ON test
FOR EACH ROW
BEGIN
	UPDATE test SET estado = 'INACTIVO' WHERE idtest <> NEW.idtest;
END;
//
DELIMITER ;*/

CREATE TABLE grupo(
	idgrupo INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    idmateria INT(4) NOT NULL,
    idinstructor INT(4) NOT NULL,
    idcatedratico INT (4) NOT NULL,
    numero_grupo VARCHAR(8) NOT NULL,
    -- alias varchar(10) not null,
    clave VARCHAR(25) NOT NULL UNIQUE,
    ciclo VARCHAR(6) NOT NULL,
    idtest INT,
    FOREIGN KEY (idmateria) REFERENCES materia(idmateria),
    FOREIGN KEY (idinstructor) REFERENCES usuario(idusuario),
    FOREIGN KEY (idcatedratico) REFERENCES usuario(idusuario),
    FOREIGN KEY (idtest) REFERENCES test(idtest)
);

delimiter //
CREATE TRIGGER bi_grupo
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
	DECLARE codigo_carrera VARCHAR(5);
    SET codigo_carrera = (SELECT c.codigo FROM carrera c WHERE c.idcarrera = (SELECT m.idcarrera FROM materia m WHERE m.idmateria = NEW.idmateria));
	SET new.clave = CONCAT((SELECT alias FROM materia WHERE idmateria = new.idmateria), '-', codigo_carrera, '-', new.numero_grupo, '-', (SELECT SUBSTRING(new.ciclo, -2)));
END;
//
delimiter ;

CREATE TABLE literal(
	idliteral INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    literal LONGTEXT NOT NULL,
    idtipo INT(4) NOT NULL,
    idtest INT(4) NOT NULL,
    FOREIGN KEY (idtipo) REFERENCES tipo(idtipo),
    FOREIGN KEY (idtest) REFERENCES test(idtest)
);

CREATE TABLE evaluacion(
	idevaluacion INT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    idgrupo INT(4) NOT NULL,
    fecha_realizacion DATE NOT NULL,
    observacion LONGTEXT,
    
    FOREIGN KEY (idgrupo) REFERENCES grupo(idgrupo)
);

CREATE TABLE valoracion(
	idvaloracion INT(4) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    valoracion VARCHAR(40) NOT NULL,
    idtipo INT,
    FOREIGN KEY (idtipo) REFERENCES tipo(idtipo)
);

CREATE TABLE literal_evaluacion(
	id INT(40) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    idevaluacion INT(20) NOT NULL,
    idliteral INT(4) NOT NULL,
    idvaloracion INT(4) NOT NULL,
    calificacion INT DEFAULT 0,
    FOREIGN KEY (idevaluacion) REFERENCES evaluacion(idevaluacion),    
    FOREIGN KEY (idliteral) REFERENCES literal(idliteral),    
    FOREIGN KEY (idvaloracion) REFERENCES valoracion(idvaloracion)
);

CREATE TABLE menu(
	idmenu INT PRIMARY KEY NOT NULL,
    menu VARCHAR(50) NOT NULL,
	descripcion VARCHAR(100),
	url VARCHAR(300),
    idcss VARCHAR(100),
    img VARCHAR(200),
	idpadre INT,
	FOREIGN KEY (idpadre) REFERENCES menu(idmenu)
);

CREATE TABLE permiso(
	idpermiso INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	idmenu INT NOT NULL,
	idrol INT NOT NULL,
	FOREIGN KEY (idmenu) REFERENCES menu (idmenu),
	FOREIGN KEY (idrol) REFERENCES rol (idrol)
);