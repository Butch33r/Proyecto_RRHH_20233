package org.gst.prodepartamentos.repositories;

import org.gst.prodepartamentos.models.Empleado;
import org.gst.prodepartamentos.models.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartamentoRepository extends CrudRepository<Departamento, Long> {
    @Modifying
    @Query("delete from DepartamentoEmpleado cu where cu.empleadoId=?1")
    void eliminarCursoUsuarioPorId(Long id);


}
