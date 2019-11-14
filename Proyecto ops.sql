USE evaluacion_instructores;

INSERT INTO rol(rol) VALUES
('admin'),
('decano'),
('catedratico'),
('instructor');

INSERT INTO usuario(usuario, nombres, apellidos, clave, idrol) VALUES
('alvaro.zavala','Álvaro Hernán','Zavala Ruballo',SHA2('admin', 256),1),
('ruben.mendoza','Rubén Alfredo','Mendoza Juárez',SHA2('admin', 256),2),
('fernando.rodriguez','Fernando Juan José', 'Rodríguez Salazar',SHA2('admin', 256),2),
('silvia.lopez','Silvia Yolany', 'López Rodríguez',SHA2('admin', 256),2),
('ivan.alvarado','Iván Orlando','Alvarado Niño',SHA2('admin', 256),3),
('david.cortez','David','Cortez Rivera',SHA2('admin', 256),3),
('danilo.mejia','Danilo Omar','Mejía Murcia',SHA2('admin', 256),4),
('mario.rivera','Mario Adalberto','Rivera Olivo',SHA2('admin', 256),4)
;

INSERT INTO facultad(facultad, alias, iddecano) VALUES
('Facultad de Ingeniería y Ciencias Naturales', 'FICN', 2),
('Facultad de Economía y Ciencias Sociales', 'FECS',3),
('Facultad de Ciencias Jurídicas', 'FCJ',4);

INSERT INTO carrera(carrera, idfacultad, codigo) VALUES
('Ingeniería en Sistemas Computacionales', 1, 'I04'),
('Ingeniería Eléctrica', 1, 'I02'),
('Ingeniería Industrial', 1, 'I01'),
('Ingeniería en Agronegocios', 1, 'I03'),
('Licenciatura en Ciencias Jurídicas', 3, 'D01');

INSERT INTO materia(materia, alias, idcarrera) VALUES
('Bases de datos I', 'BD1', 1),
('Diseño web', 'DW', 1),
('Desarrollo de software II', 'DS2', 1);

INSERT INTO grupo(idmateria, idinstructor, idcatedratico, numero_grupo, ciclo) VALUES
(1,8,1, '07', '01/19'),
(2,8,6, '07', '01/19'),
(2,7,6, '01', '01/19')
;

-- insert into tipo(tipo, tipo_input, guarda_valor) values
-- ('cualitativa', 1, false),
-- ('cuantitativa', 2, true),
-- ('si-no', 1, false);

INSERT INTO tipo(tipo, tipo_input) VALUES
('cualitativa', 1),
('cuantitativa', 2),
('si-no', 1);

INSERT INTO valoracion (valoracion, idtipo) VALUES
('Excelente', 1),
('Muy bueno', 1),
('Bueno', 1),
('Decente', 1),
('Necesita mejorar', 1),
('Calificación', 2),
('Si', 3),
('No', 3);

INSERT INTO plantilla_test(ciclo, observacion) VALUES ('02/18', 'Primer plantilla');

INSERT INTO plantilla_literal(literal, idtipo, idplantilla_test) VALUES
('¿Cómo evalúa usted la presentación personal del instructor?', 1, 1),
('¿El instructor cumple con el horario establecido?', 1, 1),
('¿Cómo evalúa el comportamiento del instructor al dar la instructoría?', 1, 1),
('¿Es ordenado el instructor al dar la instructoría?', 1, 1),
('¿Utiliza el instructor vocabulario y palabras apropiadas en el desarrollo de sus instructorías?', 1, 1),
('¿El trato del instructor es apropiado, sin palabras intimidadoras y frases que puedan considerarse de doble sentido?', 1, 1),
('¿Existe accesibilidad del instructor ante preguntas del alumno?', 1, 1),
('¿Considera que la metodología del instructor es la adecuada en el desarrollo de las prácticas?', 1, 1),
('¿Considera que las evaluaciones que utiliza el instructor están acordes al desarrollo del programa?', 1, 1),
('¿Cree usted que el instructor prepara las instructorías antes de llegar a impartirlas?', 1, 1),
('¿El instructor desarrolla las instructorías conforme al desarrollo de los contenidos programáticos de la asignatura que el docente desarrolla en la clase?', 1, 1),
('¿El instructor desarrolla la instructoría de manera que se hace comprensible y asimilable para los estudiantes?', 1, 1),
('¿El instructor posee dominio de la asignatura?', 1, 1),
('¿Demuestra seguridad y el conocimiento para responder las preguntas que los estudiantes formulan?', 1, 1),
('¿En alguna ocasión el instructor ha llegado a trabajar con usted en shorts y sandalias?', 3, 1),
('¿Sabe usted si el instructor ha abusado de su posición para los logros personales?', 3, 1),
('Del 1 al 10, ¿qué calificación le daría al instructor?', 2, 1)
;

