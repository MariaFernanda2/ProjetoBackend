package br.com.itau.desafioApi.produtosDeSeguros.exceptions;

public class EdicaoProdutoException extends RuntimeException{
    public EdicaoProdutoException() {super ("O id do produto deve ser informado");}

    public EdicaoProdutoException(String message) {super(message);}
}

