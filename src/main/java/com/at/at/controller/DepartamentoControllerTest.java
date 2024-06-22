package com.at.at.controller;

import com.at.at.model.Departamento;
import com.at.at.repository.DepartamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartamentoController.class)
public class DepartamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartamentoRepository departamentoRepository;

    private ObjectMapper objectMapper;

    private Departamento departamento;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("Secretaria");
        departamento.setLocal("Bloco A");
    }

    @Test
    public void getAllDepartamentosTest() throws Exception {
        given(departamentoRepository.findAll()).willReturn(Arrays.asList(departamento));

        mockMvc.perform(get("/api/departamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(departamento.getNome()));
    }

    @Test
    public void createDepartamentoTest() throws Exception {
        given(departamentoRepository.save(any(Departamento.class))).willReturn(departamento);

        mockMvc.perform(post("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(departamento.getNome()));
    }

    @Test
    public void updateDepartamentoTest() throws Exception {
        given(departamentoRepository.findById(anyLong())).willReturn(Optional.of(departamento));
        given(departamentoRepository.save(any(Departamento.class))).willReturn(departamento);

        departamento.setNome("Secretaria Atualizada");

        mockMvc.perform(put("/api/departamentos/{id}", departamento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(departamento.getNome()));
    }

    @Test
    public void deleteDepartamentoTest() throws Exception {
        given(departamentoRepository.findById(anyLong())).willReturn(Optional.of(departamento));

        mockMvc.perform(delete("/api/departamentos/{id}", departamento.getId()))
                .andExpect(status().isOk());
    }
}