INSERT INTO menu(idmenu, menu, descripcion, url, idcss, img, idpadre) VALUES
(1,'Inicio', NULL, '/Principal', 'inicio', NULL, NULL),
(2,'Grupos', 'Grupos de laboratorio y sus respectivos instructores, cada grupo con su detalle de evaluaciones', '/Principal?accion=resultados', 'resultados', 'instructores.png', NULL),
-- (3,'Instructores', 'Lista de instructores, cada uno con su resumen detallado de evaluaciones', '/Principal?accion=instructores', 'instructores', 'instructores.png', null),
(4,'Configuraciones', 'Información de la base de datos que puede ser modificada por el administrador', '/Principal?accion=configuraciones', 'configuraciones', 'configurar.png', NULL),
(5,'Perfil', 'Información del usuario que puede ser modificada', '/Principal?accion=perfil', 'perfil', 'user.png', NULL),
(6,'Facultades', NULL, '/Facultades', 'facultades', 'user.png', 4),
(7,'Carreras', NULL, '/Carreras', 'carreras', 'user.png', 4),
(8,'Materias', NULL, '/Materias', 'materias', 'user.png', 4),
(9,'Roles', NULL, '/Roles', 'roles', 'user.png', 4),
(10,'Menus', NULL, '/Menus', 'menus', 'user.png', 4),
(11,'Permisos', NULL, '/Permisos', 'permisos', 'user.png', 4),
(12,'Usuarios', NULL, '/Usuarios', 'usuarios', 'user.png', 4),
(13,'Grupos', NULL, '/Grupos', 'grupos', 'user.png', 4),
(14,'Valoraciones', NULL, '/Valoraciones', 'valoraciones', 'user.png', 4),
(15,'Tipos', '/Tipos', NULL, 'tipos', 'user.png', 4),
(16,'Plantillas_test', NULL, '/Plantillas_test', 'plantillas_test', 'user.png', 4),
(17,'Plantillas_literal', NULL, '/Plantillas_literal', 'plantillas_literal', 'user.png', 4),
(18,'Tests', NULL, '/Tests', 'tests', 'user.png', 4);

-- SELECT * FROM plantilla_test;

-- INSERT INTO test(ciclo) VALUES ('02/20');


INSERT INTO permiso(idmenu, idrol) SELECT idmenu, 1 FROM menu;
INSERT INTO permiso(idmenu, idrol) SELECT idmenu, 2 FROM menu WHERE idmenu IN (1,2,5);
INSERT INTO permiso(idmenu, idrol) SELECT idmenu, 3 FROM menu WHERE idmenu IN (1,2,5);
INSERT INTO permiso(idmenu, idrol) SELECT idmenu, 4 FROM menu WHERE idmenu IN (1,2,5);


-- MENU
SELECT 
	a.idmenu, a.menu, a.descripcion, a.url, a.idcss, a.img,
    IF(b.idmenu IS NULL, 0, b.idmenu) AS idpadre, 
    b.menu AS padre
FROM menu a
LEFT OUTER JOIN menu b
ON a.idpadre = b.idmenu
WHERE a.idmenu = 1
;


-- PERMISO
SELECT
	a.idpermiso,
    b.menu,
    c.rol
FROM 
	permiso a, 
	menu b, 
    rol c
WHERE 
	a.idmenu = b.idmenu
    AND a.idrol = c.idrol
--    AND a.idmenu = 1
;

SELECT * FROM grupo;
SELECT 
	z.idgrupo,
    (SELECT a.materia FROM materia a WHERE a.idmateria = z.idmateria) AS materia,
    (SELECT a.alias FROM materia a WHERE a.idmateria = z.idmateria) AS alias_mat,
    (SELECT CONCAT(b.nombres,' ',b.apellidos) FROM usuario b WHERE b.idusuario = z.idinstructor) AS instructor,
    (SELECT CONCAT(b.nombres,' ',b.apellidos) FROM usuario b WHERE b.idusuario = z.idcatedratico) AS catedratico,
    z.numero_grupo, z.clave, z.ciclo
FROM grupo z;


-- select idusuario from usuario where idrol in (select idrol from rol where rol = 'admin');

SELECT 
	*
FROM literal_evaluacion
WHERE idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = '1');


