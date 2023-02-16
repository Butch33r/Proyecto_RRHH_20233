package com.gst.proempleados.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "empleado")
//@IdClass(EmpleadoId.class)

@Data
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    // getters y setters


    //  @GeneratedValue(generator = "custom-id")
    //@GenericGenerator(name = "custom-id", strategy = "com.gst.proempleados.models.entity.CustomIdGenerator")

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String apellido;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    private String email;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String direccion;

    public void setCodigo() {

        String nombreNormalizado = Normalizer.normalize(getNombre().charAt(0) + "", Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        String apellidoNormalizado = Normalizer.normalize(getApellido(), Normalizer.Form.NFD)
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
       this.codigo=id;
    }
}

