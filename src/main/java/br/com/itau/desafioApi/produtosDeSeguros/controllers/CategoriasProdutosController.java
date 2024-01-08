package br.com.itau.desafioApi.produtosDeSeguros.controllers;


import br.com.itau.desafioApi.produtosDeSeguros.repository.CategoriasProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos_categorias")
public class CategoriasProdutosController {

    @Autowired
    private CategoriasProdutosRepository repository;

    @GetMapping
    public ResponseEntity getAllProducts() {
        var todosProdutos = repository.findAll();
        return ResponseEntity.ok(todosProdutos);
    }
}