-- PROMEDIOS DE GRUPO
SELECT
	a.idliteral,
	c.literal,
	(SELECT valoracion FROM valoracion x WHERE x.idvaloracion = FORMAT(AVG(a.idvaloracion),0)) AS 'Promedio valoración',
    IF(AVG(a.calificacion) = 0.00, '-', FORMAT(AVG(a.calificacion),0)) AS 'Promedio calificación'
FROM literal_evaluacion a, evaluacion b, literal c
WHERE a.idevaluacion = b.idevaluacion AND a.idliteral = c.idliteral AND b.idgrupo = 1
GROUP BY a.idliteral
;


-- PROMEDIOS DE INSTRUCTOR
SELECT
	c.literal,
	(SELECT valoracion FROM valoracion x WHERE x.idvaloracion = FORMAT(AVG(a.idvaloracion),0)) AS 'Promedio valoración',
    IF(AVG(a.calificacion) = 0.00, '-', FORMAT(AVG(a.calificacion),0)) AS 'Promedio calificación'
FROM literal_evaluacion a, evaluacion b, literal c
WHERE 
	a.idevaluacion = b.idevaluacion 
    AND a.idliteral = c.idliteral 
    AND b.idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = 8)
GROUP BY a.idliteral
;


-- PROMEDIOS DE INSTRUCTOR (CUALITATIVAS)
SELECT
	a.idliteral,
	(SELECT valoracion FROM valoracion x WHERE x.idvaloracion = FORMAT(AVG(a.idvaloracion),0)) AS 'Promedio valoración'
FROM literal_evaluacion a, evaluacion b, literal c
WHERE 
	a.idevaluacion = b.idevaluacion
    AND a.idliteral IN (SELECT idliteral FROM literal WHERE idtipo IN (SELECT x.idtipo FROM tipo x WHERE tipo = 'cualitativa'))
    AND a.idliteral = c.idliteral 
    AND b.idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = 8)
GROUP BY a.idliteral
;

SELECT * FROM literal_evaluacion;

-- DETALLE DE GRUPO (LA QUE SÍ FUNCIONA DE VERDAD)
SELECT
	a.idliteral,
    c.literal,
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Excelente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Excelente',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Muy bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Muy bueno',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Bueno',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Decente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Decente',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Necesita mejorar') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Necesita mejorar',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Si') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Si',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'No') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'No',
    IF(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) IS NULL, '-' ,FORMAT(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)),0)) AS 'Calificación'
FROM literal_evaluacion a, evaluacion b, literal c
WHERE 
	a.idevaluacion = b.idevaluacion 
    AND a.idliteral = c.idliteral 
    AND b.idgrupo = 1
GROUP BY a.idliteral
ORDER BY a.idvaloracion
;


-- DETALLE DE INSTRUCTOR (LA QUE SÍ FUNCIONA DE VERDAD)
SELECT
	a.idliteral,
    c.literal,
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Excelente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Excelente',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Muy bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Muy bueno',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Bueno',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Decente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Decente',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Necesita mejorar') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Necesita mejorar',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Si') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Si',
    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'No') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'No',
    IF(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) IS NULL, '-' ,FORMAT(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)),0)) AS 'Calificación'
FROM literal_evaluacion a, evaluacion b, literal c
WHERE 
	a.idevaluacion = b.idevaluacion 
    AND a.idliteral = c.idliteral 
    AND b.idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = 8)
GROUP BY a.idliteral
;


SELECT idevaluacion FROM evaluacion WHERE idevaluacion IN (SELECT idevaluacion FROM evaluacion e WHERE e.idgrupo = (SELECT idgrupo FROM grupo WHERE idgrupo = 1));

SELECT 
	(SELECT literal FROM literal WHERE idliteral = le.idliteral) AS Literal, -- literal
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Excelente') AND idliteral = le.idliteral) AS Excelente, -- cantidad de excelentes
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Muy bueno') AND idliteral = le.idliteral) AS 'Muy bueno', -- cantidad de muy buenos
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Bueno') AND idliteral = le.idliteral) AS Bueno, -- cantidad de buenos
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Decente') AND idliteral = le.idliteral) AS Decente, -- cantidad de regulares
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Necesita mejorar') AND idliteral = le.idliteral) AS 'Necesita mejorar', -- cantidad de necesita mejorar
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'Si') AND idliteral = le.idliteral) AS 'Si', -- cantidad de necesita mejorar
    (SELECT COUNT(*) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'No') AND idliteral = le.idliteral) AS 'No', -- cantidad de necesita mejorar
    (SELECT AVG(calificacion) FROM literal_evaluacion WHERE idvaloracion = (SELECT idvaloracion FROM valoracion WHERE valoracion = 'calificación') AND idliteral = le.idliteral) AS 'Calificación'
