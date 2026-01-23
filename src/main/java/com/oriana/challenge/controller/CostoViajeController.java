package com.oriana.challenge.controller;

import com.oriana.challenge.dto.CostoViajeDTO;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.CostoViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/costoviaje")
public class CostoViajeController {

    @Autowired
    private CostoViajeService costoViajeService;

    @GetMapping("/all")
    public List<CostoViaje> listCostoViaje() {
        return costoViajeService.getAllCostoViaje();
    }


    @PostMapping("/create")
    public CostoViaje addCostoViaje (@RequestBody CostoViajeDTO costoViajeDTO) {
        return costoViajeService.createCostoViaje(costoViajeDTO);
    }
}
