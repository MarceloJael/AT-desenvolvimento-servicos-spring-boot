package com.at.at.controller;

import com.at.at.model.Funcionario;
import com.at.at.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    private ObjectMapper objectMapper;

    private Funcionario funcionario;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Maria da Silva");
        funcionario.setEndereco("Rua A, 123");
        funcionario.setTelefone("123456789");
        funcionario.setEmail("maria.silva@infnet.edu.br");
        funcionario.setDataNascimento(LocalDate.of(1980, 1, 15));
    }

    @Test
    public void getAllFuncionariosTest() throws Exception {
        given(funcionarioRepository.findAll()).willReturn(Arrays.asList(funcionario));

        mockMvc.perform(get("/api/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(funcionario.getNome()));
    }

    @Test
    public void createFuncionarioTest() throws Exception {
        given(funcionarioRepository.save(any(Funcionario.class))).willReturn(funcionario);

        mockMvc.perform(post("/api/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }

    @Test
    public void updateFuncionarioTest() throws Exception {
        given(funcionarioRepository.findById(anyLong())).willReturn(Optional.of(funcionario));
        given(funcionarioRepository.save(any(Funcionario.class))).willReturn(funcionario);

        funcionario.setNome("Maria da Silva Atualizada");

        mockMvc.perform(put("/api/funcionarios/{id}", funcionario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()));
    }

    @Test
    public void deleteFuncionarioTest() throws Exception {
        given(funcionarioRepository.findById(anyLong())).willReturn(Optional.of(funcionario));

        mockMvc.perform(delete("/api/funcionarios/{id}", funcionario.getId()))
                .andExpect(status().isOk());
    }
}