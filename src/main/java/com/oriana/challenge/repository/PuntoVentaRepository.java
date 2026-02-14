package com.oriana.challenge.repository;

import com.oriana.challenge.entity.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Long> {
    Optional<PuntoVenta> findByNombre(String nombre);
}


