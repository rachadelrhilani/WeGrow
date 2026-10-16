package com.wegrow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wegrow.dto.EmployeDTO;
import com.wegrow.exception.GlobalExceptionHandler;
import com.wegrow.model.Employe;
import com.wegrow.service.EmployeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.List;

public class EmployeController implements Controller {

    private final EmployeService service;
    private final ObjectMapper json;

    public EmployeController(EmployeService service, ObjectMapper json) {
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
            String[] p = path.split("/");
            Long depId = null;
            if (p.length == 5 && "api".equals(p[1]) && "departements".equals(p[2]) && "employes".equals(p[4])) {
                try {
                    depId = Long.valueOf(p[3]);
                } catch (NumberFormatException e) {
                    depId = null;
                }
            }

            if (depId == null) {
                GlobalExceptionHandler.writeError(res, json, 404, "Introuvable : " + method + " " + path);
            } else if ("GET".equalsIgnoreCase(method)) {
                listByDep(depId, res);
            } else if ("POST".equalsIgnoreCase(method)) {
                create(req, res, depId);
            } else {
                GlobalExceptionHandler.writeError(res, json, 404, "Introuvable : " + method + " " + path);
            }
        } catch (Exception e) {
            GlobalExceptionHandler.handle(res, json, e);
        }
        return null;
    }

    private void listByDep(Long depId, HttpServletResponse res) throws Exception {
        List<EmployeDTO> list = service.getEmployesByDepartement(depId);
        res.setStatus(200);
        json.writeValue(res.getWriter(), list);
    }

    private void create(HttpServletRequest req, HttpServletResponse res, Long depId) throws Exception {
        Employe employe = json.readValue(req.getInputStream(), Employe.class);
        EmployeDTO created = service.addEmployeToDepartement(depId, employe);
        res.setStatus(201);
        json.writeValue(res.getWriter(), created);
    }
}
