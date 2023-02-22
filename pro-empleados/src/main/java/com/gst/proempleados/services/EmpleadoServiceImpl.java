package com.gst.proempleados.services;


import com.gst.proempleados.clients.DepartamentoClienteRest;
import com.gst.proempleados.models.entity.Empleado;
import com.gst.proempleados.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository repository;
    @Autowired
    private DepartamentoClienteRest client;
    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listar() {
        return (List<Empleado>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Empleado guardar(Empleado empleado) {

        String codigo = setCodigo(empleado);// Lógica para generar el código personalizado
        empleado.setCodigo(codigo);

        return repository.save(empleado);
    }

   /* @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> listarPorIds(Iterable<Long> ids) {
        return (List<Empleado>) repository.findAllById(ids);
    }

    @Override
    public Optional<Empleado> porEmail(String email) {
        return repository.findByEmail(email);
    }


    public void eliminar(Long id){
        repository.deleteById(id);
        //client.eliminarCursoUsuarioPorId(id);

    }

    @Override
    public List<Empleado> listarPorGenero(String genero) {
        return repository.findByGenero(genero);
    }

    @Override
    public List<Empleado> listarPorApellido(String apellido) {
        return repository.findByApellido(apellido);
    }

    @Override
    public List<Empleado> listarPorNacimientolistarPorNacimiento(LocalDate fechaInicio, LocalDate fechaFin) {

        if (fechaInicio != null && fechaFin != null) {
            // Se busca por fecha completa
            return repository.findByFechaNacimientoBetween(fechaInicio, fechaFin);
        } else if (fechaInicio != null && fechaInicio.getYear() != 0 && fechaFin != null && fechaFin.getYear() != 0) {
            // Se busca por años
            LocalDate inicio = LocalDate.of(fechaInicio.getYear(), 1, 1);
            LocalDate fin = LocalDate.of(fechaFin.getYear(), 12, 31);
            return repository.findByFechaNacimientoBetween(inicio, fin);
        } else if (fechaInicio != null && fechaInicio.getYear() != 0 && fechaInicio.getMonth() != null && fechaInicio.getMonthValue() != 0
                && fechaFin != null && fechaFin.getYear() != 0 && fechaFin.getMonth() != null && fechaFin.getMonthValue() != 0) {
            // Se busca por mes y año
            LocalDate inicio = LocalDate.of(fechaInicio.getYear(), fechaInicio.getMonth(), 1);
            LocalDate fin = LocalDate.of(fechaFin.getYear(), fechaFin.getMonth(), fechaFin.lengthOfMonth());
            return repository.findByFechaNacimientoBetween(inicio, fin);
        } else {
            // Caso de error
            throw new IllegalArgumentException("Fechas inválidas");
        }
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
