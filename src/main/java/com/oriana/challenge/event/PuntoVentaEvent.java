package com.oriana.challenge.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PuntoVentaEvent(
        @JsonProperty("id")
        Long id,
        
        @JsonProperty("nombre")
        String nombre,
        
        @JsonProperty("eventType")
        String eventType // "CREATED" or "DELETED"
) {}
