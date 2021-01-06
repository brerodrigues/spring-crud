package com.brenno.springcrud.resources;

import com.brenno.springcrud.models.Pessoa;
import com.brenno.springcrud.repositories.PessoaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api
@RestController
@RequestMapping(path="/pessoas")
public class PessoaResource {

    private PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @ApiOperation("Obtem todas as pessoas cadastradas.")
    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @ApiOperation("Busca e retorna pessoa por ID")
    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> getById(@PathVariable Integer id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa);
    }

    @ApiOperation("Cadastra pessoas, uma por vez.")
    @PostMapping
    public ResponseEntity<Pessoa> save(@Validated @RequestBody Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoa);
    }

    @ApiOperation("Remove pessoas por id")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> deleteById(@PathVariable Integer id) {
        try {
            pessoaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Atualiza pessoas por id")
    @PutMapping(value="/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Integer id, @Validated @RequestBody Pessoa newPessoa) {
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
                    return ResponseEntity.ok().body(pessoaUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
