package com.at.at.controller;

import com.at.at.exception.ResourceNotFoundException;
import com.at.at.model.Funcionario;
import com.at.at.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/{id}")
    public Funcionario updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario not found for this id :: " + id));

        funcionario.setNome(funcionarioDetails.getNome());
        funcionario.setEndereco(funcionarioDetails.getEndereco());
        funcionario.setTelefone(funcionarioDetails.getTelefone());
        funcionario.setEmail(funcionarioDetails.getEmail());
        funcionario.setDataNascimento(funcionarioDetails.getDataNascimento());

        return funcionarioRepository.save(funcionario);
    }

    @DeleteMapping("/{id}")
    public void deleteFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario not found for this id :: " + id));
        funcionarioRepository.delete(funcionario);
    }
}