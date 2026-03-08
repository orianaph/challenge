package com.oriana.challenge.controller;

import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.service.AcreditacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acreditaciones")
public class AcreditacionController {

    @Autowired
    AcreditacionService acreditacionService;


    @PostMapping("/create")
    public ResponseEntity<?> addAcreditacion(@RequestBody Acreditacion acreditacion) {
        try {
            Acreditacion result = acreditacionService.createAcreditacion(acreditacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAcreditacionById(@PathVariable Long id){
        try {
            Acreditacion acreditacion = acreditacionService.getAcreditacionById(id);

            return ResponseEntity.ok(acreditacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/puntoVenta/{puntoVentaid}")
    public ResponseEntity<?> getAcreditacionByPuntoVenta(@PathVariable("puntoVentaid") Long puntoVentaid){
        try {
            List<Acreditacion> acreditaciones = acreditacionService.getAcreditacionesByPuntoVentaId(puntoVentaid);

            return ResponseEntity.ok(acreditaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




}
