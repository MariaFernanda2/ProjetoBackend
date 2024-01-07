package br.com.itau.desafioApi.produtosDeSeguros.service;

import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.CategoriasSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.domain.product.entity.ProdutosSeguros;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CriacaoProdutoException;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.CategoriasNotFoundException;
import br.com.itau.desafioApi.produtosDeSeguros.exceptions.EdicaoProdutoException;
import br.com.itau.desafioApi.produtosDeSeguros.repository.CategoriasProdutosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.repository.ProdutosSegurosRepository;
import br.com.itau.desafioApi.produtosDeSeguros.dto.request.RequestCriacaoProduto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        String categoriaInformada = request.categoria();
        CategoriasSeguros categoria = categoriasRepository.findByCategoria(categoriaInformada)
                .orElseThrow(()-> new CategoriasNotFoundException("Categoria não encontrada: " + categoriaInformada));


        ProdutosSeguros novoProduto = new ProdutosSeguros();
        novoProduto.setNome(request.nome());
        novoProduto.setCategoria(categoria.getCategoria());
        novoProduto.setPrecoBase(request.preco_base());
        novoProduto.setPrecoTarifado(calcularPrecoTarifado(precoBase, categoria));

        return produtosRepository.save(novoProduto);
    }

    public BigDecimal calcularPrecoTarifado(BigDecimal precoBase, CategoriasSeguros categoria) {
        BigDecimal iofPercentual = categoria.getIof_percentual() != null ? categoria.getIof_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal pisPercentual = categoria.getPis_percentual() != null ? categoria.getPis_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal cofinsPercentual = categoria.getCofins_percentual() != null ? categoria.getCofins_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;

        // Fórmula: Preço Base + (Preço Base x IOF) + (Preço Base x PIS) + (Preço Base x COFINS)
        BigDecimal iof = precoBase.multiply(iofPercentual);
        BigDecimal pis = precoBase.multiply(pisPercentual);
        BigDecimal cofins = precoBase.multiply(cofinsPercentual);

        return precoBase.add(iof).add(pis).add(cofins);
    }
        public void validarCamposObrigatorios(RequestCriacaoProduto request) {
            List<String> mensagensErro = new ArrayList<>();

            if (request.nome() == null || request.nome().trim().isEmpty()) {
                mensagensErro.add("O campo 'nome' é obrigatório.");
            }

            if (request.categoria() == null || request.categoria().trim().isEmpty()) {
                mensagensErro.add("O campo 'categoria' é obrigatório.");
            }

            if (request.preco_base() == null || request.preco_base().compareTo(BigDecimal.ZERO) <= 0) {
                mensagensErro.add("O campo 'preco_base' é obrigatório e deve ser maior que zero.");
            }
            if (!mensagensErro.isEmpty()) {
                throw new CriacaoProdutoException(mensagensErro);
            }

            }
    public void validarCamposObrigatoriosEdicao(RequestCriacaoProduto request) {
        if (request.id() == null || request.nome().trim().isEmpty()) {
            throw new EdicaoProdutoException("O id do produto deve ser informado");
        }
    }
        public void validarCategoriaInformada(RequestCriacaoProduto request){
            String categoriaInformada = request.categoria();
            CategoriasSeguros categoria = categoriasRepository.findByCategoria(categoriaInformada)
                    .orElseThrow(()-> new CategoriasNotFoundException("Categoria não encontrada: " + categoriaInformada));

        }

        }
