package com.gst.pro.PuestoTrabajo.controllers;

import com.gst.pro.PuestoTrabajo.models.Empleado;
import com.gst.pro.PuestoTrabajo.models.entity.PuestoTrabajo;
import com.gst.pro.PuestoTrabajo.services.PuestoTrabajoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/puestoTrabajo")
public class PuestoTrabajoController {

    @Autowired
    private PuestoTrabajoService service;

    @GetMapping
    public ResponseEntity<List<PuestoTrabajo>> listar(){
        return  ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<PuestoTrabajo> op = service.porId(id);
        if (op.isPresent()){
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PuestoTrabajo puestoTrabajo, BindingResult result){
        if(result.hasErrors()) {
           return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(puestoTrabajo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody PuestoTrabajo puestoTrabajo,BindingResult result, @PathVariable Long id){
       if (result.hasErrors()){
           return validar(result);
       }
        Optional<PuestoTrabajo> op = service.porId(id);
        if(op.isPresent()){
            PuestoTrabajo puestoTrabajoDb = op.get();
            puestoTrabajoDb.setNombre(puestoTrabajo.getNombre());
            puestoTrabajoDb.setDescripcion(puestoTrabajo.getDescripcion());
            puestoTrabajoDb.setTipoContrato(puestoTrabajo.getTipoContrato());
            puestoTrabajoDb.setRequisitosEducativos(puestoTrabajo.getRequisitosEducativos());
            puestoTrabajoDb.setExperiencia(puestoTrabajo.getExperiencia());
            puestoTrabajoDb.setCompetencias(puestoTrabajo.getCompetencias());
            puestoTrabajoDb.setHabilidades(puestoTrabajo.getHabilidades());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(puestoTrabajoDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<PuestoTrabajo> op = service.porId(id);
        if(op.isPresent()){
            service.eliminar(op.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> eliminarTodo(){
        List<PuestoTrabajo> op = service.listar();
        if (!op.isEmpty()){
            service.eliminarTodo();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> count(){
        List<PuestoTrabajo> op = service.listar();
        if (!op.isEmpty()){
            int count = service.count();
            return ResponseEntity.ok( count);
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result){
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


    @PutMapping("/asignar-empleado/{puestoId}")
    public ResponseEntity<?> asignarEmpleado(@RequestBody Empleado empleado, @PathVariable Long
            puestoId){
        Optional<Empleado> o;
        try {
            o=service.asignarEmpleado(empleado,puestoId);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No existe el usuario por " +
                            "el id o error en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/crear-empleado/{puestoId}")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado, @PathVariable Long
            puestoId){
        Optional<Empleado> o;
        try {
            o=service.crearEmpleado(empleado, puestoId);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No se pudo crear el usuario " +
                            "o error en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-empleado/{puestoId}")
    public ResponseEntity<?> eliminarEmpleado(@RequestBody Empleado empleado, @PathVariable Long
            puestoId){
        Optional<Empleado> o;
        try {
            o=service.eliminarEmpleado(empleado, puestoId);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No existe el usuario por " +
                            "el id o error en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }



}
