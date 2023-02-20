package org.gst.prodepartamentos.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Empleado {

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private LocalDate fechaNacimiento;

    private String genero;

}
