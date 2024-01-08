package br.com.itau.desafioApi.produtosDeSeguros.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.ProdutosSeguros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutosSegurosRepository extends JpaRepository<ProdutosSeguros,String> {
    List<ProdutosSeguros> findByCategoria(String categoria);
}
