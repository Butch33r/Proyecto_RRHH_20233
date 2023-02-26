package com.gst.pro.PuestoTrabajo.services;

import com.gst.pro.PuestoTrabajo.clients.PuestoTrabajoClients;
import com.gst.pro.PuestoTrabajo.models.Empleado;
import com.gst.pro.PuestoTrabajo.models.entity.PuestoTrabajo;
import com.gst.pro.PuestoTrabajo.models.entity.PuestoTrabajoEmpleado;
import com.gst.pro.PuestoTrabajo.repositories.PuestoTrabajoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuestoTrabajoServiceImpl implements  PuestoTrabajoService{

    @Autowired
    public PuestoTrabajoRepository repository;

    @Autowired
    public PuestoTrabajoClients client;
    @Override
    @Transactional(readOnly = true)
    public List<PuestoTrabajo> listar() {
        return (List<PuestoTrabajo>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PuestoTrabajo> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public PuestoTrabajo guardar(PuestoTrabajo puestoTrabajo) {
        return repository.save(puestoTrabajo);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarTodo() {
        repository.deleteAll();
    }

    @Override
    @Transactional
    public int count() {
        return (int) repository.count();
    }

    @Override
    public Optional<Empleado> asignarEmpleado(Empleado empleado, Long puestoId) {
        Optional<PuestoTrabajo> o = repository.findById(puestoId);
        if(o.isPresent()){
            Empleado empleadoMsvc = client.detalle(empleado.getId());
           PuestoTrabajo puestoTrabajo = o.get();
            PuestoTrabajoEmpleado puestoTrabajoEmpleado = new PuestoTrabajoEmpleado();

            puestoTrabajoEmpleado.setEmpleadoId(empleadoMsvc.getId());
            puestoTrabajo.addPuestosTrabajoEmpledo(puestoTrabajoEmpleado);
            repository.save(puestoTrabajo);
            return Optional.of(empleadoMsvc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Empleado> crearEmpleado(Empleado empleado, Long puestoId) {
        Optional<PuestoTrabajo> o = repository.findById(puestoId);
        if(o.isPresent()){
            Empleado empleadoMsvc = client.crear(empleado);
            PuestoTrabajo puestoTrabajo = o.get();
            PuestoTrabajoEmpleado puestoTrabajoEmpleado = new PuestoTrabajoEmpleado();

            puestoTrabajoEmpleado.setEmpleadoId(empleadoMsvc.getId());
            puestoTrabajo.addPuestosTrabajoEmpledo(puestoTrabajoEmpleado);
            repository.save(puestoTrabajo);
            return Optional.of(empleadoMsvc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Empleado> eliminarEmpleado(Empleado empleado, Long puestoId) {
        Optional<PuestoTrabajo> o = repository.findById(puestoId);
        if(o.isPresent()){
            Empleado empleadoMsvc = client.detalle(empleado.getId());
            PuestoTrabajo puestoTrabajo = o.get();
            PuestoTrabajoEmpleado puestoTrabajoEmpleado = new PuestoTrabajoEmpleado();

            puestoTrabajoEmpleado.setEmpleadoId(empleadoMsvc.getId());
            puestoTrabajo.removePuestosTrabajoEmpleado(puestoTrabajoEmpleado);
            repository.save(puestoTrabajo);
            return Optional.of(empleadoMsvc);
        }
        return Optional.empty();
    }
}
