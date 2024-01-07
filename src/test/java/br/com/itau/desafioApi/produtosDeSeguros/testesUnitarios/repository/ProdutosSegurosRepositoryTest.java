package br.com.itau.desafioApi.produtosDeSeguros.testesUnitarios.repository;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.ProdutosSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.repository.ProdutosSegurosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProdutosSegurosRepositoryTest {

    @Autowired
    private ProdutosSegurosRepository produtosRepository;

    @Test
    public void testFindByCategoria() {
        ProdutosSeguros produto1 = new ProdutosSeguros();
        produto1.setNome("Seguro Viagem Internacional");
        produto1.setCategoria("Categoria Viagem");

        ProdutosSeguros produto2 = new ProdutosSeguros();
        produto2.setNome("Seguro Viagem Nacional");
        produto2.setCategoria("Categoria Viagem");

        produtosRepository.save(produto1);
        produtosRepository.save(produto2);

        List<ProdutosSeguros> resultados = produtosRepository.findByCategoria("Categoria Viagem");

        assertEquals(2, resultados.size());
    }
}

