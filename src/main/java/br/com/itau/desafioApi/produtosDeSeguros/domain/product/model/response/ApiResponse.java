package br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.response;

import java.util.List;

public class ApiResponse<T> {
    private int statusCode;
    private List<ErrorMessage> errors;

    public ApiResponse(int statusCode, List<ErrorMessage> errors) {
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

}
