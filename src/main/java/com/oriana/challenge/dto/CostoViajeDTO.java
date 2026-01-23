package com.oriana.challenge.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CostoViajeDTO {
    private Long puntoAId;
    private Long puntoBId;
    private int costo;
}
