package com.gst.proempleados.controller;


import com.gst.proempleados.models.entity.Empleado;
import com.gst.proempleados.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController     //Classe es una api rest, se comunica por sus metos con la base de datos
@RequestMapping("/api/empleado")
public class EmpleadoController {
    @Autowired  //extrae los metodos del CrudRepository para poder usarlos
    private EmpleadoService empleadoService;

    @GetMapping //GET = estrae datos de la base de datos, no recive datos
    public List<Empleado> list() {
        return empleadoService.listar();
    }
    @GetMapping("/{id}") //Recive datos
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Empleado> optional = empleadoService.porId(id);
        if (optional.isPresent()) {     //isPresent si contiene datos
            return ResponseEntity.ok(optional.get());
        } else
            return ResponseEntity.notFound().build();
    }
    @PostMapping    //POST = Crea en la base de datos
    public ResponseEntity<?> create(@Valid @RequestBody Empleado curso, BindingResult result) {
        //Devuelve al RespondeBody en JSON
        if(result.hasErrors()){ //Consulta existencia de errores
            return validar(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(empleadoService.guardar(curso));  //Estados optimo de una appWeb es 200 o 201
    }
    @PutMapping("/{id}")    //PUT = actualiza la bases de datos     //Envía un JSON
    public ResponseEntity<?> edit(@Valid @RequestBody Empleado curso, @PathVariable Long id,BindingResult result ) {
        //Devuelve al RespondeBody en JSON
        if(result.hasErrors()){ //Consulta existencia de errores
            return validar(result);
        }

        Optional<Empleado> optional = empleadoService.porId(id); //Verifica que la clase exista
        if (optional.isPresent()) {//Consulta si continee el registro buscado
            Empleado userDB = optional.get(); //Contenido se asigna a la variable
            userDB.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(userDB));
        } else
            return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")  //DELETE = Elimina de la base de datos
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Empleado> optional = empleadoService.porId(id); //Verifica que la clase exista
        if (optional.isPresent()) {
            empleadoService.eliminar(id);
            return ResponseEntity.noContent().build();  //Indicar que se aya borrado
        } else
            return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores=new HashMap<>();
        result.getFieldErrors().forEach(err -> {

            //Hace la carga del mapa
            errores.put(err.getField().toString(), String.format("El campo", err.getField())+err.getDefaultMessage());
        } );
        return ResponseEntity.badRequest().body(errores);
    }
}
