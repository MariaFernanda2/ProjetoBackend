package br.com.itau.desafioApi.produtosDeSeguros.exceptions;

public class CategoriasNotFoundException extends RuntimeException{
    public CategoriasNotFoundException() {super ("Categoria de produto não encontrada");}

    public CategoriasNotFoundException(String message) {super(message);}
}
