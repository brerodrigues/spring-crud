package com.brenno.springcrud.services;

import com.brenno.springcrud.exceptions.PessoaException;
import com.brenno.springcrud.models.Pessoa;
import com.brenno.springcrud.models.enums.Sexo;
import com.brenno.springcrud.repositories.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa criaPessoa() {
        return new Pessoa("Breno", Sexo.MASCULINO, "breno@silva.com",
                LocalDate.now().minusDays(1), "Pernambuco", "Brasil",
                "098062312");
    }

    @Test
    public void deveRetornarListaDePessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        assertEquals(pessoas, pessoaService.findAll());
    }

    @Test
    public void deveRetornarPessoaById() {
        Optional<Pessoa> pessoa = Optional.of(mock(Pessoa.class));
        when(pessoaRepository.findById(1)).thenReturn(pessoa);

        assertEquals(pessoa, pessoaService.findById(1));
    }

    @Test
    public void deveSalvarPessoa() throws PessoaException {
        Pessoa pessoa = criaPessoa();
        when(pessoaRepository.save(any())).thenReturn(pessoa);

        assertEquals(pessoa, pessoaService.save(pessoa));
    }

    @Test
    public void deveEstourarPessoaExceptionCasoDataNascimentoMaiorQueDataAtual() {
        Pessoa pessoa = criaPessoa();
        pessoa.setDataNascimento(LocalDate.now().plusDays(1));
        assertThrows(PessoaException.class, () -> pessoaService.save(pessoa));
    }

    @Test
    public void deveRetornarTrueAoDeletarPessoaById() {
        doNothing().when(pessoaRepository).deleteById(1);
        assertTrue(pessoaService.deleteById(1));
    }

    @Test
    public void deveRetornarFalseAoTentarDeletarPessoaByIdQueNaoExiste() {
        when(pessoaService.deleteById(any())).thenThrow(new EmptyResultDataAccessException("", 1));
        assertFalse(pessoaService.deleteById(1));
    }

    @Test
    public void deveFazerUpdateDePessoaById() {
        Pessoa newPessoa = new Pessoa("Bruna", Sexo.FEMININO, "bruna@silva.com",
                LocalDate.now().minusDays(2), "Pernambuca", "Brazil",
                "098062312");

        Optional<Pessoa> pessoa = Optional.of(criaPessoa());
        when(pessoaRepository.findById(1)).thenReturn(pessoa);
        when(pessoaRepository.save(pessoa.get())).thenReturn(newPessoa);

        Pessoa pessoaUpdated = pessoaService.updateById(1, newPessoa);
        assertEquals(newPessoa.getNome(), pessoaUpdated.getNome());
        assertEquals(newPessoa.getCpf(), pessoaUpdated.getCpf());
        assertEquals(newPessoa.getEmail(), pessoaUpdated.getEmail());
        assertEquals(newPessoa.getSexo(), pessoaUpdated.getSexo());
        assertEquals(newPessoa.getNaturalidade(), pessoaUpdated.getNaturalidade());
        assertEquals(newPessoa.getNacionalidade(), pessoaUpdated.getNacionalidade());
        assertEquals(newPessoa.getDataNascimento(), pessoaUpdated.getDataNascimento());
    }

}