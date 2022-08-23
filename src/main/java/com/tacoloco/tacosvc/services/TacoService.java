package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.exception.NotValidInputException;
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

    /**
     * Method to persist Taco details in the database.
     * The assumption is that the manager will time to time add new menu items that
     * customers can order
     *
     * @param taco Taco details
     * @return Taco saved in the database
     */
    @Override
    public Taco createTaco(Taco taco) {
        if(taco.getPrice() < 0.0) {
            throw new NotValidInputException("Price for Taco is less than 0");
        }
        return tacoRepository.save(taco);
    }

    /**
     * Method to update details of a Taco ie menu item
     *
     * @param id Long Id of the Taco to update
     * @param taco Taco object with new details
     * @return Taco updated
     */
    @Override
    public Taco updateTaco(Long id, Taco taco) {
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        if(tacoOptional.isEmpty()) {
            throw new TacoNotFoundException("Taco with id " + id + " not found");
        }
        if(taco.getPrice() < 0.0) {
            throw new NotValidInputException("Price for Taco is less than 0");
        }
        Taco existingTaco = tacoOptional.get();
        existingTaco.setName(taco.getName());
        existingTaco.setPrice(taco.getPrice());
        return tacoRepository.save(existingTaco);
    }
}
