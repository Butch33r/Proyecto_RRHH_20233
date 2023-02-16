package com.gst.proempleados.services;


import com.gst.proempleados.models.entity.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> listar();
    Optional<Empleado> porId(Long id);

    Empleado guardar(Empleado usuario);

    void eliminar(Long id);

}
