package br.com.itau.desafioApi.produtosDeSeguros.controllers;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.response.ApiResponse;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.service.ProdutoService;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.ProdutosSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.enums.CategoriaProduto;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.repository.ProdutosSegurosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.request.RequestCriacaoProduto;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.response.ResponseCriacaoProduto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProductsController {
    private final ProdutoService produtoService;

    @Autowired
    private ProdutosSegurosRepository repositoryProduto;

    public ProductsController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseCriacaoProduto>> CriacaoNovoProduto(@RequestBody @Valid RequestCriacaoProduto data) {

        // Validação dos campos obrigatórios
        produtoService.validarCamposObrigatorios(data);

        // Criação e persistência do produto
        ProdutosSeguros novoProduto = produtoService.criarNovoProduto(data);

        // Construção da resposta de sucesso
        Long novoProdutoId = novoProduto.getId();
        ResponseCriacaoProduto response = new ResponseCriacaoProduto(
                String.valueOf(novoProdutoId),
                novoProduto.getNome(),
                CategoriaProduto.valueOf(novoProduto.getCategoria()),
                novoProduto.getPrecoBase().floatValue(),
                novoProduto.getPrecoTarifado().floatValue()
        );

        return ResponseEntity.ok().build();
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
    public ResponseEntity AtualizarProduto(@RequestBody @Valid RequestCriacaoProduto data) {
        Optional<ProdutosSeguros> optionalProduct = repositoryProduto.findById(data.id());
        if (optionalProduct.isPresent()) {
            ProdutosSeguros produtos = optionalProduct.get();
            produtos.setNome(data.nome());
            produtos.setCategoria(data.categoria());
            produtos.setPrecoBase(data.preco_base());

            return ResponseEntity.ok(produtos);
        } else {
            throw new EntityNotFoundException();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletarProduto(@PathVariable Integer id) {
        repositoryProduto.deleteById(String.valueOf(id));
    return ResponseEntity.noContent().build();
    }
}

