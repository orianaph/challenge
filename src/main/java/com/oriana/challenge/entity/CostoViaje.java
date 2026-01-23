package com.oriana.challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonPropertyOrder({ "costoId", "puntoA", "puntoB", "costo" })
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
    private PuntoVenta puntoA;

    @ManyToOne
    @JsonProperty(index = 3)
    private PuntoVenta puntoB;

    @JsonProperty(index = 4)
    private int costo;

    public CostoViaje(PuntoVenta a, PuntoVenta b, int costo) {
        this.puntoA = a;
        this.puntoB = b;
        this.costo = costo;
    }


}


