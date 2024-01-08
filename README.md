
# ☕️ CRUD de Produtos com Calculadora de Preço Tarifado



## 📃 Linguagem e tecnologias utilizadas
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%23007ACC.svg?style=for-the-badge&logo=spring&logoColor=white)

![Apache Groovy](https://img.shields.io/badge/Apache%20Groovy-%23E57200.svg?style=for-the-badge&logo=apache&logoColor=white)

![Flyway](https://img.shields.io/badge/Flyway-%23007ACC.svg?style=for-the-badge&logo=flyway&logoColor=white)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%23336791.svg?style=for-the-badge&logo=postgresql&logoColor=white)

![Prometheus](https://img.shields.io/badge/Prometheus-%23366DA4.svg?style=for-the-badge&logo=prometheus&logoColor=white)

[![JUnit5](https://img.shields.io/badge/JUnit5-%23525A6E.svg?style=for-the-badge&logo=junit5)](https://junit.org/junit5/)

- Mockito
- Rest Assured - Biblioteca Java para testar APIs RESTful.



## 💡 Funcionalidades Principais

1. **Criação de Novo Produto:**
   - Endpoint: `POST /produtos`
   - Cria um novo produto com base nos dados fornecidos, incluindo o cálculo do preço tarifado.

2. **Listagem de Produtos:**
   - Endpoint: `GET /produtos`
   - Lista todos os produtos ou filtrados por categoria.

3. **Detalhes do Produto por ID:**
   - Endpoint: `GET /produtos/{id}`
   - Obtém detalhes de um produto específico com base no ID.

4. **Atualização de Produto:**
   - Endpoint: `PUT /produtos`
   - Atualiza os detalhes de um produto existente, recalculando o preço tarifado.

5. **Exclusão de Produto:**
   - Endpoint: `DELETE /produtos/{id}`
   - Exclui um produto com base no ID.

6. **Listagem de Categorias de Produtos:**
   - Endpoint: `GET /produtos_categorias`
   - Lista todas as categorias de produtos.

## ⁉️ Tratamento de Exceções

- Todas as exceções relacionadas à criação e edição de produtos são tratadas por classes específicas, garantindo um tratamento adequado dos erros.

```java
public class CriacaoProdutoException extends RuntimeException {
    private final List<String> mensagensErro;

    public CriacaoProdutoException(List<String> mensagensErro) {
        super("Campos obrigatórios não foram preenchidos");
        this.mensagensErro = mensagensErro;
    }

    public List<String> getMensagensErro() {
        return mensagensErro;
```
### 📚 Arquitetura do projeto
![Projeto](https://github.com/MariaFernanda2/ProjetoBackend/blob/main/1.PNG?raw=true)

## 📑Estrutura do Código

### Controllers

#### ProductsController.java
O controlador principal responsável por manipular as requisições relacionadas aos produtos.

```java
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

   ```

#### CategoriasProdutosController.java

```java
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
 ```

### Service

#### ProdutoService.java
O serviço responsável pela lógica de negócios relacionada aos produtos.

```java
public BigDecimal calcularPrecoTarifado(BigDecimal precoBase, CategoriasSeguros categoria) {
        BigDecimal iofPercentual = categoria.getIof_percentual() != null ? categoria.getIof_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal pisPercentual = categoria.getPis_percentual() != null ? categoria.getPis_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
        BigDecimal cofinsPercentual = categoria.getCofins_percentual() != null ? categoria.getCofins_percentual().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;

        // Fórmula: Preço Base + (Preço Base x IOF) + (Preço Base x PIS) + (Preço Base x COFINS)
        BigDecimal iof = precoBase.multiply(iofPercentual);
        BigDecimal pis = precoBase.multiply(pisPercentual);
        BigDecimal cofins = precoBase.multiply(cofinsPercentual);

        return precoBase.add(iof).add(pis).add(cofins);

```

### ⚙️ Configurações do Banco de Dados
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ProdutosSeguro
spring.datasource.username=postgres
spring.datasource.password=iacgmm00
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
spring.jpa.hibernate.ddl-auto=update
```

### ⚙️ Configurações do Spring Boot
```properties
management.endpoints.web.exposure.include=*
management.endpoint.metrics.enabled=true
logging.level.org.springframework.web=DEBUG
```

## ✅ Como Executar o Projeto
### Requisitos:

- Java 8 ou superior
- Maven

### ❕ Instruções:

- Clone o repositório: git clone https://github.com/MariaFernanda2/ProjetoBackend
- Navegue até o diretório do projeto: cd ProjetoBackend
- Execute a aplicação:
- 
```bash
mvn spring-boot:run
```

## 🆗 Testes
### Testes Unitários:
Os testes unitários são usados para validar a lógica interna e os comportamentos individuais de componentes específicos do sistema. Eles garantem que unidades de código, como métodos e classes, funcionem conforme o esperado. Para executar os testes unitários, utilize o seguinte comando:

```bash
mvn test
```

### Testes de Integração:
Os testes de integração são utilizados para validar a interação entre diferentes partes do sistema, garantindo que os componentes se integrem corretamente. No projeto, os testes de integração utilizam o RestAssured para verificar a funcionalidade dos endpoints da API. Para executar os testes de integração, utilize o seguinte comando:
```bash
 mvn verify 
```

## 🔍 Métricas e Observabilidade
- Prometheus está configurado para coletar métricas.
  Consulte as métricas em: http://localhost:9090
