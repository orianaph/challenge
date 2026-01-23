package com.oriana.challenge.service;

import com.oriana.challenge.dto.CostoViajeDTO;
import com.oriana.challenge.entity.CostoViaje;
import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.CostoViajeRepository;
import com.oriana.challenge.repository.PuntoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostoViajeService {

    @Autowired
    private CostoViajeRepository costoViajeRepository;

    @Autowired
    private PuntoVentaRepository puntoVentaRepository;

    public List<CostoViaje> getAllCostoViaje() {
        return costoViajeRepository.findAll();
    }

    public void addCostoViaje(CostoViaje costoViaje) {
        costoViajeRepository.save(costoViaje);
    }

    public CostoViaje saveCostoViaje(CostoViaje costoViaje) {
        return costoViajeRepository.save(costoViaje);
    }

    public CostoViaje createCostoViaje(CostoViajeDTO dto) {
        PuntoVenta puntoA = puntoVentaRepository.findById(dto.getPuntoAId())
                .orElseThrow(() -> new IllegalArgumentException("PuntoVenta A no encontrado"));
        PuntoVenta puntoB = puntoVentaRepository.findById(dto.getPuntoBId())
                .orElseThrow(() -> new IllegalArgumentException("PuntoVenta B no encontrado"));

        CostoViaje costoViaje = new CostoViaje(puntoA, puntoB, dto.getCosto());
        return costoViajeRepository.save(costoViaje);
    }
}
