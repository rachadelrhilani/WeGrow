package com.wegrow.controller;

import com.wegrow.dto.DepartementDTO;
import com.wegrow.model.Departement;
import com.wegrow.service.DepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @PostMapping
    public ResponseEntity<DepartementDTO> createDepartement(@RequestBody Departement departement) {
        DepartementDTO created = departementService.createDepartement(departement);
        return new ResponseEntity<>(created, HttpStatus.CREATED); 
    }


    @GetMapping
    public ResponseEntity<List<DepartementDTO>> getAllDepartements() {
        List<DepartementDTO> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build(); 
    }
}