# Desafio 0002: API Idempotente

## Descrição
Neste desafio, você deve criar uma API que demonstre o conceito de idempotência. Uma operação idempotente é aquela que, quando aplicada múltiplas vezes, produz o mesmo resultado que se fosse aplicada uma única vez.

## Requisitos

1. Criar uma API REST que implemente um endpoint para criar um recurso (por exemplo, uma ordem de pagamento)
2. A API deve ser idempotente, garantindo que múltiplas requisições com o mesmo ID de idempotência produzam o mesmo resultado
3. Implementar validação de entrada
4. Implementar tratamento de erros
5. Documentar a API (usando Swagger/OpenAPI ou similar)
6. Incluir testes unitários e de integração

## Exemplo de Uso

```http
POST /api/v1/orders
Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json

{
    "amount": 100.00,
    "currency": "BRL",
    "description": "Test order"
}
```

## Critérios de Avaliação

1. Implementação correta do conceito de idempotência
2. Qualidade do código e boas práticas
3. Cobertura de testes
4. Documentação clara
5. Tratamento adequado de erros
6. Performance e escalabilidade da solução

## Tecnologias Sugeridas
- Node.js/TypeScript
- Python/FastAPI
- Java/Spring Boot
- Go
- Qualquer outra stack de sua preferência

## Como Participar

1. Faça um fork deste repositório
2. Crie uma pasta com seu nome dentro do diretório `0002-idempotent-api`
3. Implemente sua solução
4. Abra um pull request com sua implementação

## Dicas
- Considere usar um banco de dados para armazenar o estado das operações
- Implemente um mecanismo de limpeza para chaves de idempotência antigas
- Pense em casos de concorrência e como lidar com eles
- Considere diferentes cenários de falha e como recuperar-se deles
