package com.at.at.repository;

import com.at.at.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void testCreateAndFindFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria da Silva");
        funcionario.setEndereco("Rua A, 123");
        funcionario.setTelefone("123456789");
        funcionario.setEmail("maria.silva@infnet.edu.br");
        funcionario.setDataNascimento(LocalDate.of(1980, 1, 15));

        funcionarioRepository.save(funcionario);

        List<Funcionario> foundFuncionarios = funcionarioRepository.findAll();
        assertThat(foundFuncionarios).hasSize(1);
        assertThat(foundFuncionarios.get(0).getNome()).isEqualTo("Maria da Silva");
    }
}