FROM literal_evaluacion le
WHERE le.idevaluacion IN (SELECT idevaluacion FROM evaluacion e WHERE e.idgrupo = (SELECT idgrupo FROM grupo WHERE idgrupo = 1)) -- que sean del grupo 1
-- and le.idliteral in (select idliteral from literal where idtipo = 1) -- y que el tipo de literal sea el 1 (cualitativo)
GROUP BY le.idliteral
;

-- OBSERVACIONES AL GRUPO
SELECT 
	idevaluacion, observacion, fecha_realizacion 
FROM evaluacion 
WHERE 
	idgrupo = '1'
    AND LENGTH(observacion) > 0
ORDER BY idevaluacion DESC LIMIT 3;


-- OBSERVACIONES AL INSTRUCTOR
SELECT 
	idevaluacion, observacion, fecha_realizacion 
FROM evaluacion 
WHERE 
	idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = 8)
    AND LENGTH(observacion) > 0
ORDER BY idevaluacion DESC LIMIT 3;


SELECT * FROM materia;
SELECT * FROM facultad;
SELECT * FROM carrera;
SELECT * FROM grupo;


-- TODOS LOS USUARIOS
SELECT
	a.idusuario,
    a.usuario,
    CONCAT(a.nombres, ' ', a.apellidos) AS nombre,
    a.idrol,
    b.rol
FROM usuario a, rol b
WHERE
	a.idrol = b.idrol
--    AND a.idusuario = 1
;

-- USUARIOS QUE NO SON INSTRUCTORES (o sea, todos los que pueden ser catedráticos)
SELECT
	a.idusuario,
    a.usuario,
    CONCAT(a.nombres, ' ', a.apellidos) AS nombre,
    a.idrol,
    b.rol
FROM usuario a, rol b
WHERE
	a.idrol = b.idrol
	AND b.rol NOT IN ('instructor')
;


-- MATERIAS
SELECT
	a.idmateria, a.materia, b.carrera
FROM materia a, carrera b
WHERE
	a.idcarrera = b.idcarrera
;


-- GRUPOS (INSERTAR_MODIFICAR)
SELECT
	a.idgrupo,
    a.idmateria, b.materia,
    a.idinstructor, CONCAT(c.nombres, ' ', c.apellidos) AS instructor,
    a.idcatedratico, CONCAT(d.nombres, ' ', d.apellidos) AS catedratico,
    a.numero_grupo, b.alias, a.clave, a.ciclo
FROM grupo a, materia b, usuario c, usuario d
WHERE
	a.idmateria = b.idmateria
    AND a.idinstructor = c.idusuario
    AND a.idcatedratico = d.idusuario
    -- AND a.idgrupo = 1
;


-- INSTRUCTORES
SELECT
	a.idinstructor,
    CONCAT(b.nombres, ' ', b.apellidos) AS instructor,
    b.usuario,
    COUNT(*) AS cant_labos,
    (SELECT	COUNT(*) FROM evaluacion WHERE idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = a.idinstructor)) AS cant_ev
FROM grupo a, usuario b
WHERE
	a.idinstructor = b.idusuario
    -- AND a.idinstructor = 7
GROUP BY a.idinstructor
;


-- CANTIDAD DE EVALUACIONES POR INSTRUCTOR
SELECT
	COUNT(*)
FROM evaluacion
WHERE idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = 8)
;



-- CONSULTAS PARA REPORTE
SELECT
	b.idusuario,
	CONCAT(b.nombres,' ',b.apellidos) AS instructor,
    COUNT(*) AS cant
FROM grupo a, usuario b
WHERE
	a.idinstructor = b.idusuario
    AND b.idusuario = 8
GROUP BY a.idinstructor
;

SELECT
	CONCAT(c.nombres,' ',c.apellidos) AS instructor,
    COUNT(*) AS cant
FROM evaluacion a, grupo b, usuario c
WHERE
	a.idgrupo = b.idgrupo
    AND b.idinstructor = c.idusuario
    AND c.idusuario = 8
GROUP BY b.idinstructor
;


-- USUARIO
SELECT
	a.*, b.rol
FROM usuario a, rol b
WHERE 
	a.idrol = b.idrol
    AND a.idusuario = 8
;


-- CANTIDAD POR CADA VALORACIÓN DE UN GRUPO
SELECT 
	a.idvaloracion, b.valoracion, 
    COUNT(*) AS cantidad,
    ROUND((COUNT(*) / (SELECT COUNT(*) FROM literal_evaluacion y WHERE y.idvaloracion IN (SELECT x.idvaloracion FROM valoracion x WHERE x.idtipo = 1) AND y.idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = 1)))*100,2) AS porcentaje
