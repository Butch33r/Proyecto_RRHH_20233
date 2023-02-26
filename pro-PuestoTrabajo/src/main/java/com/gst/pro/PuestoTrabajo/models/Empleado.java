package com.gst.pro.PuestoTrabajo.models;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Empleado {
    private Long id;
    private String nombre;
    private String codigo;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String genero;

}
