package com.oriana.challenge.service;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.exception.InvalidInputException;
import com.oriana.challenge.exception.ResourceAlreadyExistsException;
import com.oriana.challenge.exception.ResourceNotFoundException;
import com.oriana.challenge.repository.PuntoVentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import com.oriana.challenge.service.impl.PuntoVentaServiceImpl;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PuntoVentaServiceCreateTest {

    @Mock
    private PuntoVentaRepository repository;

    @InjectMocks
    private PuntoVentaServiceImpl service;

    @BeforeEach
    void setup() {
        // nada que configurar para este caso simple
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
        ResourceAlreadyExistsException ex = assertThrows(ResourceAlreadyExistsException.class, () -> service.savePuntoVenta(toSave));
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void getListaPuntoVenta_delegatesToRepository() {
        PuntoVenta pv = new PuntoVenta("Test");
        when(repository.findAll()).thenReturn(List.of(pv));

        List<PuntoVenta> result = service.getListaPuntoVenta();
        assertEquals(1, result.size());
        assertSame(pv, result.get(0));
    }

    @Test
    void getPuntoVentaById_success() {
        PuntoVenta pv = new PuntoVenta("Test");
        pv.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(pv));

        PuntoVenta result = service.getPuntoVentaById(1L);
        assertSame(pv, result);
    }

    @Test
    void getPuntoVentaById_notFound_throws() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getPuntoVentaById(1L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void getPuntoVentaById_invalidId_throws() {
        InvalidInputException ex = assertThrows(InvalidInputException.class,
                () -> service.getPuntoVentaById(null));
        assertTrue(ex.getMessage().contains("válido"));
    }

    @Test
    void deletePuntoVentaById_success() {
        PuntoVenta pv = new PuntoVenta("Test");
        pv.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(pv));

        assertDoesNotThrow(() -> service.deletePuntoVentaById(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void deletePuntoVentaById_notFound_throws() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.deletePuntoVentaById(1L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void updatePuntoVentaById_success() {
        PuntoVenta existing = new PuntoVenta("Old");
        existing.setId(1L);
        PuntoVenta updated = new PuntoVenta("New");
        updated.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByNombre("New")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> service.updatePuntoVentaById(updated));
        verify(repository).save(existing);
        assertEquals("New", existing.getNombre());
    }

    @Test
    void updatePuntoVentaById_notFound_throws() {
        PuntoVenta updated = new PuntoVenta("New");
        updated.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.updatePuntoVentaById(updated));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void updatePuntoVentaById_duplicateName_throws() {
        PuntoVenta existing = new PuntoVenta("Old");
        existing.setId(1L);
        PuntoVenta other = new PuntoVenta("New");
        other.setId(2L);
        PuntoVenta updated = new PuntoVenta("New");
        updated.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByNombre("New")).thenReturn(Optional.of(other));

        ResourceAlreadyExistsException ex = assertThrows(ResourceAlreadyExistsException.class,
                () -> service.updatePuntoVentaById(updated));
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void updatePuntoVentaById_invalidId_throws() {
        PuntoVenta updated = new PuntoVenta("New");
        updated.setId(null);

        InvalidInputException ex = assertThrows(InvalidInputException.class,
                () -> service.updatePuntoVentaById(updated));
        assertTrue(ex.getMessage().contains("válido"));
    }

    @Test
    void updatePuntoVentaById_nullEntity_throws() {
        InvalidInputException ex = assertThrows(InvalidInputException.class,
                () -> service.updatePuntoVentaById(null));
        assertTrue(ex.getMessage().contains("nulo"));
    }
}
