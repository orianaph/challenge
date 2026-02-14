package com.oriana.challenge.repository;

import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.CostoViajeService;
import com.oriana.challenge.service.PuntoVentaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner loadData(CostoViajeService costoViajeService, PuntoVentaService pvs) {
        return args -> {
            try {
                // Check if data already exists
                if (pvs.getListaPuntoVenta().isEmpty()) {
                    logger.info("Initializing database with default data...");
                    
                    // Initialize PuntoVenta objects first and store them
                    PuntoVenta p1 = pvs.savePuntoVenta(new PuntoVenta("CABA"));
                    PuntoVenta p2 = pvs.savePuntoVenta(new PuntoVenta("GBA_1"));
                    PuntoVenta p3 = pvs.savePuntoVenta(new PuntoVenta("GBA_2"));
                    PuntoVenta p4 = pvs.savePuntoVenta(new PuntoVenta("Santa Fe"));
                    PuntoVenta p5 = pvs.savePuntoVenta(new PuntoVenta("Córdoba"));
                    PuntoVenta p6 = pvs.savePuntoVenta(new PuntoVenta("Misiones"));
                    PuntoVenta p7 = pvs.savePuntoVenta(new PuntoVenta("Salta"));
                    PuntoVenta p8 = pvs.savePuntoVenta(new PuntoVenta("Chubut"));
                    PuntoVenta p9 = pvs.savePuntoVenta(new PuntoVenta("Santa Cruz"));
                    PuntoVenta p10 = pvs.savePuntoVenta(new PuntoVenta("Catamarca"));

                    // Load CostoViaje data using actual references
                    costoViajeService.createCostoViaje(new CostoViaje(p1, p2, 2));
                    costoViajeService.createCostoViaje(new CostoViaje(p1, p3, 3));
                    costoViajeService.createCostoViaje(new CostoViaje(p2, p3, 5));
                    costoViajeService.createCostoViaje(new CostoViaje(p2, p4, 10));
                    costoViajeService.createCostoViaje(new CostoViaje(p1, p4, 11));
                    costoViajeService.createCostoViaje(new CostoViaje(p4, p5, 5));
                    costoViajeService.createCostoViaje(new CostoViaje(p2, p5, 14));
                    costoViajeService.createCostoViaje(new CostoViaje(p6, p7, 32));
                    costoViajeService.createCostoViaje(new CostoViaje(p8, p9, 11));
                    costoViajeService.createCostoViaje(new CostoViaje(p10, p7, 5));
                    costoViajeService.createCostoViaje(new CostoViaje(p3, p8, 10));
                    costoViajeService.createCostoViaje(new CostoViaje(p5, p8, 30));
                    costoViajeService.createCostoViaje(new CostoViaje(p10, p5, 5));
                    costoViajeService.createCostoViaje(new CostoViaje(p4, p6, 6));
                    
                    logger.info("Database initialized successfully!");
                } else {
                    logger.info("Database already contains data. Skipping initialization.");
                }
            } catch (Exception e) {
                logger.warn("Failed to initialize database. This may occur if the database is unavailable. Error: {}", e.getMessage());
                logger.debug("Full error trace:", e);
            }
        };
    }



}
