package br.com.itau.desafioApi.produtosDeSeguros.domain.product;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name="categorias")
@Entity(name="categorias")
@Getter
@Setter
@EqualsAndHashCode(of= "id")

public class CategoriasSeguros {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoria;
    private BigDecimal iof_percentual;
    private BigDecimal pis_percentual;
    private BigDecimal cofins_percentual;

}

