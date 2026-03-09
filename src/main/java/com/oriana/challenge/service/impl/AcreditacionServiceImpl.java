package com.oriana.challenge.service.impl;

import com.oriana.challenge.service.AcreditacionService;

import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.repository.AcreditacionRepository;
import com.oriana.challenge.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcreditacionServiceImpl implements AcreditacionService {

    @Autowired
    private AcreditacionRepository acreditacionRepository;

    public Acreditacion getAcreditacionById(Long id) {
        //validateId(id);
        return acreditacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acreditacion con id: " + id + " no encontrado"));
    }

    public List<Acreditacion> getAcreditacionesByPuntoVentaId(Long id) {
        return acreditacionRepository.findAllByPuntoVenta(id);
    }


    public Acreditacion createAcreditacion(Acreditacion acreditacion) {
        return acreditacionRepository.save(acreditacion);
    }
}