package com.oriana.challenge.controller;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puntoventa")
public class PuntoVentaController {

    @Autowired
    private PuntoVentaService puntoVentaService;

    @GetMapping("/getall")
    public List<PuntoVenta> listPuntoVenta() {
        return puntoVentaService.getListaPuntoVenta();
    }

    @GetMapping("/get/{id}")
    public PuntoVenta getPuntoVenta(@PathVariable(name = "id") long id) {
        return puntoVentaService.getPuntoVentaById(id);
    }

    @PostMapping("/add")
    public PuntoVenta addPuntoVenta(@RequestBody PuntoVenta puntoVenta) {
        return puntoVentaService.savePuntoVenta(puntoVenta);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePuntoVenta(@PathVariable long id) {
        puntoVentaService.deletePuntoVentaById(id);
        return "Punto de venta eliminado con id: " + id;
    }

    @PutMapping("/update/{id}")
    public String updatePuntoVenta(@RequestBody PuntoVenta puntoVenta) {
        puntoVentaService.updatePuntoVentaById(puntoVenta);
        return "Punto de venta actualizado con id: " + (puntoVenta.getId());
    }

    //Agregar update, con validaciones, que el id exista
}