FROM literal_evaluacion a, valoracion b
WHERE 
	a.idvaloracion = b.idvaloracion
    AND a.idvaloracion IN (SELECT x.idvaloracion FROM valoracion x WHERE x.idtipo = 1)
    AND a.idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = 1)
GROUP BY a.idvaloracion;














-- LISTA DE GRUPOS (admin)
/*select
	idgrupo,
	(select materia from materia where idmateria = g.idmateria) as materia,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as instructor,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as catedratico,
	numero_grupo, clave, ciclo
from grupo g;

-- LISTA DE GRUPOS (decano)
select
	idgrupo as 'ID Grupo',
	(select materia from materia where idmateria = g.idmateria) as Materia,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as Instructor,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as Catedratico,
	numero_grupo as 'Grupo', clave as 'Clave', ciclo as 'Ciclo',
    (select (select valoracion from valoracion where idvaloracion = (format(avg(le.idvaloracion),0)))
		from literal_evaluacion le
		where idevaluacion in (select idevaluacion from evaluacion where idgrupo = g.idgrupo)) as Promedio
from grupo g
where g.idmateria in (select idmateria from materia where idcarrera = (select idcarrera from carrera where idfacultad = (select idfacultad from facultad where iddecano = 2) limit 1))
OR idinstructor = 2
;


-- LISTA DE GRUPOS (catedrático)
select
	idgrupo as 'ID Grupo',
	(select materia from materia where idmateria = g.idmateria) as Materia,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as Instructor,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as Catedratico,
	numero_grupo as 'Grupo', clave as 'Clave', ciclo as 'Ciclo',
    (select (select valoracion from valoracion where idvaloracion = (format(avg(le.idvaloracion),0)))
		from literal_evaluacion le
		where idevaluacion in (select idevaluacion from evaluacion where idgrupo = g.idgrupo)) as Promedio
from grupo g
where idcatedratico = 1
OR idinstructor = 1
;

-- LISTA DE GRUPOS (instructor)
select
	idgrupo,
	(select materia from materia where idmateria = g.idmateria) as materia,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as instructor,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as catedratico,
	numero_grupo, clave, ciclo
from grupo g
where idinstructor = 6;


-- GRUPO_DETALLE
select 
	(select literal from literal where idliteral = le.idliteral) as Literal, -- literal
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Excelente') and idliteral = le.idliteral) as Excelente, -- cantidad de excelentes
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Muy bueno') and idliteral = le.idliteral) as 'Muy bueno', -- cantidad de muy buenos
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Bueno') and idliteral = le.idliteral) as Bueno, -- cantidad de buenos
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Decente') and idliteral = le.idliteral) as Decente, -- cantidad de regulares
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Necesita mejorar') and idliteral = le.idliteral) as 'Necesita mejorar', -- cantidad de necesita mejorar
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'Si') and idliteral = le.idliteral) as 'Si', -- cantidad de necesita mejorar
    (select count(*) from literal_evaluacion where idvaloracion = (select idvaloracion from valoracion where valoracion = 'No') and idliteral = le.idliteral) as 'No' -- cantidad de necesita mejorar
from literal_evaluacion le
where idevaluacion in (select idevaluacion from evaluacion e where e.idgrupo = (select idgrupo from grupo where idgrupo = 1)) -- que sean del grupo 1
-- and le.idliteral in (select idliteral from literal where idtipo = 1) -- y que el tipo de literal sea el 1 (cualitativo)
group by le.idliteral
;


-- GRUPO_PROMEDIO
select
	(select literal from literal where idliteral = le.idliteral) as literal,
	(select valoracion from valoracion where idvaloracion = (format(avg(le.idvaloracion),0))) as promedio
from literal_evaluacion le
where idevaluacion in (select idevaluacion from evaluacion where idgrupo = 1)
group by idliteral
;

-- PROMEDIO GLOBAL DEL GRUPO
select
	count(*),
	(select valoracion from valoracion where idvaloracion = (format(avg(le.idvaloracion),0))) as promedio
from literal_evaluacion le
where idevaluacion in (select idevaluacion from evaluacion where idgrupo = 1); 



-- LISTA DE GRUPOS Y PROMEDIO
select
	idgrupo,
	(select materia from materia where idmateria = g.idmateria) as materia,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as instructor,
	(select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as catedratico,
	numero_grupo, clave, ciclo,
    (select (select valoracion from valoracion where idvaloracion = (format(avg(le.idvaloracion),0)))
		from literal_evaluacion le
		where idevaluacion in (select idevaluacion from evaluacion where idgrupo = g.idgrupo)) as promedio
from grupo g;*/

