package org.gst.prodepartamentos.services;

import org.gst.prodepartamentos.entity.Departamento;

import java.util.List;
import java.util.Optional;

public interface DepartamentoService {
    List<Departamento> listar();
    Optional<Departamento> porId(Long id);

    Departamento guardar(Departamento usuario);

    void eliminar(Long id);


}
