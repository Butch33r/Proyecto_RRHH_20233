package org.gst.prodepartamentos.models.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.gst.prodepartamentos.models.Empleado;

import java.util.List;

@Entity
@Table(name="departamentos")
@Data
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(max = 50)
    private String nombre;

    @NotBlank(message = "La descripción del departamento es obligatoria")
    @Size(max = 100)
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "curso_id")
    @JoinColumn(name = "departamento_id")
    private List<DepartamentoEmpleado> departamentoEmpleados;
    @Transient
    private List<Empleado> empleados;

    public void addDepartamentoEmpleados(DepartamentoEmpleado cursoUsuario){
        departamentoEmpleados.add(cursoUsuario);
    }
    public void removeDepartamentoEmpleados(DepartamentoEmpleado cursoUsuario){
        departamentoEmpleados.remove(cursoUsuario);
    }
}
