package br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestCriacaoProduto(String id, @NotBlank String nome, @NotBlank String categoria, @NotNull BigDecimal preco_base) {

}
