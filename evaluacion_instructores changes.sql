-- LOS QUE YA ESTÉN COMENTADOS YA ESTÁN APLICADOS
/*
-- Agregando opción 'Finalizado' al campo 'estado'
ALTER TABLE evaluacion_instructores.grupo 
MODIFY estado ENUM('Habilitado','Inhabilitado','Finalizado') NOT NULL DEFAULT 'Habilitado';

-- Borrando trigger para no generar automáticamente la clave
DROP TRIGGER bi_grupo;

-- Agregar el campo 'puntaje' en la tabla valoracion
ALTER TABLE valoracion
ADD COLUMN puntaje DECIMAL(10,2) NULL;
*/