package br.com.itau.desafioApi.produtosDeSeguros.domain.product;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name="produtos")
@Entity(name="produtos")
@Getter
@Setter
@EqualsAndHashCode(of= "id")

public class ProdutosSeguros {
        @Getter
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String categoria;
        private BigDecimal precoBase;
        private BigDecimal precoTarifado;

}

