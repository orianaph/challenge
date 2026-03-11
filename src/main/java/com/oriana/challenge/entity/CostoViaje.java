package com.oriana.challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"puntoOrigen_id", "puntoDestino_id"}
    )
)
public class CostoViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(index = 1)
    private Long costoId;

    @NotNull(message = "El punto de origen es obligatorio")
    @ManyToOne
    @JsonProperty(index = 2)
    private PuntoVenta puntoOrigen;

    @NotNull(message = "El punto de destino es obligatorio")
    @ManyToOne
    @JsonProperty(index = 3)
    private PuntoVenta puntoDestino;

    @Min(value = 1, message = "El costo debe ser mayor a 0")
    @JsonProperty(index = 4)
    private int costo;

    public CostoViaje(PuntoVenta a, PuntoVenta b, int costo) {

        this.puntoOrigen = a;
        this.puntoDestino = b;
        this.costo = costo;
    }


}


