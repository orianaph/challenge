package com.oriana.challenge.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AcreditacionCreateRequest {

    @NotNull(message = "El ID del punto de venta es obligatorio")
    @Positive(message = "El ID del punto de venta debe ser positivo")
    private Long puntoVentaId;

    @NotNull(message = "El importe es obligatorio")
    @Positive(message = "El importe debe ser positivo")
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
