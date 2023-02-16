package org.gst.prodepartamentos.services;


import org.gst.prodepartamentos.entity.Departamento;
import org.gst.prodepartamentos.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {
    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")

    @Autowired
    private DepartamentoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> listar() {
        //return (List<Curso>)repository.findAll();
        return (List<Departamento>) repository.findAll();
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


}

