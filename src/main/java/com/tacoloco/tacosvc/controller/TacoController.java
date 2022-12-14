package com.tacoloco.tacosvc.controller;

import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.services.ITacoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tacos")
public class TacoController {

    @Autowired
    private ITacoService tacoService;

    /**
     * GET all menu items that are in the system
     *
     * @return List<Taco> menu items
     */
    @GetMapping
    public ResponseEntity<List<Taco>> getAllTacos() {
        List<Taco> tacos =  tacoService.getAllTacos();
        return new ResponseEntity<>(tacos, HttpStatus.OK);
    }

    /**
     * GET details of a single menu item
     *
     * @param tacoId Long Id of the menu item we want to retrieve
     * @return Taco
     */
    @GetMapping("/{tacoId}")
    public ResponseEntity<Taco> getTacoById(
            @PathVariable Long tacoId
    ) {
        Optional<Taco> optionalTaco = tacoService.getTacoById(tacoId);
        return optionalTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST or create a new menu item
     *
     * @param tacoDetails Taco
     * @return Taco
     */
    @PostMapping
    public ResponseEntity<Taco> createTaco(
            @Valid @RequestBody Taco tacoDetails) {
        Taco taco = tacoService.createTaco(tacoDetails);
        return new ResponseEntity<>(taco, HttpStatus.CREATED);
    }
}

