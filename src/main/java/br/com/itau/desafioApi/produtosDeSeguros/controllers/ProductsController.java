package br.com.itau.desafioApi.produtosDeSeguros.controllers;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.CategoriasSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CategoriasNotFoundException;
import br.com.itau.desafioApi.produtosDeSeguros.repository.CategoriasProdutosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.service.ProdutoService;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.ProdutosSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.enums.CategoriaProduto;
import br.com.itau.desafioApi.produtosDeSeguros.repository.ProdutosSegurosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.dto.request.RequestCriacaoProduto;
import br.com.itau.desafioApi.produtosDeSeguros.dto.response.ResponseCriacaoProduto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProductsController {
    private final ProdutoService produtoService;

    @Autowired
    private ProdutosSegurosRepository repositoryProduto;
    @Autowired
    private CategoriasProdutosRepository repositoryCategoriasProdutos;

    public ProductsController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ResponseCriacaoProduto> CriacaoNovoProduto(@RequestBody RequestCriacaoProduto data) {
        produtoService.validarCamposObrigatorios(data);
        ProdutosSeguros novoProduto = produtoService.criarNovoProduto(data);
        Long novoProdutoId = novoProduto.getId();
        ResponseCriacaoProduto response = new ResponseCriacaoProduto(
                String.valueOf(novoProdutoId),
                novoProduto.getNome(),
                CategoriaProduto.valueOf(novoProduto.getCategoria()),
                novoProduto.getPrecoBase().floatValue(),
                novoProduto.getPrecoTarifado().floatValue()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutosSeguros>> getAllProducts(@RequestParam(name = "categoria", required = false) String categoria) {
        List<ProdutosSeguros> todosProdutos;

        if (categoria != null && !categoria.isEmpty()) {
            todosProdutos = repositoryProduto.findByCategoria(categoria);
        } else {
            todosProdutos = repositoryProduto.findAll();
        }

        return ResponseEntity.ok(todosProdutos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutosSeguros> getProductById(@PathVariable Long id) {
        Optional<ProdutosSeguros> produtoOptional = repositoryProduto.findById(String.valueOf(id));

        if (produtoOptional.isPresent()) {
            ProdutosSeguros produto = produtoOptional.get();
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity AtualizarProduto(@RequestBody RequestCriacaoProduto data) {
        produtoService.validarCamposObrigatoriosEdicao(data);
        produtoService.validarCategoriaInformada(data);

        Optional<ProdutosSeguros> optionalProduct = repositoryProduto.findById(data.id());

        if (optionalProduct.isPresent()) {
            ProdutosSeguros produtos = optionalProduct.get();
            produtos.setNome(data.nome());
            produtos.setCategoria(data.categoria());
            produtos.setPrecoBase(data.preco_base());

            // Buscar a categoria correspondente ao nome da categoria no RequestCriacaoProduto
            CategoriasSeguros categoria = repositoryCategoriasProdutos.findByCategoria(data.categoria())
                    .orElseThrow(() -> new CategoriasNotFoundException("Categoria não encontrada: " + data.categoria()));

            // Calcular o novo preço tarifado
            BigDecimal novoPrecoTarifado = produtoService.calcularPrecoTarifado(data.preco_base(), categoria);
            produtos.setPrecoTarifado(novoPrecoTarifado);

            // Salvar as alterações
            repositoryProduto.save(produtos);

            return ResponseEntity.ok(produtos);
        }

        // Se o produto não for encontrado, você pode retornar um ResponseEntity.notFound()
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletarProduto(@PathVariable Integer id) {
        repositoryProduto.deleteById(String.valueOf(id));
    return ResponseEntity.noContent().build();
    }
}

