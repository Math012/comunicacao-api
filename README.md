# Resolução 01 - Melhorias em um Projeto Existente


## Entrada e saída de dados

- Json de entrada, ComunicacaoInDTO.

```jason
{
    "dataHoraEnvio": "11-07-2025 08:40:00",
    "nomeDestinatario": "Jhon",
    "emailDestinatario": "max@max.com",
    "telefoneDestinatario": "119999999",
    "mensagem": "Olá, max",
    "nomeRemetente": "Jhon",
    "modoDeEnvio": "EMAIL"
}
```
- Json de saída, ComunicacaoOutDTO.
  
```jason
{
    "id": 8,
    "dataHoraEnvio": "11-07-2025 03:40:00",
    "nomeDestinatario": "Jhon",
    "emailDestinatario": "max@max.com",
    "telefoneDestinatario": "119999999",
    "mensagem": "Olá, max",
    "nomeRemetente": "Jhon",
    "modoDeEnvio": "EMAIL",
    "statusEnvio": "PENDENTE"
}
```

## Endpoints da aplicação 


| Método | URL | Descrição
|---|---|---|
| `POST` | localhost:8080/comunicacao/agendar | realiza o agendamento de um comunicado, recebendo em seu corpo de requisição um body de ComunicacaoInDTO, em caso de sucesso a requisição retorna um status code de 200 e um body de ComunicacaoOutDTO, em caso de erros a requisição retorna um status code de 400 ou 500.
| `GET` | localhost:8080/comunicacao | realiza a busca do status de um comunicado através do e-mail do destinatário passado via parâmetro. Em caso de sucesso, a requisição retorna um status code de 200 e um body de ComunicacaoOutDTO, em caso de erros, a requisição retorna um status code de 400 ou 500.
| `PATCH` | localhost:8080/comunicacao/cancelar | realiza a alteração do status de envio do comunicado para cancelado através do e-mail do destinatário passado via parâmetro. Em caso de sucesso, a requisição retorna um status code de 200 e um body de ComunicacaoOutDTO, em caso de erros, a requisição retorna um status code de 400 ou 500.
| `PUT` | localhost:8080/comunicacao | realiza a alteração do comunicado através do e-mail do destinatário e recebe em seu corpo de requisição um body de ComunicacaoInDTO. Em caso de sucesso, a requisição retorna um status code de 200 e um body de ComunicacaoOutDTO. Em caso de erros, a requisição retorna um status code de 400 ou 500.
| `DELETE` | localhost:8080/comunicacao/cancelar | deleta um comunicado através do e-mail do destinatário, em caso de sucesso a requisição retorna um status code de 200 e não retorna nenhum body, em caso de erros a requisição retorna um status code de 400 ou 500.

## Implementação do Swagger

- url: http://localhost:8080/swagger-ui/index.html#

<img width="700" height="400" alt="image" src="https://github.com/user-attachments/assets/068a4490-8bdd-44a1-940a-00495adc01ce" />

##  Integração com API externa.

