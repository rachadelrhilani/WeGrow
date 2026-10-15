package com.wegrow.service.Impl;

import com.wegrow.dto.EmployeDTO;
import com.wegrow.exception.ResourceAlreadyExistsException;
import com.wegrow.exception.ResourceNotFoundException;
import com.wegrow.model.Departement;
import com.wegrow.model.Employe;
import com.wegrow.repository.DepartementRepository;
import com.wegrow.repository.EmployeRepository;
import com.wegrow.service.EmployeService;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;
    private final DepartementRepository departementRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository, DepartementRepository departementRepository) {
        this.employeRepository = employeRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    public EmployeDTO addEmployeToDepartement(Long departementId, Employe employe) {
        Departement dep = departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + departementId));

        if (employeRepository.existsByMatricule(employe.getMatricule())) {
            throw new ResourceAlreadyExistsException("Un employé avec le matricule '" + employe.getMatricule() + "' existe déjà.");
        }

        employe.setDepartement(dep);
        Employe saved = employeRepository.save(employe);

        return new EmployeDTO(
                saved.getId(),
                saved.getMatricule(),
                saved.getNom(),
                saved.getPrenom(),
                saved.getPoste(),
                saved.getContrat()
        );
    }

    @Override
    public List<EmployeDTO> getEmployesByDepartement(Long departementId) {
        if (!departementRepository.existsById(departementId)) {
            throw new ResourceNotFoundException("Département introuvable avec l'ID : " + departementId);
        }

        return employeRepository.findByDepartementId(departementId).stream()
                .map(e -> new EmployeDTO(
                        e.getId(),
                        e.getMatricule(),
                        e.getNom(),
                        e.getPrenom(),
                        e.getPoste(),
                        e.getContrat()
                ))
                .collect(Collectors.toList());
    }
}