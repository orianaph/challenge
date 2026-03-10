package com.oriana.challenge.service;

import com.oriana.challenge.dto.AcreditacionCreateRequest;
import com.oriana.challenge.entity.Acreditacion;

import java.util.List;

public interface AcreditacionService {

    Acreditacion getAcreditacionById(Long id);

    List<Acreditacion> getAcreditacionesByPuntoVentaId(Long id);

    Acreditacion createAcreditacion(AcreditacionCreateRequest request);
}
