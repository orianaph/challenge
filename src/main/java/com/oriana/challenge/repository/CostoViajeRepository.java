package com.oriana.challenge.repository;

import com.oriana.challenge.entity.CostoViaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostoViajeRepository extends JpaRepository<CostoViaje, Long> {

}
