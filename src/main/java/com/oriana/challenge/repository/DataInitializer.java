package com.oriana.challenge.repository;

import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.CostoViajeService;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(PuntoVentaService puntoVentaService) {
        return args -> {
            puntoVentaService.addPuntoVenta(new PuntoVenta("CABA"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("GBA_1"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("GBA_2"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Santa Fe"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Córdoba"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Misiones"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Salta"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Chubut"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Santa Cruz"));
            puntoVentaService.addPuntoVenta(new PuntoVenta("Catamarca"));
        };
    }


    @Bean
    CommandLineRunner loadDataCostos(CostoViajeService costoViajeService, PuntoVentaService pvs) {
        return args -> {
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(1L), pvs.getPuntoVentaById(2L), 2));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(1L), pvs.getPuntoVentaById(3L), 3));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(2L), pvs.getPuntoVentaById(3L), 5));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(2L), pvs.getPuntoVentaById(4L), 10));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(1L), pvs.getPuntoVentaById(4L), 11));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(4L), pvs.getPuntoVentaById(5L), 5));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(2L), pvs.getPuntoVentaById(5L), 14));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(6L), pvs.getPuntoVentaById(7L), 32));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(8L), pvs.getPuntoVentaById(9L), 11));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(10L), pvs.getPuntoVentaById(7L), 5));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(3L), pvs.getPuntoVentaById(8L), 10));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(5L), pvs.getPuntoVentaById(8L), 30));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(10L), pvs.getPuntoVentaById(5L), 5));
            costoViajeService.addCostoViaje(new CostoViaje(pvs.getPuntoVentaById(4L), pvs.getPuntoVentaById(6L), 6));
        };
    }



}
