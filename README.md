## Para rodar o projeto:

### Configurando o ambiente
1. Clone o repositório
2. Necessário ter Java 21 instalado
3. Necessário ter o Maven instalado
4. Necessário ter o Docker instalado
5. Necessário ter Postgres instalado (Ajustar username e password no arquivo application.properties)
6. Rode a aplicação com o comando `mvn spring-boot:run`

### Para não se preocupar com ambiente
1. Clone o repositório
2. Rode o comando `docker-compose up`
3. Já pode fazer as requisições a partir do [Postman](./Gestao-Estoque.postman_collection.json) ou Insomnia

### Gerar relatório Excel
1. Acesse a URL `http://localhost:8080/reports?type=${type}` para gerar o relatório. Types: [LOW_STOCK, BEST_SELLERS, MOVEMENTS]