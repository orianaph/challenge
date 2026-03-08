package com.oriana.challenge.service.impl;

import com.oriana.challenge.service.AcreditacionService;

import com.oriana.challenge.entity.Acreditacion;
import com.oriana.challenge.repository.AcreditacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcreditacionServiceImpl implements AcreditacionService {

    @Autowired
    private AcreditacionRepository acreditacionRepository;

    public Acreditacion getAcreditacionById(Long id) {
        //validateId(id);
        try {
            return acreditacionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Acreditacion con id: " + id + " no encontrado"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener acreditacion", e);
        }
    }

    public List<Acreditacion> getAcreditacionesByPuntoVentaId(Long id) {
        try {
            return acreditacionRepository.findAllByPuntoVenta(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener acreditaciones por punto de venta", e);
        }
    }


    public Acreditacion createAcreditacion(Acreditacion acreditacion) {
        return acreditacionRepository.save(acreditacion);
    }
}