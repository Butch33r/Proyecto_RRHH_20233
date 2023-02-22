package com.gst.proempleados.controllers;

import com.gst.proempleados.models.entity.Empleado;
import com.gst.proempleados.services.EmpleadoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService service;
    @GetMapping
    public List<Empleado> listar(){
            return service.listar();
        }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<Empleado> opti = service.porId(id);
        if(opti.isPresent()){
            return ResponseEntity.ok(opti.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@Valid @RequestBody Empleado Empleado, BindingResult result){
        if(service.porEmail(Empleado.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections
                    .singletonMap("Mensaje","Ya existe un Empleado con ese email"));
        }
        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(Empleado));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@Valid @RequestBody Empleado Empleado, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Empleado> opti = service.porId(id);
        if(opti.isPresent()){
            Empleado EmpleadoDb = opti.get();
            if(!Empleado.getEmail().equalsIgnoreCase(EmpleadoDb.getEmail())
                    && service.porEmail(Empleado.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections
                        .singletonMap("Mensaje","Ya existe un Empleado con ese email"));
            }
            EmpleadoDb.setNombre(Empleado.getNombre());
            EmpleadoDb.setEmail(Empleado.getEmail());
            EmpleadoDb.setApellido(Empleado.getApellido());
            EmpleadoDb.setTelefono(Empleado.getTelefono());
            EmpleadoDb.setFechaNacimiento(Empleado.getFechaNacimiento());
            EmpleadoDb.setGenero(Empleado.getGenero());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(EmpleadoDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eleminar (@PathVariable Long id){
        Optional<Empleado> opti = service.porId(id);
        if(opti.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/empleados-por-genero")
    public ResponseEntity<?> listarPorGenero(@RequestParam(required = false) String genero) {

        if (genero == null) {
            return ResponseEntity.badRequest().body("El parámetro 'genero' es requerido.");
        }
            return ResponseEntity.ok(service.listarPorGenero(genero));

    }

    @GetMapping("/empleados-por-apellido")
    public ResponseEntity<?> listarPorApellido(@RequestParam(required = false) String apellido) {

        if (apellido == null) {
            return ResponseEntity.badRequest().body("El parámetro 'apellido' es requerido.");
        }
        return ResponseEntity.ok(service.listarPorApellido(apellido));

    }

    @GetMapping("/empleados-por-fechaNacimiento")
    public ResponseEntity<?> listarPorFechaNacimiento(@RequestParam String fechaIn, @RequestParam String fechaFin) {
        LocalDate fechaInicio, fechaFi;
        try {
            fechaInicio = LocalDate.parse(fechaIn);
            fechaFi = LocalDate.parse(fechaFin);
        } catch (DateTimeParseException e) {
            try {
                fechaInicio = parseDateWithYear(fechaIn);
                fechaFi = parseDateWithYear(fechaFin);
            } catch (DateTimeParseException e1) {
                try {
                    fechaInicio = parseDateWithYearAndMonth(fechaIn);
                    fechaFi = parseDateWithYearAndMonth(fechaFin);
                } catch (DateTimeParseException e2) {
                    return ResponseEntity.badRequest().body("Las fechas ingresadas no son válidas.");
                }
            }
        }

        if (fechaInicio.isAfter(fechaFi)) {
            return ResponseEntity.badRequest().body("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        List<Empleado> empleados = service.listarPorNacimientolistarPorNacimiento(fechaInicio, fechaFi);
        return ResponseEntity.ok(empleados);
    }

    private LocalDate parseDateWithYear(String dateStr) {
        return LocalDate.parse(dateStr + "-01-01", DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private LocalDate parseDateWithYearAndMonth(String dateStr) {
        return LocalDate.parse(dateStr + "-01", DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),"El campo "+err.getField()
                    +" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
