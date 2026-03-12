package com.oriana.challenge.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcreditacionCreateRequest {

    @NotNull(message = "El ID del punto de venta es obligatorio")
    @Positive(message = "El ID del punto de venta debe ser positivo")
    private Long puntoVentaId;

    @NotNull(message = "El importe es obligatorio")
    @Positive(message = "El importe debe ser positivo")
    private Double importe;


}
