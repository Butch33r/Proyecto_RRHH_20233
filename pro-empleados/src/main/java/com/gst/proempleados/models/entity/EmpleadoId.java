package com.gst.proempleados.models.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpleadoId implements Serializable {
    private String id;

    public EmpleadoId() {}

    public EmpleadoId(String id) {
        this.id = id;
    }


}