package org.gst.prodepartamentos.services;


import org.gst.prodepartamentos.models.Empleado;
import org.gst.prodepartamentos.models.entity.Departamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepartamentoService {
    List<Departamento> listar();
    Optional<Departamento> porId(Long id);
    Departamento guardar(Departamento Departamento);
    void eliminar(Long id);
    Optional<Departamento> porIdConDepartamento(Long id);
    void eliminarDepartamentoEmpleadoPorId(Long id);

    //Metodos remotos relacionados con el HTTPClient
    // (para la comunicacion de microservicios por medio de apiRest)
    Optional<Empleado> asignarEmpleado(Empleado Empleado, Long id);
    Optional<Empleado> crearEmpleado(Empleado Empleado, Long id);
    Optional<Empleado> eliminarEmpleado(Empleado Empleado, Long id);
    Optional<Departamento> listarEmpleadosPorGenero(Long id,String genero);
    Optional<Departamento> listarPorNacimientolistar(Long id,String fechaInicio, String fechaFin);



}
