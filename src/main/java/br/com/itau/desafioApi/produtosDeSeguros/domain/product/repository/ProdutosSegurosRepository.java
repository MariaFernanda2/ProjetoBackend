package br.com.itau.desafioApi.produtosDeSeguros.domain.product.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.ProdutosSeguros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutosSegurosRepository extends JpaRepository<ProdutosSeguros,String> {
    List<ProdutosSeguros> findByCategoria(String categoria);
}
