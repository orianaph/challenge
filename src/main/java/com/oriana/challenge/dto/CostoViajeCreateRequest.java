package com.oriana.challenge.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostoViajeCreateRequest {

    @NotNull(message = "El ID del punto de origen es obligatorio")
    @Positive(message = "El ID del punto de origen debe ser positivo")
    private Long puntoOrigenId;

    @NotNull(message = "El ID del punto de destino es obligatorio")
    @Positive(message = "El ID del punto de destino debe ser positivo")
    private Long puntoDestinoId;

    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private int costo;

}
