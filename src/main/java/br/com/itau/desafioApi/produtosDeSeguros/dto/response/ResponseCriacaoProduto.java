package br.com.itau.desafioApi.produtosDeSeguros.dto.response;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.enums.CategoriaProduto;

public record ResponseCriacaoProduto(String id, String nome, CategoriaProduto categoria, Float preco_base, Float preco_tarifado) {
}