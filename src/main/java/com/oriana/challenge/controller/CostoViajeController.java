package com.oriana.challenge.controller;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.CostoViajeService;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public CostoViaje addCostoViaje(@RequestBody CostoViaje costoViaje) throws Exception {
        return costoViajeService.createCostoViaje(costoViaje);
    }

    /**
     * Obtener todos los costos asociados a un punto de venta
     */
    @GetMapping("/puntos-venta/{puntoId}")
    public ResponseEntity<?> getCostosPorPuntoVenta(@PathVariable Long puntoId) {
        try {
            List<CostoViaje> costos = costoViajeService.getCostosPorPuntoVenta(puntoId);

            return ResponseEntity.ok(costos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{puntoA}/{puntoB}")
    public ResponseEntity<?> eliminarCostoViaje(@PathVariable Long puntoA, @PathVariable Long puntoB) {
        costoViajeService.deleteCostoViaje(puntoA, puntoB);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/min-ruta/{origen}/{destino}")
    public RutaMinimaResponse calcularRuta(
            @PathVariable Long origen,
            @PathVariable Long destino) {

        return costoViajeService.calcularRutaMinima(origen, destino);
    }


}
