package com.gst.pro.PuestoTrabajo.models.entity;

import com.gst.pro.PuestoTrabajo.models.Empleado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "PuestoTRabajo")
public class PuestoTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La descripción del puesto de trabajo es obligatoria")
    @Size(max = 500, message = "La descripción del puesto de trabajo no debe superar los 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El tipo de contrato es obligatorio")
    @Size(max = 300, message = "El tipo de contrato no debe superar los 300 caracteres")
    private String tipoContrato;

    @NotBlank(message = "El campo requisitos educativos es obligatorio.")
    @Size(max = 500, message = "El tipo de contrato no debe superar los 500 caracteres.")
    private String requisitosEducativos;

    private int experiencia;

    @NotBlank(message = "El campo habilidades es obligatorio")
    @Size(max = 500, message = "El valor no debe superar los 500 caracteres")
    private String habilidades;

    @NotBlank(message = "El campo competencias es obligatorio")
    @Size(max = 500, message = "El valor no debe superar los 500 caracteres")
    private String competencias;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="puestoTrabajo_id")
    private List<PuestoTrabajoEmpleado> puestoTrabajoEmpleados;

    @Transient
    private List<Empleado> empleados;

    public PuestoTrabajo() {
        puestoTrabajoEmpleados = new ArrayList<>();
        empleados = new ArrayList<>();
    }

    public void addPuestosTrabajoEmpledo(PuestoTrabajoEmpleado puestoTrabajoEmpleado){
        puestoTrabajoEmpleados.add(puestoTrabajoEmpleado);
    }
    public void removePuestosTrabajoEmpleado(PuestoTrabajoEmpleado puestoTrabajoEmpleado){
        puestoTrabajoEmpleados.remove(puestoTrabajoEmpleado);
    }

}