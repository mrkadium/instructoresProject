-- Agregando opción 'Finalizado' al campo 'estado'
ALTER TABLE evaluacion_instructores.grupo 
MODIFY estado ENUM('Habilitado','Inhabilitado','Finalizado') NOT NULL DEFAULT 'Habilitado';