package com.oriana.challenge.dto;

public class AcreditacionCreateRequest {

    private Long puntoVentaId;
    private Double importe;

    public Long getPuntoVentaId() {
        return puntoVentaId;
    }

    public void setPuntoVentaId(Long puntoVentaId) {
        this.puntoVentaId = puntoVentaId;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
