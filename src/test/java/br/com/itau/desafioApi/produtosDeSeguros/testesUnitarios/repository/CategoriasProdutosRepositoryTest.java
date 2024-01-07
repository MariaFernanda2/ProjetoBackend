package br.com.itau.desafioApi.produtosDeSeguros.testesUnitarios.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.CategoriasSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.enums.CategoriaProduto;
import br.com.itau.desafioApi.produtosDeSeguros.repository.CategoriasProdutosRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoriasProdutosRepositoryTest {
    @Autowired
    private CategoriasProdutosRepository categoriasRepository;

    @Test
    public void testFindByCategoria() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setCategoria("TesteNovaCategoriaProduto");
        categoriasRepository.save(categoria);

        Optional<CategoriasSeguros> result = categoriasRepository.findByCategoria("TesteNovaCategoriaProduto");

        assertTrue(result.isPresent());
        assertEquals("TesteNovaCategoriaProduto", result.get().getCategoria());
    }
}