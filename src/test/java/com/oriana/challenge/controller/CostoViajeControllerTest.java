package com.oriana.challenge.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.service.CostoViajeService;
import com.oriana.challenge.service.PuntoVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CostoViajeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CostoViajeService costoViajeService;

    @Mock
    private PuntoVentaService puntoVentaService;

    @InjectMocks
    private CostoViajeController costoViajeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(costoViajeController).build();
    }

    @Test
    void listCostoViaje_shouldReturnList() throws Exception {
        PuntoVenta punto1 = new PuntoVenta();
        punto1.setId(1L);
        punto1.setNombre("Punto 1");
        PuntoVenta punto2 = new PuntoVenta();
        punto2.setId(2L);
        punto2.setNombre("Punto 2");

        CostoViaje costo1 = new CostoViaje(punto1, punto2, 100);
        costo1.setCostoId(1L);
        CostoViaje costo2 = new CostoViaje(punto1, punto2, 200);
        costo2.setCostoId(2L);

        List<CostoViaje> costos = Arrays.asList(costo1, costo2);

        when(costoViajeService.getAllCostoViaje()).thenReturn(costos);

        mockMvc.perform(get("/costoviaje/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].costoId").value(1L))
                .andExpect(jsonPath("$[1].costoId").value(2L));
    }

    @Test
    void createCostoViaje_shouldReturnCostoViaje() throws Exception {
        PuntoVenta punto1 = new PuntoVenta();
        punto1.setId(1L);
        punto1.setNombre("Punto 1");
        PuntoVenta punto2 = new PuntoVenta();
        punto2.setId(2L);
        punto2.setNombre("Punto 2");

        CostoViaje costoViaje = new CostoViaje(punto1, punto2, 100);
        costoViaje.setCostoId(1L);

        when(costoViajeService.createCostoViaje(any(CostoViaje.class))).thenReturn(costoViaje);

        mockMvc.perform(post("/costoviaje/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(costoViaje)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.costoId").value(1L))
                .andExpect(jsonPath("$.costo").value(100));
    }

    @Test
    void createCostoViaje_invalidInput_shouldReturn400() throws Exception {
        CostoViaje costoViaje = new CostoViaje();
        costoViaje.setPuntoOrigen(null); // Invalid

        when(costoViajeService.createCostoViaje(any(CostoViaje.class)))
                .thenThrow(new com.oriana.challenge.exception.InvalidInputException("Los puntos de venta no pueden ser nulos"));

        mockMvc.perform(post("/costoviaje/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(costoViaje)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Los puntos de venta no pueden ser nulos"));
    }

    @Test
    void getCostosPorPuntoVenta_shouldReturnList() throws Exception {
        PuntoVenta punto1 = new PuntoVenta();
        punto1.setId(1L);
        punto1.setNombre("Punto 1");
        PuntoVenta punto2 = new PuntoVenta();
        punto2.setId(2L);
        punto2.setNombre("Punto 2");

        CostoViaje costo1 = new CostoViaje(punto1, punto2, 100);
        costo1.setCostoId(1L);
        CostoViaje costo2 = new CostoViaje(punto1, punto2, 200);
        costo2.setCostoId(2L);

        List<CostoViaje> costos = Arrays.asList(costo1, costo2);

        when(costoViajeService.getCostosPorPuntoVenta(1L)).thenReturn(costos);

        mockMvc.perform(get("/costoviaje/puntos-venta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].costoId").value(1L))
                .andExpect(jsonPath("$[1].costoId").value(2L));
    }

    @Test
    void deleteCostoViaje_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/costoviaje/1/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Costo de viaje eliminado entre puntos 1 y 2"));
    }

   /* @Test
    void deleteCostoViaje_notFound_shouldReturn404() throws Exception {
        when(costoViajeService.deleteCostoViaje(1L, 2L))
                .thenThrow(new com.oriana.challenge.exception.ResourceNotFoundException("No existe un costo entre esos puntos de venta"));

        mockMvc.perform(delete("/costoviaje/1/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un costo entre esos puntos de venta"));
    }*/

    @Test
    void calcularRuta_shouldReturnRutaMinimaResponse() throws Exception {
        RutaMinimaResponse response = new RutaMinimaResponse(150, Arrays.asList(1L, 3L, 2L));

        when(costoViajeService.calcularRutaMinima(1L, 2L)).thenReturn(response);

        mockMvc.perform(get("/costoviaje/min-ruta/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(150))
                .andExpect(jsonPath("$.path[0]").value(1L))
                .andExpect(jsonPath("$.path[1]").value(3L))
                .andExpect(jsonPath("$.path[2]").value(2L));
    }
}