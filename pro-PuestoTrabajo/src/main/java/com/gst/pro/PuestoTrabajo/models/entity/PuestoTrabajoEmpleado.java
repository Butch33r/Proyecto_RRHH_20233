package com.gst.pro.PuestoTrabajo.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="PuestoTrabajo_empleado")
@Data
public class PuestoTrabajoEmpleado {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name="empleados_id", unique = true)
        private Long empleadoId;

}
