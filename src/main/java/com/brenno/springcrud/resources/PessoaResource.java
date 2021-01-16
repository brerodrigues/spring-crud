package com.brenno.springcrud.resources;

import com.brenno.springcrud.exceptions.PessoaException;
import com.brenno.springcrud.models.Pessoa;
import com.brenno.springcrud.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api
@RestController
@RequestMapping(path="/pessoas")
public class PessoaResource {

    private PessoaService pessoaService;

    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @ApiOperation("Obtem todas as pessoas cadastradas.")
    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        List<Pessoa> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @ApiOperation("Busca e retorna pessoa por ID")
    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> getById(@PathVariable Integer id) {
        Optional<Pessoa> pessoa = pessoaService.findById(id);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa);
    }

    @ApiOperation("Cadastra pessoas, uma por vez.")
    @PostMapping
    public ResponseEntity<Pessoa> save(@Validated @RequestBody Pessoa pessoa) throws PessoaException {
        pessoaService.save(pessoa);
        return ResponseEntity.ok(pessoa);
    }

    @ApiOperation("Remove pessoas por id")
    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Pessoa>> deleteById(@PathVariable Integer id) {
        if (pessoaService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Atualiza pessoas por id")
    @PutMapping(value="/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Integer id, @Validated @RequestBody Pessoa newPessoa) {
        Pessoa pessoaUpdated = pessoaService.updateById(id, newPessoa);

        if (pessoaUpdated != null) {
            return ResponseEntity.ok().body(pessoaUpdated);
        }

        return ResponseEntity.notFound().build();
    }
}
