package com.wegrow.service.Impl;

import com.wegrow.dto.DepartementDTO;
import com.wegrow.exception.ResourceAlreadyExistsException;
import com.wegrow.exception.ResourceConflictException;
import com.wegrow.exception.ResourceNotFoundException;
import com.wegrow.model.Departement;
import com.wegrow.repository.DepartementRepository;
import com.wegrow.service.DepartementService;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;
    

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    @Transactional
    public DepartementDTO createDepartement(Departement departement) {
        if (departementRepository.existsByNom(departement.getNom())) {
            throw new ResourceAlreadyExistsException("Un département avec le nom '" + departement.getNom() + "' existe déjà.");
        }
        
        Departement saved = departementRepository.save(departement);
        return new DepartementDTO(saved.getId(), saved.getNom(), saved.getDescription(), 0);
    }

    @Override
    public List<DepartementDTO> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(d -> new DepartementDTO(
                        d.getId(),
                        d.getNom(),
                        d.getDescription(),
                        d.getEmployes() != null ? d.getEmployes().size() : 0
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDepartement(Long id) {
        Departement dep = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + id));

        if (dep.getEmployes() != null && !dep.getEmployes().isEmpty()) {
            throw new ResourceConflictException("Impossible de supprimer le département '" + dep.getNom() + "' car il contient des employés.");
        }

        departementRepository.delete(dep);
    }
}