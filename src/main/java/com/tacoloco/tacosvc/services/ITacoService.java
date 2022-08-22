package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.entities.Taco;

import java.util.List;
import java.util.Optional;

public interface ITacoService {

    List<Taco> getAllTacos();
    Optional<Taco> getTacoById(Long id);
    Taco createTaco(Taco taco);
    Taco updateTaco(Long id, Taco taco);

}
