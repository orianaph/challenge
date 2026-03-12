package com.oriana.challenge.service.impl;

import com.oriana.challenge.service.AcreditacionService;

import com.oriana.challenge.dto.AcreditacionCreateRequest;
import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.AcreditacionRepository;
import com.oriana.challenge.repository.PuntoVentaRepository;
import com.oriana.challenge.exception.ResourceNotFoundException;
import com.oriana.challenge.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AcreditacionServiceImpl implements AcreditacionService {

    @Autowired
    private AcreditacionRepository acreditacionRepository;

    @Autowired
    private PuntoVentaRepository puntoVentaRepository;

    public Acreditacion getAcreditacionById(Long id) {
        validateId(id);
        return acreditacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acreditacion con id: " + id + " no encontrado"));
    }

    public List<Acreditacion> getAcreditacionesByPuntoVentaId(Long id) {
        validateId(id);
        return acreditacionRepository.findAllByPuntoVenta(id);
    }

    @Transactional
    public Acreditacion createAcreditacion(AcreditacionCreateRequest request) {
        if (request == null) {
            throw new InvalidInputException("La solicitud no puede ser nula");
        }

        PuntoVenta puntoVenta = puntoVentaRepository.findById(request.getPuntoVentaId())
                .orElseThrow(() -> new ResourceNotFoundException("PuntoVenta con id: " + request.getPuntoVentaId() + " no encontrado"));
        Acreditacion acreditacion = new Acreditacion();
        acreditacion.setPuntoVenta(puntoVenta);
        acreditacion.setImporte(request.getImporte());
        acreditacion.setFechaReception(new Date());
        return acreditacionRepository.save(acreditacion);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("ID debe ser válido y mayor a 0");
        }
    }
}