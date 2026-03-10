package com.oriana.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

// comment
@Entity
public class Acreditacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long puntoVentaId;
    private Date fechaReception;
    private Double importe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPuntoVentaId() {
        return puntoVentaId;
    }

    public void setPuntoVentaId(Long puntoVentaId) {
        this.puntoVentaId = puntoVentaId;
    }

    public Date getFechaReception() {
        return fechaReception;
    }

    public void setFechaReception(Date fechaReception) {
        this.fechaReception = fechaReception;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
