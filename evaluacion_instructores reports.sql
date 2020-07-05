SELECT * FROM literal_evaluacion WHERE idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = 5);
SELECT * FROM literal;
SELECT * FROM valoracion;
SELECT * FROM tipo;
SELECT * FROM evaluacion;
SELECT * FROM grupo;

-- CANTIDAD POR LITERAL
SELECT 
	COUNT(*)
FROM literal_evaluacion
WHERE 
	idliteral = 1
	AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = 'Excelente')
    AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = 5);
    
-- PROMEDIO POR LITERAL
SELECT ROUND(AVG(calificacion),2) FROM literal_evaluacion
WHERE idliteral = 18 
AND idevaluacion IN (SELECT y.idevaluacion FROM evaluacion y WHERE y.idgrupo = 5);

-- PROMEDIO POR GRUPO
SELECT ROUND(AVG(a.calificacion),2) 
FROM literal_evaluacion a
INNER JOIN literal d ON a.idliteral = d.idliteral
INNER JOIN tipo e ON d.idtipo = e.idtipo
INNER JOIN evaluacion b ON a.idevaluacion = b.idevaluacion
INNER JOIN grupo c ON b.idgrupo = c.idgrupo
WHERE c.idgrupo = 5
AND e.tipo = 'cualitativa';
    


-- COMPLETA
-- CUALITATIVA
SELECT
	b.literal,
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Excelente")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Excelente",
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Muy bueno")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Muy bueno",
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Bueno")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Bueno",
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Decente")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Decente",
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Necesita mejorar")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Necesita mejorar",
	(SELECT ROUND(AVG(calificacion),2) FROM literal_evaluacion WHERE idliteral = a.idliteral
    AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Promedio"
FROM literal_evaluacion a
INNER JOIN literal b ON a.idliteral = b.idliteral
INNER JOIN tipo e ON b.idtipo = e.idtipo
INNER JOIN evaluacion c ON a.idevaluacion = c.idevaluacion
INNER JOIN grupo d ON c.idgrupo = d.idgrupo
WHERE d.idgrupo = 5
AND e.tipo = "cualitativa"
GROUP BY a.idliteral;


-- SI-NO
SELECT
	b.literal,
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Si")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Si",
	(SELECT COUNT(*) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "No")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "No"
FROM literal_evaluacion a
INNER JOIN literal b ON a.idliteral = b.idliteral
INNER JOIN tipo e ON b.idtipo = e.idtipo
INNER JOIN evaluacion c ON a.idevaluacion = c.idevaluacion
INNER JOIN grupo d ON c.idgrupo = d.idgrupo
WHERE d.idgrupo = 5
AND e.tipo = "si-no"
GROUP BY a.idliteral;


-- CUANTITATIVAS
SELECT
	b.literal,
	(SELECT ROUND(AVG(calificacion),2) FROM literal_evaluacion
	WHERE 
		idliteral = a.idliteral
		AND idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE x.valoracion = "Calificacion")
        AND idevaluacion IN (SELECT x.idevaluacion FROM evaluacion x WHERE x.idgrupo = d.idgrupo)) AS "Calificacion"
FROM literal_evaluacion a
INNER JOIN literal b ON a.idliteral = b.idliteral
INNER JOIN tipo e ON b.idtipo = e.idtipo
INNER JOIN evaluacion c ON a.idevaluacion = c.idevaluacion
INNER JOIN grupo d ON c.idgrupo = d.idgrupo
WHERE d.idgrupo = 5
AND e.tipo = "cuantitativa"
GROUP BY a.idliteral;



-- OBSERVACIONES
SELECT observacion FROM evaluacion
WHERE LENGTH(observacion) > 3
AND idgrupo = 10;


-- CONSULTAS PARA LOS PARÁMETROS DE LOS REPORTES
-- Verifica los tipos de literal que hay
SELECT 
	a.tipo, (COUNT(*) > 0) AS hasValue
FROM tipo a
LEFT JOIN valoracion b ON a.idtipo = b.idtipo
INNER JOIN literal_evaluacion c ON c.idvaloracion = b.idvaloracion
INNER JOIN evaluacion d ON c.idevaluacion = d.idevaluacion
INNER JOIN grupo e ON d.idgrupo = e.idgrupo
WHERE e.idgrupo = 4
GROUP BY a.idtipo
;

-- Verifica si hay evaluaciones para el grupo
SELECT COUNT(*) > 0 AS hasEvaluacion FROM evaluacion WHERE idgrupo = 4;