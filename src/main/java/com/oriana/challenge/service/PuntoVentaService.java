package com.oriana.challenge.service;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.PuntoVentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PuntoVentaService {

    @Autowired
    PuntoVentaRepository puntoVentaRepository;

    public List<PuntoVenta> getListaPuntoVenta() {
        return puntoVentaRepository.findAll();
    }

    public void addPuntoVenta(PuntoVenta puntoVenta) {
        puntoVentaRepository.save(puntoVenta);
    }

    public PuntoVenta getPuntoVentaById(Long id) {

        Optional<PuntoVenta> optionalPuntoVenta = puntoVentaRepository.findById(id);
        if (optionalPuntoVenta.isPresent()) {
            return optionalPuntoVenta.get();
        } else {
            System.out.println("Id no encontrado");
            return null;
        }
    }
    //Agregar validaciones  (por ej nombre no existe en base de datos  con getbynombre)
    public PuntoVenta savePuntoVenta(PuntoVenta puntoVenta) {
        return puntoVentaRepository.save(puntoVenta);
    }

    public void deletePuntoVentaById(long id) {
        puntoVentaRepository.deleteById(id);
    }

    public PuntoVenta updatePuntoVentaById(PuntoVenta puntoVenta) {
        Optional<PuntoVenta> optionalPuntoVenta = puntoVentaRepository.findById(puntoVenta.getId());

        if (optionalPuntoVenta.isPresent()) {
            PuntoVenta find = optionalPuntoVenta.get();
            find.setNombre(puntoVenta.getNombre());
            return puntoVentaRepository.save(find);
        } else {
            throw new IllegalArgumentException("PuntoVenta con id: " + puntoVenta.getId() + " no encontrado");
        }
    }
}
