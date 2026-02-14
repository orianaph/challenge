package com.oriana.challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class CostoViaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(index = 1)
    private Long costoId;

    @ManyToOne
    @JsonProperty(index = 2)
    private PuntoVenta puntoOrigen;

    @ManyToOne
    @JsonProperty(index = 3)
    private PuntoVenta puntoDestino;

    @JsonProperty(index = 4)
    private int costo;

    public CostoViaje(PuntoVenta a, PuntoVenta b, int costo) {

        this.puntoOrigen = a;
        this.puntoDestino = b;
        this.costo = costo;
    }


}


