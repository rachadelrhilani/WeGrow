package com.wegrow.service;

import com.wegrow.dto.DepartementDTO;
import com.wegrow.model.Departement;

import java.util.List;

public interface DepartementService {
    // creer un departement
    DepartementDTO createDepartement(Departement departement);

    // lister les departements avec leur nombre d'employes
    List<DepartementDTO> getAllDepartements();

    // supprimer un departement
    void deleteDepartement(Long id);
}