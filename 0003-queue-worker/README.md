# Desafio 0003: Sistema de Fila e Workers

## Descrição
Neste desafio, você deve criar um sistema simples que demonstre o conceito de filas e workers. O sistema deve processar tarefas de forma assíncrona, permitindo melhor gerenciamento de recursos e escalabilidade.

## Requisitos

1. Criar um sistema com os seguintes componentes:
   - Um produtor (producer) que envia tarefas para a fila
   - Uma fila (queue) para armazenar as tarefas
   - Um ou mais workers que processam as tarefas da fila

2. Implementar um cenário real:
   - Criar um sistema de processamento de imagens
   - O produtor envia URLs de imagens para processamento
   - Os workers baixam as imagens, redimensionam e aplicam um filtro
   - O resultado deve ser salvo em um diretório de saída

3. Funcionalidades obrigatórias:
   - Sistema de retry para tarefas que falham
   - Logging das operações
   - Tratamento de erros
   - Monitoramento do status das tarefas

## Exemplo de Uso

```python
# Exemplo de como enviar uma tarefa (Producer)
producer.send_task({
    "image_url": "https://example.com/image.jpg",
    "width": 800,
    "height": 600,
    "filter": "grayscale"
})
```

## Critérios de Avaliação

1. Implementação correta dos conceitos de fila e workers
2. Tratamento adequado de erros e retries
3. Qualidade do código e boas práticas
4. Documentação clara
5. Testes unitários e de integração
6. Performance e escalabilidade da solução

## Tecnologias Sugeridas
- Python (com Celery ou RQ)
- Node.js (com Bull ou RabbitMQ)
- Java (com Spring AMQP)
- Go (com RabbitMQ ou Redis)
- Elixir (com Oban)
- Qualquer outra stack de sua preferência

## Como Participar

1. Faça um fork deste repositório
2. Crie uma pasta com seu nome dentro do diretório `0003-queue-worker`
3. Implemente sua solução
4. Abra um pull request com sua implementação

## Dicas
- Use bibliotecas populares de filas como RabbitMQ, Redis, ou Amazon SQS
- Implemente um mecanismo de dead letter queue para tarefas que falham repetidamente
- Considere usar um sistema de monitoramento para acompanhar o processamento
- Pense em como escalar o sistema com múltiplos workers
- Implemente um mecanismo de priorização de tarefas
- Considere como lidar com workers que morrem durante o processamento

## Referências
- [Message Queues: A Deep Dive](https://www.rabbitmq.com/getstarted.html) - Tutorial oficial do RabbitMQ
- [Understanding Message Queues](https://www.youtube.com/watch?v=oUJbuFMyBDk) - Vídeo explicativo sobre filas de mensagens
- [Distributed Systems: Queue Theory](https://www.youtube.com/watch?v=KHhLor1Soa0) - Teoria sobre filas em sistemas distribuídos
- [Celery Documentation](https://docs.celeryproject.org/en/stable/) - Documentação do Celery (Python)
- [Bull Queue Documentation](https://github.com/OptimalBits/bull) - Documentação do Bull (Node.js)
- [Spring AMQP Guide](https://spring.io/guides/gs/messaging-rabbitmq/) - Guia do Spring AMQP (Java)
- [Go RabbitMQ Tutorial](https://www.rabbitmq.com/tutorials/tutorial-one-go.html) - Tutorial de RabbitMQ com Go
- [Dead Letter Queues](https://www.rabbitmq.com/dlx.html) - Explicação sobre Dead Letter Queues
- [Queue Patterns](https://martinfowler.com/articles/patterns-of-distributed-systems/singular-update-queue.html) - Padrões de filas por Martin Fowler
