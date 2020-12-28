package com.brenno.springcrud.models;

import com.brenno.springcrud.models.enums.Sexo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Pessoa {

    @GeneratedValue
    @Id
    private int id;

    @Column(name = "NOME")
    @NotBlank(message = "Campo nome é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEXO")
    private Sexo sexo;

    @Column(name = "EMAIL")
    @Email(message = "Email inválido")
    private String email;

    @Column(name = "DT_NASCIMENTO")
    @NotNull(message = "Campo data nascimento é obrigatório")
    private LocalDate dataNascimento;

    @Column(name = "NATURALIDADE")
    private String naturalidade;

    @Column(name = "NACIONALIDADE")
    private String nacionalidade;

    @Column(name = "CPF")
    @NotBlank(message = "Campo cpf é obrigatório")
    private String cpf;

    public Pessoa() {

    }

    public Pessoa(String nome, Sexo sexo, String email, LocalDate dataNascimento, String naturalidade,
                  String nacionalidade, String cpf) {
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
