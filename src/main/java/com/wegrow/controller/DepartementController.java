package com.wegrow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegrow.dto.DepartementDTO;
import com.wegrow.exception.GlobalExceptionHandler;
import com.wegrow.model.Departement;
import com.wegrow.service.DepartementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.List;

// Gère : GET + POST /api/departements et DELETE /api/departements/{id}
public class DepartementController implements Controller {

    private final DepartementService service;
    private final ObjectMapper json;

    public DepartementController(DepartementService service, ObjectMapper json) {
        this.service = service;
        this.json = json;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setContentType("application/json;charset=UTF-8");

        String method = req.getMethod();
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        try {
            if ("/api/departements".equals(path) && "GET".equalsIgnoreCase(method)) {
                listAll(res);
            } else if ("/api/departements".equals(path) && "POST".equalsIgnoreCase(method)) {
                create(req, res);
            } else if ("DELETE".equalsIgnoreCase(method) && path.startsWith("/api/departements/")) {
                String idPart = path.substring("/api/departements/".length());
                try {
                    delete(Long.valueOf(idPart), res);
                } catch (NumberFormatException e) {
                    GlobalExceptionHandler.writeError(res, json, 404, "Introuvable : " + method + " " + path);
                }
            } else {
                GlobalExceptionHandler.writeError(res, json, 404, "Introuvable : " + method + " " + path);
            }
        } catch (Exception e) {
            GlobalExceptionHandler.handle(res, json, e);
        }
        return null;
    }

    // GET /api/departements
    private void listAll(HttpServletResponse res) throws Exception {
        List<DepartementDTO> list = service.getAllDepartements();
        res.setStatus(200);
        json.writeValue(res.getWriter(), list);
    }

    // POST /api/departements
    private void create(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Departement dep = json.readValue(req.getInputStream(), Departement.class);
        DepartementDTO created = service.createDepartement(dep);
        res.setStatus(201);
        json.writeValue(res.getWriter(), created);
    }

    // DELETE /api/departements/{id}
    private void delete(Long id, HttpServletResponse res) throws Exception {
        service.deleteDepartement(id);
        res.setStatus(204);
    }
}
