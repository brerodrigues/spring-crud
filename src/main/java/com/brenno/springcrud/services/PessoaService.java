package com.brenno.springcrud.services;

import com.brenno.springcrud.exceptions.PessoaException;
import com.brenno.springcrud.models.Pessoa;
import com.brenno.springcrud.repositories.PessoaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(Integer id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa save(Pessoa pessoa) throws PessoaException {
        validate(pessoa);
        return pessoaRepository.save(pessoa);
    }

    public Boolean deleteById(Integer id) {
        try {
            pessoaRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    public Pessoa updateById(Integer id, Pessoa newPessoa) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(newPessoa.getNome());
                    pessoa.setCpf(newPessoa.getCpf());
                    pessoa.setDataNascimento(newPessoa.getDataNascimento());
                    pessoa.setEmail(newPessoa.getEmail());
                    pessoa.setNacionalidade(newPessoa.getNacionalidade());
                    pessoa.setNaturalidade(newPessoa.getNaturalidade());
                    pessoa.setSexo(newPessoa.getSexo());
                    Pessoa pessoaUpdated = pessoaRepository.save(pessoa);
                    return pessoaUpdated;
                }).orElse(null);
    }

    private void validate(Pessoa pessoa) throws PessoaException {
        List<String> errors = new ArrayList<>();

        if (isDataNascimentoMaiorQueDataAtual(pessoa.getDataNascimento())) {
            errors.add("Data de nascimento não pode ser maior que a data atual.");
        }

        if (isCpfValido(pessoa.getCpf())) {
            errors.add("CPF inválido.");
        }

        if (!errors.isEmpty()) {
            PessoaException pessoaException = new PessoaException(errors);
            throw pessoaException;
        }
    }

    private Boolean isDataNascimentoMaiorQueDataAtual(LocalDate dataNascimento) {
        if (dataNascimento.isAfter(LocalDate.now())) {
            return true;
        }

        return false;
    }

    private Boolean isCpfValido(String cpf) {
        // TODO validar CPF
        return true;
    }
}
