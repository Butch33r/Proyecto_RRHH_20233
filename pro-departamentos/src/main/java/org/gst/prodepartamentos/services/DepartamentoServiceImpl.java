package org.gst.prodepartamentos.services;


import org.gst.prodepartamentos.clients.EmpleadoClientRest;
import org.gst.prodepartamentos.models.Empleado;
import org.gst.prodepartamentos.models.entity.Departamento;
import org.gst.prodepartamentos.models.entity.DepartamentoEmpleado;
import org.gst.prodepartamentos.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    @Autowired
    private DepartamentoRepository repository;
    @Autowired
    private EmpleadoClientRest client;
    @Override
    @Transactional(readOnly = true)
    public List<Departamento> listar() {
        return (List<Departamento>)repository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> porId(Long id) {
        return repository.findById(id);
    }
    @Override
    @Transactional
    public Departamento guardar(Departamento curso) {
        return repository.save(curso);
    }
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> listarEmpleadosPorGenero(Long id,String genero) {
        Optional<Departamento> o = repository.findById(id);
        if(o.isPresent()){
            Departamento curso = o.get();
            if(!curso.getDepartamentoEmpleados().isEmpty()){
                List<Long> ids = curso.getDepartamentoEmpleados().stream().map(cu -> cu.getEmpleadoId())
                        .collect(Collectors.toList());


                List<Empleado> empleadosPorFechaNacimiento = client.obtenerEmpleadosPorGenero(genero);


                List<Empleado> empleadosEnDepartamentoConGenero = empleadosPorFechaNacimiento.stream()
                        .filter(empleado -> ids.contains(empleado.getId()))
                        .collect(Collectors.toList());

                curso.setEmpleados(empleadosEnDepartamentoConGenero);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> listarPorNacimientolistar(Long id,String fechaInicio, String fechaFin) {
        Optional<Departamento> o = repository.findById(id);
        if(o.isPresent()){
            Departamento departamento = o.get();
            if(!departamento.getDepartamentoEmpleados().isEmpty()){
                List<Long> idsEmpleadosDepartamento = departamento.getDepartamentoEmpleados().stream().map(cu -> cu.getEmpleadoId())
                        .collect(Collectors.toList());

                // Llamar al cliente de Empleado para obtener todos los empleados con la fecha de nacimiento dada
                List<Empleado> empleadosPorFechaNacimiento = client.listarPorNacimientolistar(fechaInicio,fechaFin);


                // Filtrar los empleados obtenidos manteniendo solo aquellos en el departamento dado
                List<Empleado> empleadosEnDepartamentoConFechaNacimiento = empleadosPorFechaNacimiento.stream()
                        .filter(empleado -> idsEmpleadosDepartamento.contains(empleado.getId()))
                        .collect(Collectors.toList());

                departamento.setEmpleados(empleadosEnDepartamentoConFechaNacimiento);
            }
            return Optional.of(departamento);
        }
        return Optional.empty();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> porIdConDepartamento(Long id) {
            Optional<Departamento> o = repository.findById(id);
            if(o.isPresent()){
                Departamento curso = o.get();
                if(!curso.getDepartamentoEmpleados().isEmpty()){
                    List<Long> ids = curso.getDepartamentoEmpleados().stream().map(cu -> cu.getEmpleadoId())
                            .collect(Collectors.toList());
                    List<Empleado> usuarios = client.obtenerAlumnosPorCurso(ids);
                    curso.setEmpleados(usuarios);
                }
                return Optional.of(curso);
            }
            return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarDepartamentoEmpleadoPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    //Implementacion de los metodos remotos
    @Override
    @Transactional
    public Optional<Empleado> asignarEmpleado(Empleado usuario, Long cursoId) {
        Optional<Departamento> o = repository.findById(cursoId);
        if(o.isPresent()){
            Empleado usuarioMsvc = client.detalle(usuario.getId());

            Departamento curso = o.get();
            //CursoUsuario cursoUsuario = new CursoUsuario();
            DepartamentoEmpleado cursoUsuario = new DepartamentoEmpleado();

            cursoUsuario.setEmpleadoId(usuarioMsvc.getId());

            //curso.addCursoUsuario(cursoUsuario);
            curso.addDepartamentoEmpleados(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
    @Override
    @Transactional
    public Optional<Empleado> crearEmpleado(Empleado usuario, Long cursoId) {
        Optional<Departamento> o = repository.findById(cursoId);
        if(o.isPresent()){
            Empleado usuarioMsvc = client.crear(usuario);

            Departamento curso = o.get();
            DepartamentoEmpleado cursoUsuario = new DepartamentoEmpleado();
            cursoUsuario.setEmpleadoId(usuarioMsvc.getId());

            curso.addDepartamentoEmpleados(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
    @Override
    @Transactional
    public Optional<Empleado> eliminarEmpleado(Empleado usuario, Long cursoId) {
        Optional<Departamento> o = repository.findById(cursoId);
        if(o.isPresent()){
            Empleado usuarioMsvc = client.detalle(usuario.getId());

            Departamento curso = o.get();
            DepartamentoEmpleado cursoUsuario = new DepartamentoEmpleado();
            cursoUsuario.setEmpleadoId(usuarioMsvc.getId());

            curso.removeDepartamentoEmpleados(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
    /*
    @Override
    public List<Empleado> listarEmpleadosPorGenero(String genero) {
        List<Empleado> empleados = new ArrayList<>();
        for (Departamento departamento : repository.findAll()) {
            for (DepartamentoEmpleado departamentoEmpleado : departamento.getDepartamentoEmpleados()) {
                Empleado empleado = client.detalle(departamentoEmpleado.getEmpleadoId());
                if (empleado.getGenero().equals(genero)) {
                    empleados.add(empleado);
                }
            }
        }
        return empleados;
    }*/


}
