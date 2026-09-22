# API de Usuários

Projeto de uma API REST desenvolvida durante meus estudos de Java e Spring Boot. A aplicação permite cadastrar, consultar, atualizar, excluir e autenticar usuários, sempre evitando expor senhas nas respostas da API.

## Tecnologias

- Java 25
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- Gradle
- BCrypt (`spring-security-crypto`)

## Funcionalidades

- CRUD completo de usuários
- Validação de nome, e-mail e senha
- Senhas armazenadas com hash BCrypt
- Login por e-mail e senha
- Respostas sem o campo de senha
- Console H2 para desenvolvimento local

## Como executar

1. Clone o repositório.
2. Entre na pasta do projeto.
3. Execute no terminal:

```powershell
.\gradlew.bat bootRun
```

4. A API estará disponível em `http://localhost:8080`.

Para verificar se o projeto compila e os testes passam:

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
| POST | `/usuarios/login` | Autentica um usuário |

### Cadastro

`POST /usuarios`

```json
{
  "nome": "Lucca",
  "email": "lucca@email.com",
  "senha": "123456"
}
```

Resposta esperada (`201 Created`):

```json
{
  "id": 1,
  "nome": "Lucca",
  "email": "lucca@email.com"
}
```

### Login

`POST /usuarios/login`

```json
{
  "email": "lucca@email.com",
  "senha": "123456"
}
```

Resposta esperada (`200 OK`):

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

Com credenciais inválidas, a API retorna `401 Unauthorized` sem informar se o e-mail ou a senha falhou:

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

Para consultar os dados:

```sql
SELECT * FROM USUARIOS;
```

O campo `SENHA` deve conter um hash iniciado por `$2a$` ou `$2b$`, nunca a senha em texto puro.

## Observação sobre autenticação

Este projeto implementa a validação de login com BCrypt como exercício de segurança. Em uma próxima evolução, o endpoint de login pode emitir um token JWT para proteger rotas privadas.

## Autor

Desenvolvido como projeto de estudo de Java e Spring Boot.
