package br.com.itau.desafioApi.produtosDeSeguros.testesIntegrados;
import br.com.itau.desafioApi.produtosDeSeguros.dto.request.RequestCriacaoProduto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriacaoNovoProdutoTests {
    private int port = 8080;
    private String baseURI = "http://localhost";

    @Test
    void testCriarNovoProdutoVidaEVeririficarPrecoTarifado() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro de vida familia", "VIDA", new BigDecimal("100.0"));

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Seguro de vida familia"))
                .body("categoria", equalTo("VIDA"))
                .body("preco_base", equalTo(100.0F))
                .body("preco_tarifado", equalTo(103.2F));
    }
    @Test
    void testCriarNovoProdutoAutoEVeririficarPrecoTarifado() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro auto familia", "AUTO", new BigDecimal("100.0"));

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Seguro auto familia"))
                .body("categoria", equalTo("AUTO"))
                .body("preco_base", equalTo(100.0F))
                .body("preco_tarifado", equalTo(110.5F));
    }
    @Test
    void testCriarNovoProdutoViagemEVeririficarPrecoTarifado() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro viagem familia", "VIAGEM", new BigDecimal("100.0"));

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Seguro viagem familia"))
                .body("categoria", equalTo("VIAGEM"))
                .body("preco_base", equalTo(100.0F))
                .body("preco_tarifado", equalTo(107.0F));
    }
    @Test
    void testCriarNovoProdutoResidencialEVeririficarPrecoTarifado() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro residencial familia", "RESIDENCIAL", new BigDecimal("100.0"));

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Seguro residencial familia"))
                .body("categoria", equalTo("RESIDENCIAL"))
                .body("preco_base", equalTo(100.0F))
                .body("preco_tarifado", equalTo(107.0F));
    }
    @Test
    void testCriarNovoProdutoPatrimonialEVeririficarPrecoTarifado() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro patrimonial familia", "PATRIMONIAL", new BigDecimal("100.0"));

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .body("nome", equalTo("Seguro patrimonial familia"))
                .body("categoria", equalTo("PATRIMONIAL"))
                .body("preco_base", equalTo(100.0F))
                .body("preco_tarifado", equalTo(108.0F));
    }
    @Test
    void testCamposObrigatorios() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( null, null, null, null);

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(400)
                .body("status", equalTo("BAD_REQUEST"))
                .body("errors[0].message", equalTo("O campo 'nome' é obrigatório."))
                .body("errors[1].message", equalTo("O campo 'categoria' é obrigatório."))
                .body("errors[2].message", equalTo("O campo 'preco_base' é obrigatório e deve ser maior que zero."));

    }
    @Test
    void CriarProdutoComCategoriaInexistente(){
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro de vida familia", "CATEGORIAINVALIDA", new BigDecimal("100.0"));
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(404)
                .body("message", equalTo("Categoria não encontrada: CATEGORIAINVALIDA"));
    }
    @Test
    void AtualizarProdutoEVerificarPrecoTarifado(){
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;

        RequestCriacaoProduto request = new RequestCriacaoProduto( "1", "Seguro patrimonial familia", "PATRIMONIAL", new BigDecimal("100.0"));

        Response response = given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/produtos")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String productId = response.path("id");

        RequestCriacaoProduto requestPut = new RequestCriacaoProduto( productId, "Seguro patrimonial familia", "PATRIMONIAL", new BigDecimal("300.0"));
        given()
                .contentType("application/json")
                .body(requestPut)
                .when()
                .put("/produtos")
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(productId)))
                //.body("precoBase", equalTo(300.0F))
                .body("precoTarifado", equalTo(324.0F));
    }

}