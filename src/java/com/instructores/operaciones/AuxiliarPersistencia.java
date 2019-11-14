package com.instructores.operaciones;

import com.instructores.anotaciones.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AuxiliarPersistencia {
    public String getEntityName(Object entity) throws Exception {
        String nombre = "";
        Class clase = entity.getClass();
//        Annotation[] anotaciones = clase.getAnnotationsByType(Entity.class);
        Annotation[] anotaciones = clase.getAnnotations();
        if(anotaciones.length == 0) {
            throw new Exception(String.format("Debe agregar la anotación Entity a la clase %s.", clase.getSimpleName()));
        }
        for(Annotation a : anotaciones) {
            Entity e = (Entity) a;
            nombre = e.table();
            if("".equals(nombre))
                nombre = clase.getSimpleName();
        }
        return nombre;
    }
    public List<FieldClass> getCampos(Object entity) throws Exception {
        ArrayList<FieldClass> campos = new ArrayList<>();
        Class clase = entity.getClass();
        Field[] fields = clase.getDeclaredFields();
        int nauto_increment = 0;
        for(Field f : fields) {
            FieldClass campo = new FieldClass();
            if (f.isAnnotationPresent(FieldName.class)){
                FieldName fc = f.getAnnotation(FieldName.class);
                campo.setNombre(fc.name());
            }else{
                campo.setNombre(f.getName());
            }
            //Annotation[] anotacionesPrimaryKey = f.getAnnotationsByType(PrimaryKey.class);
            
            if(nauto_increment != 0 && f.isAnnotationPresent(PrimaryKey.class)){
                throw new Exception(String.format("La clase %s sólo debe contener un campo como llave primaria (PrimaryKey).", clase.getSimpleName()));
            }
            if (nauto_increment==0){
                if (f.isAnnotationPresent(PrimaryKey.class)){
                    campo.setPrimary(true);
                    nauto_increment++;
                }
            }
//            else{
//                throw new Exception(String.format("La clase %s sólo debe contener un campo como llave primaria (PrimaryKey).", clase.getSimpleName()));
//            }
            if(f.isAnnotationPresent(AutoIncrement.class) && !campo.isPrimary()){
                throw new Exception("La anotación @AutoIncrement sólo puede asociarse a una llave primaria.");
            }
            if (f.isAnnotationPresent(AutoIncrement.class)){
                if(campo.isPrimary())
                    campo.setAutoincrement(true);
            }
//            else{
//                throw new Exception("La anotación @AutoIncrement sólo puede asociarse a una llave primaria.");
//            }
            
            if (f.isAnnotationPresent(NotNull.class)){
                campo.setNotnull(true);
            }
            try {
                Field field = clase.getDeclaredField(f.getName());
                field.setAccessible(true);
                campo.setValor(field.get(entity));
            } catch (IllegalAccessException | IllegalArgumentException ex){
                throw ex;
            }
            
            campos.add(campo);
        }
        return campos;
    }
}
