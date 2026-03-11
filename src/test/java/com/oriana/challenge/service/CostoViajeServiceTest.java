package com.oriana.challenge.service;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.exception.InvalidInputException;
import com.oriana.challenge.exception.ResourceNotFoundException;
import com.oriana.challenge.repository.CostoViajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import com.oriana.challenge.service.impl.CostoViajeServiceImpl;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostoViajeServiceTest {

    @Mock
    private CostoViajeRepository repository;

    @Mock
    private com.oriana.challenge.service.PuntoVentaService puntoVentaService;

    @InjectMocks
    private CostoViajeServiceImpl service;

    private PuntoVenta pv1;
    private PuntoVenta pv2;
    private PuntoVenta pv3;

    @BeforeEach
    void setup() {
        // inyectar servicio de punto en el impl (Mockito lo hará con @InjectMocks)
        pv1 = new PuntoVenta("A");
        pv1.setId(1L);
        pv2 = new PuntoVenta("B");
        pv2.setId(2L);
        pv3 = new PuntoVenta("C");
        pv3.setId(3L);
    }

    @Test
    void getAllCostoViaje_delegatesToRepository() {
        CostoViaje r = new CostoViaje(pv1, pv2, 5);
        when(repository.findAll()).thenReturn(List.of(r));

        List<CostoViaje> result = service.getAllCostoViaje();
        assertEquals(1, result.size());
        assertSame(r, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void saveCostoViaje_delegatesToRepository() {
        CostoViaje toSave = new CostoViaje(pv1, pv2, 7);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CostoViaje saved = service.saveCostoViaje(toSave);
        assertSame(toSave, saved);
    }

    @Test
    void createCostoViaje_normalizesAndSaves() throws Exception {
        CostoViaje permit = new CostoViaje(pv2, pv1, 10);
        // el ID de origen > ID de destino, la normalización debe invertirlos
        // el objeto permit no tiene costoId porque no es necesario para crear
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CostoViaje result = service.createCostoViaje(permit);
        assertEquals(1L, result.getPuntoOrigen().getId());
        assertEquals(2L, result.getPuntoDestino().getId());
        assertEquals(10, result.getCosto());
    }

    @Test
    void createCostoViaje_withIdsLoadsPoints() {
        when(puntoVentaService.getPuntoVentaById(1L)).thenReturn(pv1);
        when(puntoVentaService.getPuntoVentaById(2L)).thenReturn(pv2);
        when(repository.existsByOrigenAndDestino(1L, 2L)).thenReturn(false);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        CostoViaje result = service.createCostoViaje(1L, 2L, 20);
        assertEquals(1L, result.getPuntoOrigen().getId());
        assertEquals(2L, result.getPuntoDestino().getId());
        assertEquals(20, result.getCosto());
    }

    @Test
    void createCostoViaje_nullPointsThrows() {
        CostoViaje r = new CostoViaje();
        r.setPuntoOrigen(null);
        r.setPuntoDestino(null);
        InvalidInputException ex = assertThrows(InvalidInputException.class,
                () -> service.createCostoViaje(r));
        assertTrue(ex.getMessage().contains("nulos"));
    }

    @Test
    void createCostoViaje_samePointsThrows() {
        CostoViaje r = new CostoViaje(pv1, pv1, 1);
        InvalidInputException ex = assertThrows(InvalidInputException.class,
                () -> service.createCostoViaje(r));
        assertTrue(ex.getMessage().contains("identicos"));
    }

    @Test
    void createCostoViaje_duplicateThrows() {
        CostoViaje r = new CostoViaje(pv1, pv2, 10);
        // el repositorio debe consultarse con el orden normalizado
        when(repository.existsByOrigenAndDestino(1L, 2L)).thenReturn(true);

        com.oriana.challenge.exception.ResourceAlreadyExistsException ex =
                assertThrows(com.oriana.challenge.exception.ResourceAlreadyExistsException.class,
                        () -> service.createCostoViaje(r));
        assertTrue(ex.getMessage().contains("Ya existe"));
    }

    @Test
    void deleteCostoViaje_success() {
        when(repository.deleteByOrigenAndDestino(1L, 2L)).thenReturn(1);

        assertDoesNotThrow(() -> service.deleteCostoViaje(1L, 2L));
        verify(repository).deleteByOrigenAndDestino(1L, 2L);
    }

    @Test
    void deleteCostoViaje_nonexistentThrows() {
        when(repository.deleteByOrigenAndDestino(1L, 2L)).thenReturn(0);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.deleteCostoViaje(1L, 2L));
        assertTrue(ex.getMessage().contains("No existe"));
    }

    @Test
    void deleteCostoViaje_invalidArgsThrows() {
        assertThrows(InvalidInputException.class,
                () -> service.deleteCostoViaje(null, 2L));
        assertThrows(InvalidInputException.class,
                () -> service.deleteCostoViaje(2L, 2L));
    }

    @Test
    void calcularRutaMinima_simplePath() {
        // construir grafo: 1-2 costo2, 2-3 costo3, 1-3 costo10
        CostoViaje r1 = new CostoViaje(pv1, pv2, 2);
        CostoViaje r2 = new CostoViaje(pv2, pv3, 3);
        CostoViaje r3 = new CostoViaje(pv1, pv3, 10);
        when(repository.findAll()).thenReturn(List.of(r1, r2, r3));

        RutaMinimaResponse resp = service.calcularRutaMinima(1L, 3L);
        assertEquals(5, resp.getTotalCost());
        assertEquals(List.of(1L, 2L, 3L), resp.getPath());
    }

    @Test
    void calcularRutaMinima_noPathThrows() {
        // grafo donde sólo 1 y 2 están conectados, 3 está aislado
        CostoViaje r1 = new CostoViaje(pv1, pv2, 2);
        when(repository.findAll()).thenReturn(List.of(r1));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.calcularRutaMinima(1L, 3L));
        assertTrue(ex.getMessage().contains("ruta"));
    }

}
