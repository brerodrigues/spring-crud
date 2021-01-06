package com.brenno.springcrud.exceptions;

import java.util.Date;
import java.util.List;

public class ApiError {
    private int statusCode;
    private Date timestamp;
    private List errors;

    public ApiError(int statusCode, Date timestamp, List errors) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List getErrors() {
        return errors;
    }
}
