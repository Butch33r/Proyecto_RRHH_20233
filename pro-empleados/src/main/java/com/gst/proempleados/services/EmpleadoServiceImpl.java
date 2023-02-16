package com.gst.proempleados.services;


import com.gst.proempleados.models.entity.Empleado;
import com.gst.proempleados.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")

    @Autowired
    private EmpleadoRepository repository;



    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listar() {
        //return (List<Curso>)repository.findAll();
        return (List<Empleado>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Empleado guardar(Empleado curso) {
        String codigo = setCodigo(curso);// Lógica para generar el código personalizado
        curso.setCodigo(codigo);

        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public String setCodigo(Empleado empleado) {

        String nombreNormalizado = Normalizer.normalize(empleado.getNombre().charAt(0) + "", Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        String apellidoNormalizado = Normalizer.normalize(empleado.getApellido(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();



        // Obtener el día del mes actual
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Obtener los dos últimos dígitos del año actual
        int anio = calendario.get(Calendar.YEAR);
        String ultimosDosDigitosAnio = String.format("%02d", anio % 100);

        // Crear el id con los valores obtenidos
        String id = nombreNormalizado+apellidoNormalizado + String.format("%02d", dia) + "-" + ultimosDosDigitosAnio;
        /*Primera versión*/
        //String anioActual = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        //String id = apellido.replaceAll("\\s+","").substring(0, 3).toUpperCase() +
        //      anioActual;
        return id;
    }
}

