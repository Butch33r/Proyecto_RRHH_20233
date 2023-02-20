package com.gst.proempleados.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-departamentos", url="localhost:8001/api/departamento")
public interface DepartamentoClienteRest {
    //@DeleteMapping("/eliminar-curUser/{id}")
    //public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id);
}
