package com.oriana.challenge.controller;

import com.oriana.challenge.dto.AcreditacionCreateRequest;
import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.service.AcreditacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/acreditaciones")
@Validated
public class AcreditacionController {

    @Autowired
    AcreditacionService acreditacionService;


    @PostMapping("/create")
    public ResponseEntity<?> addAcreditacion(@Valid @RequestBody AcreditacionCreateRequest request) {
        Acreditacion result = acreditacionService.createAcreditacion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAcreditacionById(@PathVariable @Min(value = 1, message = "El ID debe ser mayor a 0") Long id){
        Acreditacion acreditacion = acreditacionService.getAcreditacionById(id);
        return ResponseEntity.ok(acreditacion);
    }


    @GetMapping("/puntoVenta/{puntoVentaid}")
    public ResponseEntity<?> getAcreditacionByPuntoVenta(@PathVariable("puntoVentaid") @Min(value = 1, message = "El ID del punto de venta debe ser mayor a 0") Long puntoVentaid){
        List<Acreditacion> acreditaciones = acreditacionService.getAcreditacionesByPuntoVentaId(puntoVentaid);
        return ResponseEntity.ok(acreditaciones);
    }




}
