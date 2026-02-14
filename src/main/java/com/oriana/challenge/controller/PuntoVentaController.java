package com.oriana.challenge.controller;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/puntoventa")
public class PuntoVentaController {

    @Autowired
    private PuntoVentaService puntoVentaService;

    //Trae lista de punto de ventas, utiliza try catch con excepcion generica. Devuelve ResponseEntity
    @GetMapping("/getall")
    public ResponseEntity<?> listPuntoVenta() {
        try {
            List<PuntoVenta> lista = puntoVentaService.getListaPuntoVenta();
            if (lista == null || lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la lista de puntos de venta: " + e.getMessage());
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPuntoVenta(@PathVariable(name = "id") long id) {
        try {
            PuntoVenta puntoVenta = puntoVentaService.getPuntoVentaById(id);
            return ResponseEntity.ok(puntoVenta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPuntoVenta(@Valid @RequestBody PuntoVenta puntoVenta) {
        try {
            PuntoVenta nuevoPunto = puntoVentaService.savePuntoVenta(puntoVenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPunto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear punto de venta: " + e.getMessage());
        }
    }

    //Try catch basico, logica de validacion en service, devuelve response entity
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePuntoVenta(@PathVariable long id) {
        try {
            puntoVentaService.deletePuntoVentaById(id);
            return ResponseEntity.ok("Punto de venta eliminado con id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Recibe un body y un path variable, valida que tengan el mismo id y lo guarda con los nuevos valores.
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePuntoVenta(@PathVariable long id,
                                                   @Valid @RequestBody PuntoVenta puntoVenta) {
        if (!Objects.equals(id, puntoVenta.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("ID en path y body deben ser iguales");
        }
        try {
            puntoVentaService.updatePuntoVentaById(puntoVenta);
            return ResponseEntity.ok("Punto de venta actualizado con id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


