package br.com.itau.desafioApi.produtosDeSeguros.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.CategoriasSeguros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriasProdutosRepository extends JpaRepository<CategoriasSeguros,Integer> {
    Optional<CategoriasSeguros> findByCategoria(String categoria);
}
