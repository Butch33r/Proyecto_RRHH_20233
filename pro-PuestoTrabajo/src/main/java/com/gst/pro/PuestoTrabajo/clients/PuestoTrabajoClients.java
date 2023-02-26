package com.gst.pro.PuestoTrabajo.clients;

import com.gst.pro.PuestoTrabajo.models.Empleado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="msvc-empleados", url="localhost:8002/api/empleado")
public interface PuestoTrabajoClients {

    @GetMapping("/{id}")
    Empleado detalle(@PathVariable Long id);

    @PostMapping()
    Empleado crear(@RequestBody Empleado empleado);

}
