package com.gst.proempleados.services;

import com.gst.proempleados.models.entity.Empleado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> listar();
    Optional<Empleado> porId(Long id);
    Empleado guardar(Empleado Empleado);
    void eliminar(Long id);

    List<Empleado> listarPorIds(Iterable<Long> ids);

    Optional<Empleado> porEmail(String email);

    List<Empleado> listarPorGenero(String genero);
    List<Empleado> listarPorApellido(String apellido);
    List<Empleado> listarPorNacimientolistarPorNacimiento(LocalDate fechaInicio, LocalDate fechaFin);


}
