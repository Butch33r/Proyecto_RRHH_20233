package com.gst.proempleados.repositories;


import com.gst.proempleados.models.entity.Empleado;
import org.springframework.data.repository.CrudRepository;


public interface EmpleadoRepository extends CrudRepository<Empleado,Long> {

}
