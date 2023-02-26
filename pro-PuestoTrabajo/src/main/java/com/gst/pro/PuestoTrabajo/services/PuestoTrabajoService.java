package com.gst.pro.PuestoTrabajo.services;

import com.gst.pro.PuestoTrabajo.models.Empleado;
import com.gst.pro.PuestoTrabajo.models.entity.PuestoTrabajo;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PuestoTrabajoService{

    List<PuestoTrabajo> listar();

    Optional<PuestoTrabajo> porId(Long id);

    PuestoTrabajo guardar(PuestoTrabajo puestoTrabajo);

    void eliminar(Long id);

    void eliminarTodo();

    int count();

    Optional<Empleado> asignarEmpleado(Empleado empleado, Long puestoId);
    Optional<Empleado> crearEmpleado(Empleado empleado, Long puestoId);
    Optional<Empleado> eliminarEmpleado(Empleado empleado, Long puestoId);




}
