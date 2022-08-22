package com.tacoloco.tacosvc.repositories;

import com.tacoloco.tacosvc.entities.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
