package com.oriana.challenge.service;

import com.oriana.challenge.dto.AcreditacionCreateRequest;
import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.exception.ResourceNotFoundException;
import com.oriana.challenge.repository.AcreditacionRepository;
import com.oriana.challenge.repository.PuntoVentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.oriana.challenge.service.impl.AcreditacionServiceImpl;

@ExtendWith(MockitoExtension.class)
class AcreditacionServiceTest {

    @Mock
    private AcreditacionRepository repository;

    @Mock
    private PuntoVentaRepository puntoVentaRepository;

    @InjectMocks
    private AcreditacionServiceImpl service;

    @Test
    void getAcreditacionById_success() {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setId(1L);
        acreditacion.setImporte(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(acreditacion));

        Acreditacion result = service.getAcreditacionById(1L);
        assertSame(acreditacion, result);
    }

    @Test
    void getAcreditacionById_notFound_throws() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getAcreditacionById(1L));
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void getAcreditacionesByPuntoVentaId_delegatesToRepository() {
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setId(1L);

        when(repository.findAllByPuntoVenta(1L)).thenReturn(List.of(acreditacion));

        List<Acreditacion> result = service.getAcreditacionesByPuntoVentaId(1L);
        assertEquals(1, result.size());
        assertSame(acreditacion, result.get(0));
        verify(repository).findAllByPuntoVenta(1L);
    }

    @Test
    void createAcreditacion_delegatesToRepository() {
        AcreditacionCreateRequest request = new AcreditacionCreateRequest();
        request.setPuntoVentaId(1L);
        request.setImporte(200.0);

        PuntoVenta puntoVenta = new PuntoVenta();
        puntoVenta.setId(1L);
        puntoVenta.setNombre("Test");

        when(puntoVentaRepository.findById(1L)).thenReturn(Optional.of(puntoVenta));
        when(repository.save(any(Acreditacion.class))).thenAnswer(i -> {
            Acreditacion a = i.getArgument(0);
            a.setId(1L);
            return a;
        });

        Acreditacion result = service.createAcreditacion(request);
        assertNotNull(result.getId());
        assertEquals(200.0, result.getImporte());
        assertEquals(puntoVenta, result.getPuntoVenta());
        verify(repository).save(any(Acreditacion.class));
    }
}