- A integração foi feita com a API de enviar e-mails (https://github.com/Math012/notifica-tarefa) com uma pequena alteração no formato do email, a primeira versão do projeto estava enviando lembretes de tarefas, neste caso eu criei um novo layout thymeleaf para enviar mensagens e criei um endpoint especifico para o envio de mensagens.
<img width="700" height="400" alt="image" src="https://github.com/user-attachments/assets/52a6957b-1479-49ae-b309-105220dc3f0a" />

- A API comunicacao_api possui um Cron próprio que utiliza a configuração de horário para: 0 */2 * * * *

- O email só pode ser enviado se o status do comunicado estiver como "pendente".

## Melhore a conversão DTO-Entity

- Troca total do conversor Builder para MapStruct.

```java
@Mapper(componentModel = "spring")
public interface ComunicadoMapper {
    ComunicacaoEntity paraComunicadoEntity(ComunicacaoInDTO comunicacaoInDTO);
    ComunicacaoOutDTO paraComunicadoOutDTO(ComunicacaoEntity comunicacaoEntity);
    ComunicacaoOutDTO paraComunicadoOutDTOFromComunicacaoInDTO(ComunicacaoInDTO comunicacaoInDTO);
    ComunicacaoInDTO paraComunicadoInDTOFromComunicacaoOutDTO(ComunicacaoOutDTO comunicacaoOutDTO);
    List<ComunicacaoOutDTO> paraListaComunicadoOutDTO(List<ComunicacaoEntity> comunicacaoEntities);
}
```

## Tratamento de erros e exceções
- Implementação do CustomHandlerException.

- Com a utilização do CustomHandlerException e uma classe auxiliar para definir a estrutura do body de resposta, um possível front-end conseguiria capturar as exceções e suas mensagens com uma maior facilidade.

- Classe auxiliar para definir a estrutura do body.

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StructException {
    private Date timestamp;
    private String msg;
    private String detail;
}
```

- Classe que herda a ResponseEntityExceptionHandler e possui a anotação @ControllerAdvice para controlar o retorno das exceções quando são chamadas.

```java
@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StructException> handlerResourceNotFound(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DateInvalid.class)
    public ResponseEntity<StructException> handlerDateInvalid(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<StructException> handlerInvalidFieldsException(Exception e, WebRequest request){
        StructException exception = new StructException(new Date(),e.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
```

- Exemplo de exceção ao fazer uma requisição com valores nulos ou vazios.

```jason
{
    "timestamp": "2025-07-12T13:26:49.957+00:00",
    "msg": "Erro ao agendar comunicado: campos inválidos",
    "detail": "uri=/comunicacao/agendar"
}
```

## Testes Unitários

- Testes unitários realizados nas camadas de Converter, Service e Controller

<img width="400" height="180" alt="image" src="https://github.com/user-attachments/assets/71197230-559b-46da-a11b-c89c1171013f" />


## Suba a aplicação no Docker

- Arquivo Dockerfile

```yaml
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/comunicacao_api-0.0.1-SNAPSHOT.jar /app/comunicacao_api.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/comunicacao_api.jar"]
```

- Arquivo docker-compose.yml

```yaml
services:
  db:
    image: postgres:latest
    container_name: db
    environment:
      - POSTGRES_DB=${DB_DATABASE}
      - POSTGRES_USER=${DB_USERNAME}
      - POSTGRES_PASSWORD=${DB_PASSWORD}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - minha-rede
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 5s
      timeout: 5s
      retries: 10

  app:
    build: .
    container_name: comunicacao_api
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}${DB_DATABASE}
      - SPRING_DATASOURCE_USERNAME=${DB_USERNAME}
      - SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
      - SPRING_JPA_HIBERNATE_DDL_AUTO=${SPRING_JPA_HIBERNATE_DDL_AUTO}
      - EMAIL_URL=${EMAIL_URL}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - minha-rede
volumes:
  postgres_data:

networks:
  minha-rede:
    external: true
```

- Foram usados dois serviços, um para a aplicação java e outro para subir um banco de dados postgres

- Para melhorar organização e segurança, as variáveis foram geradas em um arquivo .env
  - Variáveis
    - DB_DATABASE
    - DB_USERNAME
    - DB_PASSWORD
    - SPRING_DATASOURCE_URL
    - SPRING_JPA_HIBERNATE_DDL_AUTO
    - EMAIL_URL
- Como este microsserviço faz uma comunicação com outro microsserviço, os dois projetos estão no DockerHub
  - comunicacao_api: https://hub.docker.com/r/math012i/comunicacao_api/tags
  - notificacaoapi: https://hub.docker.com/r/math012i/notificacao-api/tags
 
  ## Docker-compose para baixar os dois serviços.
  
- Para utilizar este docker-compose é preciso informar seu email e senha SMTP.

- Criar uma rede (ou alterar para uma rede de sua preferência) chamada "minha-rede": docker network create minha-rede.

- O microsserviço de comunicacao está na porta 8080.

- O microsserviço de notificacao está na porta 8082.

- Comando para acessar o postgres via terminal: docker exec -it db psql -U postgres -d comunicacao1

```yaml
version: '3.8'
services:
  notificacao-service:
    image: math012i/notificacao-api:latest
    ports:
      - "8082:8082"
    environment:
      - USERNAME_EMAIL=SEU EMAIL SMTP
      - PASSWORD_EMAIL=SUA SENHA SMTP
    networks:
      - minha-rede

  db:
    image: postgres:latest
    container_name: db
    environment:
      - POSTGRES_DB=comunicacao1
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=1234
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - minha-rede
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 5s
      timeout: 5s
      retries: 10

  comunicacao-service:
    image: math012i/comunicacao_api:latest
    ports:
      - "8080:8080"
    environment:
      - DB_DATABASE=comunicacao1
      - DB_USERNAME=postgres
      - DB_PASSWORD=1234
      - EMAIL_URL=http://notificacao-service:8082/email
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    depends_on:
      db:
        condition: service_healthy
    networks:
      - minha-rede

volumes:
  postgres_data:

networks:
  minha-rede:
    external: true
```
