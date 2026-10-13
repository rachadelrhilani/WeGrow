package com.wegrow.dto;

public class DepartementDTO {

    private Long id;
    private String nom;
    private String description;
    private int nombreEmployes;

    public DepartementDTO() {
    }

    public DepartementDTO(Long id, String nom, String description, int nombreEmployes) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.nombreEmployes = nombreEmployes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombreEmployes() {
        return nombreEmployes;
    }

    public void setNombreEmployes(int nombreEmployes) {
        this.nombreEmployes = nombreEmployes;
    }
}