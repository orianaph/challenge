package com.oriana.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Acreditacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de recepción es obligatoria")
    @PastOrPresent(message = "La fecha de recepción no puede ser futura")
    private Date fechaReception;

    @NotNull(message = "El importe es obligatorio")
    @Positive(message = "El importe debe ser positivo")
    private Double importe;

    @NotNull(message = "El punto de venta es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "punto_venta_id")
    private PuntoVenta puntoVenta;
}
