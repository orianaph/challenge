package com.oriana.challenge.service;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;

import java.util.List;

public interface CostoViajeService {

    List<CostoViaje> getAllCostoViaje();

    CostoViaje saveCostoViaje(CostoViaje costoViaje);

    CostoViaje createCostoViaje(CostoViaje costoViaje);

    List<CostoViaje> getCostosPorPuntoVenta(Long id);

    void deleteCostoViaje(Long puntoA, Long puntoB);

    RutaMinimaResponse calcularRutaMinima(Long origenId, Long destinoId);
}




