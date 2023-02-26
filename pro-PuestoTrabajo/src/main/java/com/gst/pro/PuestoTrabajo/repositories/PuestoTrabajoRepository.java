package com.gst.pro.PuestoTrabajo.repositories;

import com.gst.pro.PuestoTrabajo.models.entity.PuestoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PuestoTrabajoRepository extends JpaRepository<PuestoTrabajo, Long> {

}
