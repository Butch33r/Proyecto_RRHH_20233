package com.gst.proempleados.models.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;

@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Empleado empleado = (Empleado) object;
        String nombreNormalizado = Normalizer.normalize(empleado.getNombre().charAt(0) + "", Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        String apellidoNormalizado = Normalizer.normalize(empleado.getApellido(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();



    // Obtener el día del mes actual
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

    // Obtener los dos últimos dígitos del año actual
        int anio = calendario.get(Calendar.YEAR);
        String ultimosDosDigitosAnio = String.format("%02d", anio % 100);

    // Crear el id con los valores obtenidos
        String id = nombreNormalizado+apellidoNormalizado + String.format("%02d", dia) + "-" + ultimosDosDigitosAnio;
        /*Primera versión*/
        //String anioActual = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        //String id = apellido.replaceAll("\\s+","").substring(0, 3).toUpperCase() +
          //      anioActual;
        return id;
    }
}