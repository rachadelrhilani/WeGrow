package com.wegrow.service;

import com.wegrow.dto.EmployeDTO;
import com.wegrow.model.Employe;

import java.util.List;

public interface EmployeService {
    // ajouter un employe dans un departement
    EmployeDTO addEmployeToDepartement(Long departementId, Employe employe);

    // lister les employes d'un departement
    List<EmployeDTO> getEmployesByDepartement(Long departementId);
}