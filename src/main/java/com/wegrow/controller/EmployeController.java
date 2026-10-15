package com.wegrow.controller;

import com.wegrow.dto.EmployeDTO;
import com.wegrow.model.Employe;
import com.wegrow.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @PostMapping("/api/departements/{id}/employes")
    public ResponseEntity<EmployeDTO> addEmployeToDepartement(@PathVariable("id") Long departementId,
            @RequestBody Employe employe) {
        EmployeDTO created = employeService.addEmployeToDepartement(departementId, employe);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/api/departements/{id}/employes")
    public ResponseEntity<List<EmployeDTO>> getEmployesByDepartement(@PathVariable("id") Long departementId) {
        List<EmployeDTO> employes = employeService.getEmployesByDepartement(departementId);
        return ResponseEntity.ok(employes); // Code HTTP 200
    }
}