package com.gst.proempleados.repositories;

import com.gst.proempleados.models.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByGenero(String genero);

    List<Empleado> findByApellido(String genero);

    List<Empleado> findByFechaNacimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
