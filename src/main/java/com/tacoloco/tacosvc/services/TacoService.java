package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.exception.TacoNotFoundException;
import com.tacoloco.tacosvc.repositories.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TacoService implements ITacoService {

    @Autowired
    private TacoRepository tacoRepository;

    @Override
    public List<Taco> getAllTacos() {
        return tacoRepository.findAll();
    }

    @Override
    public Optional<Taco> getTacoById(Long id) {
        return tacoRepository.findById(id);
    }

    @Override
    public Taco createTaco(Taco taco) {
        return tacoRepository.save(taco);
    }

    @Override
    public Taco updateTaco(Long id, Taco taco) {
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        if(tacoOptional.isEmpty()) {
            throw new TacoNotFoundException("Taco with id " + id + " not found");
        }
        Taco existingTaco = tacoOptional.get();
        existingTaco.setName(taco.getName());
        existingTaco.setPrice(taco.getPrice());
        return tacoRepository.save(existingTaco);
    }
}
