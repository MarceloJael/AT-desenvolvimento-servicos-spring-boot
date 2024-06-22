package com.at.at.controller;

import com.at.at.model.Funcionario;
import com.at.at.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FuncionarioControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Funcionario funcionario;

    @BeforeEach
    public void setUp() {
        funcionarioRepository.deleteAll();

        funcionario = new Funcionario();
        funcionario.setNome("Maria da Silva");
        funcionario.setEndereco("Rua A, 123");
        funcionario.setTelefone("123456789");
        funcionario.setEmail("maria.silva@infnet.edu.br");
        funcionario.setDataNascimento(LocalDate.of(1980, 1, 15));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void createFuncionarioTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(funcionario), headers);

        ResponseEntity<Funcionario> response = restTemplate.exchange("/api/funcionarios", HttpMethod.POST, request, Funcionario.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getNome()).isEqualTo(funcionario.getNome());
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getAllFuncionariosTest() {
        funcionarioRepository.save(funcionario);

        ResponseEntity<Funcionario[]> response = restTemplate.getForEntity("/api/funcionarios", Funcionario[].class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getNome()).isEqualTo(funcionario.getNome());
    }
}
