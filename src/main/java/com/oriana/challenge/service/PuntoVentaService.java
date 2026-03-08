package com.oriana.challenge.service;

import com.oriana.challenge.entity.PuntoVenta;

import java.util.List;

public interface PuntoVentaService {

    List<PuntoVenta> getListaPuntoVenta();

    PuntoVenta getPuntoVentaById(Long id);

    PuntoVenta savePuntoVenta(PuntoVenta puntoVenta);

    void deletePuntoVentaById(long id);

    void updatePuntoVentaById(PuntoVenta puntoVenta);
}
