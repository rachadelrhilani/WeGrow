package com.wegrow.repository;

import com.wegrow.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    boolean existsByMatricule(String matricule);
    List<Employe> findByDepartementId(Long departementId);
}