package com.at.at.controller;

import com.at.at.exception.ResourceNotFoundException;
import com.at.at.model.Departamento;
import com.at.at.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoRepository departamentoRepository;

    @Autowired
    public DepartamentoController(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    @PostMapping
    public Departamento createDepartamento(@RequestBody Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @PutMapping("/{id}")
    public Departamento updateDepartamento(@PathVariable Long id, @RequestBody Departamento departamentoDetails) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento not found for this id :: " + id));

        departamento.setNome(departamentoDetails.getNome());
        departamento.setLocal(departamentoDetails.getLocal());

        return departamentoRepository.save(departamento);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartamento(@PathVariable Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento not found for this id :: " + id));
        departamentoRepository.delete(departamento);
    }
}