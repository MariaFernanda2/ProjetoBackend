package br.com.itau.desafioApi.produtosDeSeguros.domain.product.service;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.CategoriasSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.ProdutosSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.repository.CategoriasProdutosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.repository.ProdutosSegurosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.model.request.RequestCriacaoProduto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProdutoService {

    private final ProdutosSegurosRepository produtosRepository;
    private final CategoriasProdutosRepository categoriasRepository;

    public ProdutoService(ProdutosSegurosRepository produtosRepository, CategoriasProdutosRepository categoriasRepository) {
        this.produtosRepository = produtosRepository;
        this.categoriasRepository = categoriasRepository;
    }

    public ProdutosSeguros criarNovoProduto(RequestCriacaoProduto request) {
        BigDecimal precoBase = request.preco_base();
        CategoriasSeguros categoria = categoriasRepository.findByCategoria(request.categoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + request.categoria()));

        ProdutosSeguros novoProduto = new ProdutosSeguros();
        novoProduto.setNome(request.nome());
        novoProduto.setCategoria(categoria.getCategoria());
        novoProduto.setPrecoBase(request.preco_base());
        novoProduto.setPrecoTarifado(calcularPrecoTarifado(precoBase, categoria));

        return produtosRepository.save(novoProduto);
    }

    private BigDecimal calcularPrecoTarifado(BigDecimal precoBase, CategoriasSeguros categoria) {
        BigDecimal iofPercentual = categoria.getIof_percentual() != null ? categoria.getIof_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal pisPercentual = categoria.getPis_percentual() != null ? categoria.getPis_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal cofinsPercentual = categoria.getCofins_percentual() != null ? categoria.getCofins_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;

        // Fórmula: Preço Base + (Preço Base x IOF) + (Preço Base x PIS) + (Preço Base x COFINS)
        BigDecimal iof = precoBase.multiply(iofPercentual);
        BigDecimal pis = precoBase.multiply(pisPercentual);
        BigDecimal cofins = precoBase.multiply(cofinsPercentual);

        return precoBase.add(iof).add(pis).add(cofins);
    }
    public ProdutosSeguros validarCamposObrigatorios(RequestCriacaoProduto request) {
        if (request == null) {
            throw new IllegalArgumentException("Request de criação do produto não pode ser nulo.");
        }

        if (request.nome() == null || request.nome().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'nome' é obrigatório.");
        }

        if (request.categoria() == null || request.categoria().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'categoria' é obrigatório.");
        }

        if (request.preco_base() == null || request.preco_base().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O campo 'preco_base' é obrigatório e deve ser maior que zero.");
        }
        return null;
    }

}
