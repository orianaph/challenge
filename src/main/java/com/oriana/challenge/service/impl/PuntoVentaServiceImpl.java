package com.oriana.challenge.service.impl;

import com.oriana.challenge.service.PuntoVentaService;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.PuntoVentaRepository;
import com.oriana.challenge.exception.ResourceNotFoundException;
import com.oriana.challenge.exception.ResourceAlreadyExistsException;
import com.oriana.challenge.exception.InvalidInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Service utiliza excepciones del tipo RuntimeException
@Service
public class PuntoVentaServiceImpl implements PuntoVentaService {

    @Autowired
    private PuntoVentaRepository puntoVentaRepository;

    public List<PuntoVenta> getListaPuntoVenta() {
        return puntoVentaRepository.findAll();
    }


    public PuntoVenta getPuntoVentaById(Long id) {
        validateId(id);
        return puntoVentaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PuntoVenta con id: " + id + " no encontrado"));
    }


    @Transactional
    public PuntoVenta savePuntoVenta(PuntoVenta puntoVenta) {
        validatePuntoVenta(puntoVenta);
        
        if (puntoVentaRepository.findByNombre(puntoVenta.getNombre()).isPresent()) {
            throw new ResourceAlreadyExistsException("Punto de venta con nombre: " + puntoVenta.getNombre() + " ya existe.");
        }
        return puntoVentaRepository.save(puntoVenta);
    }

    @Transactional
    public void deletePuntoVentaById(long id) {

        Optional<PuntoVenta> puntoVenta = puntoVentaRepository.findById(id);
        if (!puntoVenta.isPresent()) {
            throw new ResourceNotFoundException("PuntoVenta con id: " + id + " no encontrado");
        }
        puntoVentaRepository.deleteById(id);
    }

    @Transactional
    public void updatePuntoVentaById(PuntoVenta puntoVenta) {
        validatePuntoVenta(puntoVenta);
        validateId(puntoVenta.getId());
        
        Optional<PuntoVenta> optionalPuntoVenta = puntoVentaRepository.findById(puntoVenta.getId());
        if (!optionalPuntoVenta.isPresent()) {
            throw new ResourceNotFoundException("PuntoVenta con id: " + puntoVenta.getId() + " no encontrado");
        }
        
        // Verificar que el nuevo nombre no esté usado por otro punto de venta
        Optional<PuntoVenta> existingByNombre = puntoVentaRepository.findByNombre(puntoVenta.getNombre());
        if (existingByNombre.isPresent() && !Objects.equals(existingByNombre.get().getId(), puntoVenta.getId())) {
            throw new ResourceAlreadyExistsException("Punto de venta con nombre: " + puntoVenta.getNombre() + " ya existe.");
        }
        
        PuntoVenta find = optionalPuntoVenta.get();
        find.setNombre(puntoVenta.getNombre());
        puntoVentaRepository.save(find);
    }

    //  Métodos privados de validación
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("ID debe ser válido y mayor a 0");
        }
    }

    private void validateNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new InvalidInputException("El nombre del punto de venta no puede estar vacío");
        }
    }

    private void validatePuntoVenta(PuntoVenta puntoVenta) {
        if (puntoVenta == null) {
            throw new InvalidInputException("Punto de venta no puede ser nulo");
        }
        validateNombre(puntoVenta.getNombre());
    }
}