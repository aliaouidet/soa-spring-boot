package com.soa.soaproject.controller;

import com.soa.soaproject.exception.ResourceNotFoundException;
import com.soa.soaproject.model.Scategorie;
import com.soa.soaproject.repository.ScategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/scategorie")
public class ScategorieController {

    @Autowired
    private ScategorieRepository scategorieRepository;

    @GetMapping
    public List<Scategorie> getAllScategories() {
        return scategorieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Scategorie> createScategorie(@RequestBody Scategorie scategorie) {
        // Your logic for creating the Scategorie
        Scategorie createdScategorie = scategorieRepository.save(scategorie);
        return new ResponseEntity<>(createdScategorie, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Scategorie> getScategorieById(@PathVariable long id) {
        Scategorie scategorie = scategorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scategorie not exist with id:" + id));
        return ResponseEntity.ok(scategorie);
    }

    @PutMapping("{id}")
    public ResponseEntity<Scategorie> updateScategorie(@PathVariable long id, @RequestBody Scategorie scategorieDetails) {
        Scategorie updateScategorie = scategorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scategorie not exist with id: " + id));

        updateScategorie.setNomscategorie(scategorieDetails.getNomscategorie());
        updateScategorie.setImagescategorie(scategorieDetails.getImagescategorie());

        scategorieRepository.save(updateScategorie);

        return ResponseEntity.ok(updateScategorie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteScategorie(@PathVariable long id) {

        Scategorie scategorie = scategorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scategorie not exist with id: " + id));

        scategorieRepository.delete(scategorie);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
