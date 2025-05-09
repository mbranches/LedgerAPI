# Ledger APi

> Api desenvolvida em Java com Spring Boot para registrar transações financeiras.

## 🎯 Funcionalidades
- ✅ **Cadastro de Transações** 
- ✅ **Listar histórico de Transações**
- ✅ **Filtrar Transações por data**
- ✅ **Listar entradas**
- ✅ **Listar saídas**
- ✅ **Consultar saldo atual**
- ✅ **Exportar transações em Json e CSV**

## Endpoints e Exemplos de Uso

### 1. Cadastrar uma Transação (Entrada)
```
    POST /v1/transactions
```

#### Atributos requeridos:
- `value`: Valor da transação (positivo para entrada, negativo para saída)
- `description`: Descrição da transação

#### Exemplo de Requisição:
```
curl -X POST http://localhost:8888/v1/transactions \
-H "Content-Type: application/json" \
-d '{"value": 100.00, "description": "Venda de produto"}'
```

#### Exemplo de Resposta:
```json
{
    "id": 1,
    "value": 100.00,
    "description": "Venda de produto",
    "date": "2025-04-01T12:35:37.232502"
}
```

### 2. Cadastro de Transação (Saída)
```
    POST /v1/transactions
```

#### Exemplo de Requisição:
```
curl -X POST http://localhost:8888/v1/transactions \
-H "Content-Type: application/json" \
-d '{"value": -650.00, "description": "Conta de energia"}'
```

#### Exemplo de Resposta:
```json
{
    "id": 2,
    "value": -650.00,
    "description": "Conta de energia",
    "date": "2025-05-08T17:14:22.282612"
}
```

### 3. Listar Transações
```
    GET /v1/transactions
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions
```

#### Exemplo de Resposta:
```json
[
    {
        "id": 1,
        "value": 100.00,
        "description": "Venda de produto",
        "date": "2025-04-01T12:35:37.232502"
    },
    {
        "id": 2,
        "value": -650.00,
        "description": "Conta de energia",
        "date": "2025-05-08T17:14:22.282612"
    }
]
```

### 4. Filtrar Transações por Data
```
    GET /v1/transactions?startDate=DATA-INICIAL&endDate=DATA-FINAL
```

### Parâmetros:
- startDate: Data inicial (formato ISO 8601)
- endDate: Data final (formato ISO 8601)

> Obs: Ambos os parâmetros são opcionais e podem ser utilizados isoladamente.

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions?startDate=2025-04-01T12:35:37&endDate=2025-05-08T17:15:00
```

#### Exemplo de Resposta:
```json
[
    {
        "id": 1,
        "value": 100.00,
        "description": "Venda de produto",
        "date": "2025-04-01T12:35:37.232502"
    },
  {
    "id": 2,
    "value": -650.00,
    "description": "Conta de energia",
    "date": "2025-05-08T17:14:22.282612"
  }
]
```

### 5. Listar Entradas
```
    GET /v1/transactions/expenses
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions/expenses
```

#### Exemplo de Resposta:
```json
[
    {
        "id": 1,
        "value": 100.00,
        "description": "Venda de produto",
        "date": "2025-04-01T12:35:37.232502"
    }
]
```

### 6. Listar Saídas
```
    GET /v1/transactions/incomes
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions/incomes
```

#### Exemplo de Resposta:
```json
[
    {
        "id": 2,
        "value": -650.00,
        "description": "Conta de energia",
        "date": "2025-05-08T17:14:22.282612"
    }
]
```

### 7. Consultar Saldo Atual
```
    GET /v1/transactions/balance
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions/balance
```

#### Exemplo de Resposta:
```json
{
    "balance": -550.00
}
```

### 8. Exportar Transações em JSON
```
    GET /v1/transactions/export/json
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions/export/json
```

#### Resposta esperada: Arquivo `transactions.json` com o conteúdo:
```json
[
    {
        "id": 1,
        "value": 100.00,
        "description": "Venda de produto",
        "date": "2025-04-01T12:35:37.232502"
    },
    {
        "id": 2,
        "value": -650.00,
        "description": "Conta de energia",
        "date": "2025-05-08T17:14:22.282612"
    }
]
```

### 9. Exportar Transações em CSV
```
    GET /v1/transactions/export/csv
```

#### Exemplo de Requisição:
```
curl http://localhost:8888/v1/transactions/export/csv
```

#### Resposta esperada: Arquivo `transactions.csv` com o conteúdo:
```csv
id,value,description,date
1,100.00,"Venda de produto","2025-04-01T12:35:37.232502"
2,-650.00,"Conta de energia","2025-05-08T17:14:22.282612"
```

## Tecnologias Utilizadas
- ![Java](https://img.shields.io/badge/Java-21-blue?logo=java) - Linguagem de programação utilizada.
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?logo=spring)  - Framework para criação de APIs REST em Java.
- ![JPA](https://img.shields.io/badge/JPA-blue?logo=eclipselink) - Framework para mapeamento objeto-relacional.
- ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker) - Conteinerização do Banco de dados.
- ![MySQL](https://img.shields.io/badge/MySQL-black?logo=mysql) - Banco de dados utilizado.
- ![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven) - Gerencia dependências e automação de builds para projetos Java.

## 🚀 Como Executar o Projeto
### Pré-requisitos
- Java 21
- Maven 
- Docker

### 1. Clone o repositório dos desafios:
```
git clone https://github.com/mbranches/desafios.git
```

### 2. Navegue até o diretório do projeto:
```
cd desafios/0001-ledger/mbranches
```

### 3. Popule as variáveis de ambiente

#### a. Variáveis usadas no Docker
- Renomeie o arquivo `.envTemplate`, localizado na raiz desse projeto, para `.env`.
- Preencha as variáveis de ambiente com os valores desejados.

#### b. Variáveis usadas no Spring Boot
- Renomeie o arquivo `.envTemplate`, localizado em `src/main/resources`, para `.env`.
- Preencha as variáveis de ambiente com os mesmos valores definidos no arquivo `.env` da raiz desse projeto.

### 4. Rodar o container do banco de dados
```
docker-compose up -d
```

### 5. Executar a aplicação
```
mvn spring-boot:run
```
Agora você pode acessar a aplicação em `http://localhost:8888`.


## 🧪 Testes
### Testes Unitários
- Testes da camada de serviço, que garantem que a lógica de negócio funcione corretamente.
- Utilizam `JUnit` para estruturação dos testes, `Mockito` para mockar dependências e `AssertJ` para asserções fluentes.

### Testes de Controller (Camada Web)
- Validam o comportamento dos endpoints REST da aplicação em isolamento.
- Configurados com `@WebMvcTest` inicializam apenas os beans da camada de controller sem carregar todo o contexto do Spring.
- Utilizam `MockMVC` para simular requisições HTTP.
- Testam os status HTTP e os payloads das requisições e respostas.

### Para rodar os testes, execute o seguinte comando na raiz desse projeto:
```
mvn test
```

## Especificações Técnicas

---

Escolhi Java com Spring Boot pelas seguintes razões;
- **Familiaridade**: Tenho mais experiência com essa stack, o que me permite desenvolver de forma mais eficiente.
- **Praticar**: O projeto permitiu-me pôr em prática os meus conhecimentos na linguagem e no framework.
- **Organização**: A estrutura do Spring facilita a separação de responsabilidades e manutenção do código.