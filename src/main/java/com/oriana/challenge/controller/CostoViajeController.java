package com.oriana.challenge.controller;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.service.CostoViajeService;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("/costoviaje")
public class CostoViajeController {

    @Autowired
    private CostoViajeService costoViajeService;

    @Autowired
    PuntoVentaService puntoVentaService;

    @GetMapping("/all")
    public List<CostoViaje> listCostoViaje() {
        return costoViajeService.getAllCostoViaje();
    }

    @PostMapping("/create")
    public CostoViaje addCostoViaje(@Valid @RequestBody CostoViaje costoViaje) {
        return costoViajeService.createCostoViaje(costoViaje);
    }

    /**
     * Obtener todos los costos asociados a un punto de venta
     */
    @GetMapping("/puntos-venta/{puntoId}")
    public ResponseEntity<?> getCostosPorPuntoVenta(@PathVariable @Min(value = 1, message = "El ID del punto de venta debe ser mayor a 0") Long puntoId) {
        List<CostoViaje> costos = costoViajeService.getCostosPorPuntoVenta(puntoId);
        return ResponseEntity.ok(costos);
    }

    @DeleteMapping("/{puntoA}/{puntoB}")
    public ResponseEntity<?> eliminarCostoViaje(@PathVariable @Min(value = 1, message = "El ID del punto A debe ser mayor a 0") Long puntoA,
                                                @PathVariable @Min(value = 1, message = "El ID del punto B debe ser mayor a 0") Long puntoB) {
        costoViajeService.deleteCostoViaje(puntoA, puntoB);
        return ResponseEntity.ok("Costo de viaje eliminado entre puntos " + puntoA + " y " + puntoB);
    }


    @GetMapping("/min-ruta/{origen}/{destino}")
    public RutaMinimaResponse calcularRuta(
            @PathVariable @Min(value = 1, message = "El ID de origen debe ser mayor a 0") Long origen,
            @PathVariable @Min(value = 1, message = "El ID de destino debe ser mayor a 0") Long destino) {

        return costoViajeService.calcularRutaMinima(origen, destino);
    }


}
