-- GRUPOS_CONSULTA
SELECT
	a.idgrupo, b.materia,
    CONCAT(c.nombres,' ',c.apellidos) AS instructor,
    CONCAT(d.nombres,' ',d.apellidos) AS catedratico,
    a.numero_grupo, a.clave, a.ciclo,
    IF(a.idtest IS NULL,'-',a.idtest) as idtest
FROM 
	grupo a, materia b, usuario c, usuario d
WHERE
	a.idmateria = b.idmateria
    AND a.idinstructor = c.idusuario
    AND a.idcatedratico = d.idusuario
;
-- GRUPOS_CONSULTA_Principal
SELECT
	a.idgrupo, b.materia, e.alias as facultad,
    CONCAT(c.nombres,' ',c.apellidos) AS instructor,
    CONCAT(d.nombres,' ',d.apellidos) AS catedratico,
    a.numero_grupo, a.clave, a.ciclo,
    IF(a.idtest IS NULL,'-',a.idtest) as idtest
FROM 
	grupo a, materia b, usuario c, usuario d, facultad e, carrera f
WHERE
	a.idmateria = b.idmateria
    AND a.idinstructor = c.idusuario
    AND a.idcatedratico = d.idusuario
    AND e.idfacultad = f.idfacultad
    AND f.idcarrera = b.idcarrera
;

