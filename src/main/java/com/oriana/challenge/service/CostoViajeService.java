package com.oriana.challenge.service;

import com.oriana.challenge.dto.RutaMinimaResponse;
import com.oriana.challenge.entity.CostoViaje;

import java.util.List;

public interface CostoViajeService {

    List<CostoViaje> getAllCostoViaje();

    CostoViaje saveCostoViaje(CostoViaje costoViaje);

    CostoViaje createCostoViaje(CostoViaje costoViaje);

    /**
     * Create a new CostoViaje by supplying punto venta IDs and cost.
     */
    CostoViaje createCostoViaje(Long puntoOrigenId, Long puntoDestinoId, int costo);

    List<CostoViaje> getCostosPorPuntoVenta(Long id);

    void deleteCostoViaje(Long puntoA, Long puntoB);

    RutaMinimaResponse calcularRutaMinima(Long origenId, Long destinoId);
}




