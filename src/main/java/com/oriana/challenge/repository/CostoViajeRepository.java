package com.oriana.challenge.repository;

import com.oriana.challenge.entity.CostoViaje;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostoViajeRepository extends JpaRepository<CostoViaje, Long> {

    @Query("""
        SELECT c FROM CostoViaje c
        WHERE c.puntoOrigen.id = :puntoId
           OR c.puntoDestino.id = :puntoId
    """)
    List<CostoViaje> findAllByPuntoVenta(@Param("puntoId") Long puntoId);


    @Modifying
    @Transactional
    @Query("""
    DELETE FROM CostoViaje c
    WHERE c.puntoOrigen.id = :origenId
      AND c.puntoDestino.id = :destinoId
""")
    int deleteByOrigenAndDestino(
            @Param("origenId") Long origenId,
            @Param("destinoId") Long destinoId
    );
}
