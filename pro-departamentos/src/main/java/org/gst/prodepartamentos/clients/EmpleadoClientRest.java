package org.gst.prodepartamentos.clients;

import org.gst.prodepartamentos.models.Empleado;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "msvc-empleados", url="localhost:8002/api/empleado")
public interface EmpleadoClientRest {
    @GetMapping("/{id}")
    Empleado detalle(@PathVariable Long id);
    @PostMapping
    Empleado crear(@RequestBody Empleado usuario);

    @GetMapping("/usuarios-por-curso")
    List<Empleado> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);

    @GetMapping("/empleados-por-genero")

    List<Empleado> obtenerEmpleadosPorGenero(@RequestParam String genero);

    @GetMapping("/empleados-por-fechaNacimiento")
    List<Empleado> listarPorNacimientolistar(@RequestParam String fechaIn,@RequestParam String fechaFin);

}
