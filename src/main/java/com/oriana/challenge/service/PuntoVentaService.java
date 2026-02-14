package com.oriana.challenge.service;

import com.oriana.challenge.entity.PuntoVenta;
import com.oriana.challenge.repository.PuntoVentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Service utiliza excepciones del tipo RuntimeException
@Service
public class PuntoVentaService {

    @Autowired
    PuntoVentaRepository puntoVentaRepository;

    public List<PuntoVenta> getListaPuntoVenta() {
        try {
            return puntoVentaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener lista de puntos de venta", e);
        }
    }

    public PuntoVenta getPuntoVentaById(Long id) {
        validateId(id);
        try {
            return puntoVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PuntoVenta con id: " + id + " no encontrado"));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener punto de venta", e);
        }
    }

    public PuntoVenta savePuntoVenta(PuntoVenta puntoVenta) {
        validatePuntoVenta(puntoVenta);
        
        try {
            if (puntoVentaRepository.findByNombre(puntoVenta.getNombre()).isPresent()) {
                throw new RuntimeException("Punto de venta con nombre: " + puntoVenta.getNombre() + " ya existe.");
            }
            return puntoVentaRepository.save(puntoVenta);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar punto de venta", e);
        }
    }

    public void deletePuntoVentaById(long id) {

        try {
            if (!puntoVentaRepository.existsById(id)) {
                throw new RuntimeException("PuntoVenta con id: " + id + " no encontrado");
            }
            puntoVentaRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar punto de venta", e);
        }
    }


    public void updatePuntoVentaById(PuntoVenta puntoVenta) {
        validatePuntoVenta(puntoVenta);
        validateId(puntoVenta.getId());
        
        try {
            Optional<PuntoVenta> optionalPuntoVenta = puntoVentaRepository.findById(puntoVenta.getId());
            if (!optionalPuntoVenta.isPresent()) {
                throw new RuntimeException("PuntoVenta con id: " + puntoVenta.getId() + " no encontrado");
            }
            
            // Verificar que el nuevo nombre no esté usado por otro punto de venta
            Optional<PuntoVenta> existingByNombre = puntoVentaRepository.findByNombre(puntoVenta.getNombre());
            if (existingByNombre.isPresent() && !Objects.equals(existingByNombre.get().getId(), puntoVenta.getId())) {
                throw new RuntimeException("Punto de venta con nombre: " + puntoVenta.getNombre() + " ya existe.");
            }
            
            PuntoVenta find = optionalPuntoVenta.get();
            find.setNombre(puntoVenta.getNombre());
            puntoVentaRepository.save(find);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar punto de venta", e);
        }
    }

    // === Métodos privados de validación ===
    
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID debe ser válido y mayor a 0");
        }
    }

    private void validateNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException("El nombre del punto de venta no puede estar vacío");
        }
    }

    private void validatePuntoVenta(PuntoVenta puntoVenta) {
        if (puntoVenta == null) {
            throw new RuntimeException("Punto de venta no puede ser nulo");
        }
        validateNombre(puntoVenta.getNombre());
    }
}
