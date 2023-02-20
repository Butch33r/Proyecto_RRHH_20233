package org.gst.prodepartamentos.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//@Table(name="cursos_usuarios")
@Table(name="departamentos-empleados")
@Data
public class DepartamentoEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // @Column(name="usuario_id", unique = true)
  //private Long usuarioId;
    @Column(name="empleado_id", unique = true)
    private Long empleadoId;


    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(obj instanceof DepartamentoEmpleado)){
            return false;
        }
        DepartamentoEmpleado o = (DepartamentoEmpleado) obj;
        return this.empleadoId !=null && this.empleadoId.equals(o.empleadoId);
    }
}
