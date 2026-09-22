# Plataforma de Autenticação

Projeto desenvolvido durante meus estudos de desenvolvimento Back-end com Java e Spring Boot.

A aplicação possui uma interface simples integrada a uma API REST para gerenciar usuários e realizar autenticação. O foco foi praticar a comunicação entre front-end e back-end, operações CRUD, validações e cuidados básicos com senhas.

## Funcionalidades

- Interface simples para interagir com a aplicação
- Cadastro, consulta, atualização e exclusão de usuários
- Login por e-mail e senha
- Validação de nome, e-mail e senha
- Senhas armazenadas como hash BCrypt
- Respostas da API sem exposição do campo `senha`
- Tratamento de credenciais inválidas com `401 Unauthorized`
- Console H2 para desenvolvimento local
- Testes automatizados para CRUD e login

## Tecnologias

### Back-end

- Java 25
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- Gradle
- BCrypt

### Front-end

- Interface simples integrada à API REST

Index.html
Script.js
Style.css

## Como executar a API

1. Clone o repositório.
2. Entre na pasta do projeto.
3. Execute:

```powershell
.\gradlew.bat bootRun
```

4. A API estará disponível em `http://localhost:8080`.

Para compilar e executar os testes:

```powershell
.\gradlew.bat clean build
```

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| POST | `/usuarios` | Cadastra um usuário |
| GET | `/usuarios` | Lista usuários |
| GET | `/usuarios/{id}` | Busca um usuário por ID |
| PUT | `/usuarios/{id}` | Atualiza um usuário |
| DELETE | `/usuarios/{id}` | Remove um usuário |
| POST | `/usuarios/login` | Realiza o login |

## Exemplo de cadastro

`POST /usuarios`

```json
{
  "nome": "Lucca",
  "email": "lucca@email.com",
  "senha": "123456"
}
```

Resposta (`201 Created`):

```json
{
  "id": 1,
  "nome": "Lucca",
  "email": "lucca@email.com"
}
```

## Exemplo de login

`POST /usuarios/login`

```json
{
  "email": "lucca@email.com",
  "senha": "123456"
}
```

Resposta (`200 OK`):

```json
{
  "mensagem": "Login realizado com sucesso",
  "usuario": {
    "id": 1,
    "nome": "Lucca",
    "email": "lucca@email.com"
  }
}
```

Com credenciais inválidas, a API retorna `401 Unauthorized`:

```json
{
  "mensagem": "E-mail ou senha inválidos"
}
```

## Banco H2

Com a aplicação em execução, acesse `http://localhost:8080/h2-console`.

- JDBC URL: `jdbc:h2:mem:usuariosdb`
- Usuário: `sa`
- Senha: deixe em branco

```sql
SELECT * FROM USUARIOS;
```

O campo `SENHA` deve conter um hash BCrypt, nunca a senha original.

## Próximos passos

- Versionar o front-end junto ao projeto.
- Implementar JWT para proteger rotas privadas.
- Evoluir a interface e melhorar a experiência de uso.

## Autor

Projeto desenvolvido para prática de Java, Spring Boot, APIs REST e integração entre front-end e back-end.
