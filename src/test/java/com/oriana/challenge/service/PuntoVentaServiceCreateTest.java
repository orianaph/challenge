package com.oriana.challenge.service;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.PuntoVentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import com.oriana.challenge.service.impl.PuntoVentaServiceImpl;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PuntoVentaServiceCreateTest {

    @Mock
    private PuntoVentaRepository repository;

    @InjectMocks
    private PuntoVentaServiceImpl service;

    @BeforeEach
    void setup() {
        // nothing to set up for this simple case
    }

    @Test
    void savePuntoVenta_success() {
        PuntoVenta toSave = new PuntoVenta("Nuevo");
        when(repository.findByNombre("Nuevo")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(i -> {
            PuntoVenta pv = i.getArgument(0);
            pv.setId(1L);
            return pv;
        });

        PuntoVenta saved = service.savePuntoVenta(toSave);
        assertNotNull(saved.getId());
        assertEquals("Nuevo", saved.getNombre());
    }

    @Test
    void savePuntoVenta_duplicateName_throws() {
        PuntoVenta existing = new PuntoVenta("Existente");
        existing.setId(2L);
        when(repository.findByNombre("Existente")).thenReturn(Optional.of(existing));

        PuntoVenta toSave = new PuntoVenta("Existente");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.savePuntoVenta(toSave));
        assertTrue(ex.getMessage().contains("ya existe"));
    }
}
