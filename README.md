# FikFit API

FikFit é uma API para gestão de personal trainers, alunos e perfis de usuários, desenvolvida em Java com Spring Boot, PostgreSQL, autenticação JWT e documentação Swagger.

## Funcionalidades
- Cadastro e autenticação de usuários (JWT)
- Associação de alunos a usuários
- Cadastro e consulta de alunos
- Estrutura de perfil de usuário (profile)
- Endpoints protegidos por token JWT
- Documentação interativa com Swagger
- Pronto para Docker e Docker Compose

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- JWT (jjwt)
- Swagger (springdoc-openapi)
- Docker

## Como rodar o projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/juulhao/fikfit.git
cd fikfit
```

### 2. Subir com Docker Compose
```bash
docker-compose up --build
```
A aplicação estará disponível em `http://localhost:8080`.

### 3. Acessar o Swagger
Abra no navegador:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- ou [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Fluxo de uso
1. **Registrar usuário:** `POST /api/v1/auth/register`
2. **Login:** `POST /api/v1/auth/login` (recebe token JWT)
3. **Criar/atualizar perfil:** `POST /api/v1/profile` (enviar JWT no header)
4. **Cadastrar aluno:** `POST /api/v1/student` (enviar JWT no header)
5. **Consultar alunos:** `GET /api/v1/student/all` (enviar JWT no header)

## Exemplo de uso do JWT
Após o login, envie o token JWT no header das requisições:
```
Authorization: Bearer SEU_TOKEN_JWT
```

## Estrutura de pastas
```
src/main/java/com/fikfit/api/
  controller/   # Controllers REST
  entity/       # Entidades JPA
  repository/   # Repositórios JPA
  service/      # Serviços de negócio
  security/     # Interceptor JWT e configs
  config/       # Configurações (Swagger)
```

## Contribuição
Pull requests são bem-vindos! Para grandes mudanças, abra uma issue primeiro para discutir o que você gostaria de modificar.

## Licença
[MIT](LICENSE)
