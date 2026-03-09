package com.oriana.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.PuntoVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PuntoVentaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private PuntoVentaService puntoVentaService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void listPuntoVenta_shouldReturnList() throws Exception {
        PuntoVenta pv1 = new PuntoVenta();
        pv1.setId(1L);
        pv1.setNombre("Punto 1");

        PuntoVenta pv2 = new PuntoVenta();
        pv2.setId(2L);
        pv2.setNombre("Punto 2");

        List<PuntoVenta> puntos = Arrays.asList(pv1, pv2);

        when(puntoVentaService.getListaPuntoVenta()).thenReturn(puntos);

        mockMvc.perform(get("/puntoventa/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Punto 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("Punto 2"));
    }

    @Test
    void listPuntoVenta_emptyList_shouldReturnNoContent() throws Exception {
        when(puntoVentaService.getListaPuntoVenta()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/puntoventa/getall"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPuntoVenta_shouldReturnPuntoVenta() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setId(1L);
        pv.setNombre("Punto 1");

        when(puntoVentaService.getPuntoVentaById(1L)).thenReturn(pv);

        mockMvc.perform(get("/puntoventa/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Punto 1"));
    }

    @Test
    void getPuntoVenta_notFound_shouldReturn404() throws Exception {
        when(puntoVentaService.getPuntoVentaById(1L))
                .thenThrow(new com.oriana.challenge.exception.ResourceNotFoundException("PuntoVenta con id: 1 no encontrado"));

        mockMvc.perform(get("/puntoventa/get/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("PuntoVenta con id: 1 no encontrado"));
    }

    @Test
    void addPuntoVenta_shouldReturnCreated() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setId(1L);
        pv.setNombre("Nuevo Punto");

        when(puntoVentaService.savePuntoVenta(any(PuntoVenta.class))).thenReturn(pv);

        mockMvc.perform(post("/puntoventa/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Nuevo Punto"));
    }

    @Test
    void addPuntoVenta_alreadyExists_shouldReturn409() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setNombre("Existing Punto");

        when(puntoVentaService.savePuntoVenta(any(PuntoVenta.class)))
                .thenThrow(new com.oriana.challenge.exception.ResourceAlreadyExistsException("Punto de venta con nombre: Existing Punto ya existe"));

        mockMvc.perform(post("/puntoventa/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Punto de venta con nombre: Existing Punto ya existe"));
    }

    @Test
    void addPuntoVenta_invalidInput_shouldReturn400() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setNombre(""); // Invalid

        when(puntoVentaService.savePuntoVenta(any(PuntoVenta.class)))
                .thenThrow(new com.oriana.challenge.exception.InvalidInputException("El nombre del punto de venta no puede estar vacío"));

        mockMvc.perform(post("/puntoventa/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El nombre del punto de venta no puede estar vacío"));
    }

    @Test
    void deletePuntoVenta_shouldReturnOk() throws Exception {
        doNothing().when(puntoVentaService).deletePuntoVentaById(1L);

        mockMvc.perform(delete("/puntoventa/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Punto de venta eliminado con id: 1"));
    }

/*    @Test
    void deletePuntoVenta_notFound_shouldReturn404() throws Exception {
        when(puntoVentaService.deletePuntoVentaById(1L))
                .thenThrow(new com.oriana.challenge.exception.ResourceNotFoundException("PuntoVenta con id: 1 no encontrado"));

        mockMvc.perform(delete("/puntoventa/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("PuntoVenta con id: 1 no encontrado"));
    }*/

    @Test
    void updatePuntoVenta_shouldReturnOk() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setId(1L);
        pv.setNombre("Updated Punto");

        doNothing().when(puntoVentaService).updatePuntoVentaById(pv);

        mockMvc.perform(put("/puntoventa/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isOk())
                .andExpect(content().string("Punto de venta actualizado con id: 1"));
    }

    @Test
    void updatePuntoVenta_idMismatch_shouldReturn400() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setId(2L); // Different ID
        pv.setNombre("Updated Punto");

        mockMvc.perform(put("/puntoventa/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID en path y body deben ser iguales"));
    }

/*    @Test
    void updatePuntoVenta_notFound_shouldReturn404() throws Exception {
        PuntoVenta pv = new PuntoVenta();
        pv.setId(1L);
        pv.setNombre("Updated Punto");

        when(puntoVentaService.updatePuntoVentaById(pv))
                .thenThrow(new com.oriana.challenge.exception.ResourceNotFoundException("PuntoVenta con id: 1 no encontrado"));

        mockMvc.perform(put("/puntoventa/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pv)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("PuntoVenta con id: 1 no encontrado"));
    }*/
}