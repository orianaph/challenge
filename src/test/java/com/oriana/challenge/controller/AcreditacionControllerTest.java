package com.oriana.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oriana.challenge.dto.AcreditacionCreateRequest;
import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.service.AcreditacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AcreditacionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private AcreditacionService acreditacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // @Test
    // void createAcreditacion_shouldReturnCreated() throws Exception {
    //     AcreditacionCreateRequest request = new AcreditacionCreateRequest();
    //     request.setPuntoVentaId(1L);
    //     request.setImporte(100.0);

    //     PuntoVenta puntoVenta = new PuntoVenta();
    //     puntoVenta.setId(1L);
    //     puntoVenta.setNombre("Test Punto");

    //     Acreditacion acreditacion = new Acreditacion();
    //     acreditacion.setId(1L);
    //     acreditacion.setImporte(100.0);
    //     acreditacion.setPuntoVenta(puntoVenta);

    //     when(acreditacionService.createAcreditacion(any(AcreditacionCreateRequest.class))).thenReturn(acreditacion);

    //     mockMvc.perform(post("/acreditaciones/create")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(request)))
    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.id").value(1L))
    //             .andExpect(jsonPath("$.importe").value(100.0));
    // }

    // @Test
    // void getAcreditacionById_shouldReturnAcreditacion() throws Exception {
    //     Acreditacion acreditacion = new Acreditacion();
    //     acreditacion.setId(1L);
    //     acreditacion.setImporte(200.0);

    //     when(acreditacionService.getAcreditacionById(1L)).thenReturn(acreditacion);

    //     mockMvc.perform(get("/acreditaciones/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(1L))
    //             .andExpect(jsonPath("$.importe").value(200.0));
    // }

    // @Test
    // void getAcreditacionById_notFound_shouldReturn404() throws Exception {
    //     when(acreditacionService.getAcreditacionById(1L))
    //             .thenThrow(new com.oriana.challenge.exception.ResourceNotFoundException("Acreditacion con id: 1 no encontrado"));

    //     mockMvc.perform(get("/acreditaciones/1"))
    //             .andExpect(status().isNotFound())
    //             .andExpect(content().string("Acreditacion con id: 1 no encontrado"));
    // }

    // @Test
    // void getAcreditacionesByPuntoVenta_shouldReturnList() throws Exception {
    //     Acreditacion acreditacion1 = new Acreditacion();
    //     acreditacion1.setId(1L);
    //     acreditacion1.setImporte(100.0);

    //     Acreditacion acreditacion2 = new Acreditacion();
    //     acreditacion2.setId(2L);
    //     acreditacion2.setImporte(200.0);

    //     List<Acreditacion> acreditaciones = Arrays.asList(acreditacion1, acreditacion2);

    //     when(acreditacionService.getAcreditacionesByPuntoVentaId(1L)).thenReturn(acreditaciones);

    //     mockMvc.perform(get("/acreditaciones/puntoVenta/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].id").value(1L))
    //             .andExpect(jsonPath("$[0].importe").value(100.0))
    //             .andExpect(jsonPath("$[1].id").value(2L))
    //             .andExpect(jsonPath("$[1].importe").value(200.0));
    // }
}