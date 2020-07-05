-- LOS QUE YA ESTÉN COMENTADOS YA ESTÁN APLICADOS
/*
-- Agregando opción 'Finalizado' al campo 'estado'
ALTER TABLE evaluacion_instructores.grupo 
MODIFY estado ENUM('Habilitado','Inhabilitado','Finalizado') NOT NULL DEFAULT 'Habilitado';

-- Borrando trigger para no generar automáticamente la clave
DROP TRIGGER bi_grupo;
*/

-- Actualiza para que cada 
UPDATE literal_evaluacion
SET calificacion = 10
WHERE idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE valoracion = "Excelente");
UPDATE literal_evaluacion
SET calificacion = 8
WHERE idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE valoracion = "Muy bueno");
UPDATE literal_evaluacion
SET calificacion = 6
WHERE idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE valoracion = "Bueno");
UPDATE literal_evaluacion
SET calificacion = 4
WHERE idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE valoracion = "Decente");
UPDATE literal_evaluacion
SET calificacion = 2
WHERE idvaloracion = (SELECT x.idvaloracion FROM valoracion x WHERE valoracion = "Necesita mejorar");