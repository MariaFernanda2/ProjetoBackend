package br.com.itau.desafioApi.produtosDeSeguros.infra;

import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CriacaoProdutoException;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CategoriasNotFoundException;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.EdicaoProdutoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.BindException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoriasNotFoundException.class)
    private ResponseEntity<RestErrorMessage> categoriasNotFoundHandler(CategoriasNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler({CriacaoProdutoException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProductsResponseError> criacaoProdutoHandler(CriacaoProdutoException exception) {
        List<String> mensagensErro = exception.getMensagensErro();

        List<ProductsResponseError.ErrorMessage> errorMessages = mensagensErro.stream()
                .map(ProductsResponseError.ErrorMessage::new)
                .collect(Collectors.toList());

        ProductsResponseError errorResponse = new ProductsResponseError(HttpStatus.BAD_REQUEST, errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(EdicaoProdutoException.class)
    private ResponseEntity<RestErrorMessage> EdicaoProdutoHandler(EdicaoProdutoException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
