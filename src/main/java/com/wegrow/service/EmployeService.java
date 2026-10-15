package com.wegrow.service;

import com.wegrow.dto.EmployeDTO;
import com.wegrow.model.Employe;

import java.util.List;

public interface EmployeService {
    EmployeDTO addEmployeToDepartement(Long departementId, Employe employe);

    List<EmployeDTO> getEmployesByDepartement(Long departementId);
}