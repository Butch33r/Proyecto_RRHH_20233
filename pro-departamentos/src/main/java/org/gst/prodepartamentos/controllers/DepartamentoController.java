package org.gst.prodepartamentos.controllers;

import feign.FeignException;

import org.gst.prodepartamentos.models.Empleado;
import org.gst.prodepartamentos.models.entity.Departamento;
import org.gst.prodepartamentos.services.DepartamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departamento")
public class DepartamentoController {
    @Autowired
    private DepartamentoService service;

    @GetMapping
    public ResponseEntity<List<Departamento>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Departamento> op = service.porId(id);
        if(op.isPresent()){
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Departamento curso){
        Departamento cursoDb = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Departamento curso, @PathVariable Long id){
        Optional<Departamento> op = service.porId(id);
        if(op.isPresent()){
            Departamento curDb = op.get();
            curDb.setNombre(curso.getNombre());
            curDb.setDescripcion(curso.getDescripcion());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Departamento> op = service.porId(id);
        if(op.isPresent()){
            service.eliminar(op.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Empleado usuario, @PathVariable Long cursoId){
        Optional<Empleado> o;
        try{
            o=service.asignarEmpleado(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No existe usuario con ese id "+
                            e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Empleado usuario, @PathVariable Long cursoId){
        Optional<Empleado> o;
        try{
            o=service.crearEmpleado(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No se pudo crear el usuario "+
                            "o falla en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarEmpleado(@RequestBody Empleado usuario, @PathVariable Long cursoId){
        Optional<Empleado> o;
        try{
            o=service.eliminarEmpleado(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No existe usuario con ese id "+
                            " o error en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/curUsers/{id}")
    public ResponseEntity<?> detalleCurUsers(@PathVariable Long id){
        Optional<Departamento> op = service.porIdConDepartamento(id);
        if(op.isPresent()){
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-curUser/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.eliminarDepartamentoEmpleadoPorId(id);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/empleados-por-genero/{id}")
    public ResponseEntity<?> obtenerEmpleadosPorGenero(@PathVariable Long id,@RequestParam(required = false)  String genero) {

        if (genero == null || genero.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El 'género'  no puede estar mal definido vacío.");
        }

        try {

            Optional<Departamento> op = service.listarEmpleadosPorGenero(id, genero);
            if (op.isPresent()) {
                return ResponseEntity.ok(op.get());
            }
            return ResponseEntity.notFound().build();
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }

    @GetMapping("/empleados-por-fechaNacimiento/{id}")
    public ResponseEntity<?> obtenerEmpleadosPorFecha(@PathVariable Long id,@RequestParam String fechaIni,@RequestParam String fechaFi) {
        try {
            Optional<Departamento> op = service.listarPorNacimientolistar(id,fechaIni,fechaFi);
            if(op.isPresent()){
                return ResponseEntity.ok(op.get());
            }
            return ResponseEntity.notFound().build();
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }
    }


}
