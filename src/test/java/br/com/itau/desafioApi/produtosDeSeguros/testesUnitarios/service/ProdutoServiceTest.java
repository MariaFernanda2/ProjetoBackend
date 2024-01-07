package br.com.itau.desafioApi.produtosDeSeguros.testesUnitarios.service;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.CategoriasSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.dto.request.RequestCriacaoProduto;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CategoriasNotFoundException;
import br.com.itau.desafioApi.produtosDeSeguros.repository.CategoriasProdutosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.repository.ProdutosSegurosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

@Mock
private ProdutosSegurosRepository produtosRepository;

@Mock
private CategoriasProdutosRepository categoriasRepository;

@InjectMocks
private ProdutoService produtoService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testCriarNovoProduto() {
    // Mockando o comportamento do repositório de categorias
    when(categoriasRepository.findByCategoria(anyString())).thenReturn(Optional.of(new CategoriasSeguros()));

    // Criando uma instância de RequestCriacaoProduto para o teste
    RequestCriacaoProduto request = new RequestCriacaoProduto("ID123", "Nome", "Categoria", BigDecimal.TEN);

    // Chamando o método a ser testado
    produtoService.criarNovoProduto(request);

    // Verificando se o método do repositório foi chamado
    verify(produtosRepository, times(1)).save(any());
}

@Test
void testCriarNovoProdutoCategoriaNaoEncontrada() {
    when(categoriasRepository.findByCategoria(anyString())).thenReturn(Optional.empty());
    RequestCriacaoProduto request = new RequestCriacaoProduto("ID123", "Nome", "Categoria", BigDecimal.TEN);
    assertThrows(CategoriasNotFoundException.class, () -> produtoService.criarNovoProduto(request));
}

@Test
void testValidarCamposObrigatorios() {
    RequestCriacaoProduto request = new RequestCriacaoProduto("ID123", "Nome", "Categoria", BigDecimal.TEN);
    assertDoesNotThrow(() -> produtoService.validarCamposObrigatorios(request));
}
    @Test
    void testCalcularPrecoTarifadoVida() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setIof_percentual(BigDecimal.valueOf(1.0));
        categoria.setPis_percentual(BigDecimal.valueOf(2.2));
        categoria.setCofins_percentual(BigDecimal.valueOf(0.0));

        RequestCriacaoProduto request = new RequestCriacaoProduto("1", "Nome", "VIDA", BigDecimal.valueOf(100));

        BigDecimal precoTarifado = produtoService.calcularPrecoTarifado(request.preco_base(), categoria);

        BigDecimal expectedPrecoTarifado = BigDecimal.valueOf(103.20);
        assertEquals(0, precoTarifado.compareTo(expectedPrecoTarifado));
    }
    @Test
    void testCalcularPrecoTarifadoViagem() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setIof_percentual(BigDecimal.valueOf(2.0));
        categoria.setPis_percentual(BigDecimal.valueOf(4.0));
        categoria.setCofins_percentual(BigDecimal.valueOf(1.0));

        RequestCriacaoProduto request = new RequestCriacaoProduto("1", "Nome", "VIAGEM", BigDecimal.valueOf(100));

        BigDecimal precoTarifado = produtoService.calcularPrecoTarifado(request.preco_base(), categoria);

        BigDecimal expectedPrecoTarifado = BigDecimal.valueOf(107.0);
        assertEquals(0, precoTarifado.compareTo(expectedPrecoTarifado));
    }

    @Test
    void testCalcularPrecoTarifadoAuto() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setIof_percentual(BigDecimal.valueOf(5.5));
        categoria.setPis_percentual(BigDecimal.valueOf(4.0));
        categoria.setCofins_percentual(BigDecimal.valueOf(1.0));

        RequestCriacaoProduto request = new RequestCriacaoProduto("1", "Nome", "AUTO", BigDecimal.valueOf(100));

        BigDecimal precoTarifado = produtoService.calcularPrecoTarifado(request.preco_base(), categoria);

        BigDecimal expectedPrecoTarifado = BigDecimal.valueOf(110.5);
        assertEquals(0, precoTarifado.compareTo(expectedPrecoTarifado));
    }
    @Test
    void testCalcularPrecoTarifadoResidencial() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setIof_percentual(BigDecimal.valueOf(4.0));
        categoria.setPis_percentual(BigDecimal.valueOf(0.0));
        categoria.setCofins_percentual(BigDecimal.valueOf(3.0));

        RequestCriacaoProduto request = new RequestCriacaoProduto("1", "Nome", "RESIDENCIAL", BigDecimal.valueOf(100));

        BigDecimal precoTarifado = produtoService.calcularPrecoTarifado(request.preco_base(), categoria);

        BigDecimal expectedPrecoTarifado = BigDecimal.valueOf(107.0);
        assertEquals(0, precoTarifado.compareTo(expectedPrecoTarifado));
    }
    @Test
    void testCalcularPrecoTarifadoPatrimonial() {
        CategoriasSeguros categoria = new CategoriasSeguros();
        categoria.setIof_percentual(BigDecimal.valueOf(5.0));
        categoria.setPis_percentual(BigDecimal.valueOf(3.0));
        categoria.setCofins_percentual(BigDecimal.valueOf(0.0));

        RequestCriacaoProduto request = new RequestCriacaoProduto("1", "Nome", "PATRIMONIAL", BigDecimal.valueOf(100));

        BigDecimal precoTarifado = produtoService.calcularPrecoTarifado(request.preco_base(), categoria);

        BigDecimal expectedPrecoTarifado = BigDecimal.valueOf(108.0);
        assertEquals(0, precoTarifado.compareTo(expectedPrecoTarifado));
    }
}
