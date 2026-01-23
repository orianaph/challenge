package com.oriana.challenge.repository;

import com.oriana.challenge.entity.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Long> {

}
