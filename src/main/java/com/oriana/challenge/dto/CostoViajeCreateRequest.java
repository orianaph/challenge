package com.oriana.challenge.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CostoViajeCreateRequest {

    @NotNull(message = "El ID del punto de origen es obligatorio")
    @Positive(message = "El ID del punto de origen debe ser positivo")
    private Long puntoOrigenId;

    @NotNull(message = "El ID del punto de destino es obligatorio")
    @Positive(message = "El ID del punto de destino debe ser positivo")
    private Long puntoDestinoId;

    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private int costo;

    public Long getPuntoOrigenId() {
        return puntoOrigenId;
    }

    public void setPuntoOrigenId(Long puntoOrigenId) {
        this.puntoOrigenId = puntoOrigenId;
    }

    public Long getPuntoDestinoId() {
        return puntoDestinoId;
    }

    public void setPuntoDestinoId(Long puntoDestinoId) {
        this.puntoDestinoId = puntoDestinoId;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
