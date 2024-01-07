package br.com.itau.desafioApi.produtosDeSeguros.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductsResponseError {
    private HttpStatus status;
    private List<ErrorMessage> errors;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class ErrorMessage {
        private String message;
    }
}

