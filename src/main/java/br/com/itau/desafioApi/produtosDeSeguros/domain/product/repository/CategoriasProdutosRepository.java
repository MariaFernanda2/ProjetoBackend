package br.com.itau.desafioApi.produtosDeSeguros.domain.product.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.CategoriasSeguros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriasProdutosRepository extends JpaRepository<CategoriasSeguros,Integer> {
    Optional<CategoriasSeguros> findByCategoria(String categoria);
}
