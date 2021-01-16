package com.brenno.springcrud.exceptions;

import java.util.List;

public class PessoaException extends Exception {
    private List errors;

    public PessoaException(List errors) {
        this.errors = errors;
    }

    public List getErrors() {
        return errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }
}
