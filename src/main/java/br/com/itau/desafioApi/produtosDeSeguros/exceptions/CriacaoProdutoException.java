package br.com.itau.desafioApi.produtosDeSeguros.exceptions;

import java.util.List;

public class CriacaoProdutoException extends RuntimeException {
    private final List<String> mensagensErro;

    public CriacaoProdutoException(List<String> mensagensErro) {
        super("Campos obrigatórios não foram preenchidos");
        this.mensagensErro = mensagensErro;
    }

    public List<String> getMensagensErro() {
        return mensagensErro;
    }
}