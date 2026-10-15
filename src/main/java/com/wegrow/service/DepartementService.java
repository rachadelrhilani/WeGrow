package com.wegrow.service;

import com.wegrow.dto.DepartementDTO;
import com.wegrow.model.Departement;

import java.util.List;

public interface DepartementService {
    DepartementDTO createDepartement(Departement departement);

    List<DepartementDTO> getAllDepartements();

    void deleteDepartement(Long id);
}