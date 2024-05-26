# Documentação da API

O aplicativo irá consumir uma api desenvolvida no projeto "uenf educar" desenvolvida em `node.js`, em conjunto com o banco de dados não relacional `MongoDB`, a api implementa como praticas de segurança token de acesso `JWT` e criptografia `BCrypt`.

Autores da API: Matheus Shulz e Fernando Linhares

## Usuários

### Criar um usuário
- **URL:** `/user`
- **Método:** `POST`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Corpo da Requisição:**
~~~json
{
  "name": "Nome do Usuário",
  "email": "usuario@exemplo.com",
  "password": "senha123",
  "isAdmin": false
}
~~~
- **Resposta:**
~~~json
{
  "message": "Usuário criado com sucesso!",
  "user": {
    "_id": "60d2ca57d6c9850015d49cb7",
    "name": "Nome do Usuário",
    "email": "usuario@exemplo.com",
    "isAdmin": false,
    "createdAt": "2023-05-25T12:34:56.789Z",
    "updatedAt": "2023-05-25T12:34:56.789Z"
  }
}
~~~

### Obter dados do usuário
- **URL:** `/user`
- **Método:** `GET`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Resposta:**
~~~json
{
  "_id": "60d2ca57d6c9850015d49cb7",
  "name": "Nome do Usuário",
  "email": "usuario@exemplo.com",
  "isAdmin": false,
  "createdAt": "2023-05-25T12:34:56.789Z",
  "updatedAt": "2023-05-25T12:34:56.789Z"
}
~~~

### Obter todos os usuários
- **URL:** `/users`
- **Método:** `GET`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Resposta:**
~~~json
{
  "users": [
    {
      "_id": "60d2ca57d6c9850015d49cb7",
      "name": "Nome do Usuário",
      "email": "usuario@exemplo.com",
      "isAdmin": false,
      "createdAt": "2023-05-25T12:34:56.789Z",
      "updatedAt": "2023-05-25T12:34:56.789Z"
    }
  ]
}
~~~

### Obter um usuário pelo ID
- **URL:** `/user/{id}`
- **Método:** `GET`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Resposta:**
~~~json
{
  "_id": "60d2ca57d6c9850015d49cb7",
  "name": "Nome do Usuário",
  "email": "usuario@exemplo.com",
  "isAdmin": false,
  "createdAt": "2023-05-25T12:34:56.789Z",
  "updatedAt": "2023-05-25T12:34:56.789Z"
}
~~~

### Atualizar um usuário
- **URL:** `/user/{id}`
- **Método:** `PUT`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Corpo da Requisição:** Campos a serem atualizados no usuário.
~~~json
{
  "name": "Nome Atualizado",
  "email": "usuario@exemplo.com",
  "isAdmin": false
}
~~~
- **Resposta:**
~~~json
{
  "message": "Usuário atualizado com sucesso!",
  "user": {
    "_id": "60d2ca57d6c9850015d49cb7",
    "name": "Nome Atualizado",
    "email": "usuario@exemplo.com",
    "isAdmin": false,
    "createdAt": "2023-05-25T12:34:56.789Z",
    "updatedAt": "2024-01-01T12:34:56.789Z"
  }
}
~~~

### Excluir um usuário
- **URL:** `/user/{id}`
- **Método:** `DELETE`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação de administrador)
- **Resposta:**
~~~json
{
  "message": "Usuário deletado com sucesso!"
}
~~~

## Bibliotecas

### Criar uma biblioteca
- **URL:** `/library`
- **Método:** `POST`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Corpo da Requisição:**
~~~json
{
  "name": "Nome da Biblioteca",
  "description": "Descrição da Biblioteca",
  "slug": "nome-da-biblioteca"
}
~~~
- **Resposta:**
~~~json
{
  "message": "Biblioteca criada com sucesso!"
}
~~~

### Obter todas as bibliotecas
- **URL:** `/libraries`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "libraries": [
    {
      "_id": "60d2ca57d6c9850015d49cb7",
      "name": "Nome da Biblioteca",
      "description": "Descrição da Biblioteca",
      "slug": "nome-da-biblioteca",
      "createdAt": "2023-05-25T12:34:56.789Z",
      "updatedAt": "2023-05-25T12:34:56.789Z"
    }
  ]
}
~~~

## Documentos

### Upload de um documento
- **URL:** `/document/upload`
- **Método:** `POST`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Resposta:**
~~~json
{
  "message": "Arquivo enviado com sucesso",
  "pathName": "/caminho/para/o/arquivo"
}
~~~

### Download de um documento
- **URL:** `/document/download/{id}`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "message": "Arquivo baixado com sucesso"
}
~~~

### Criar um documento
- **URL:** `/document`
- **Método:** `POST`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Corpo da Requisição:**
~~~json
{
  "title": "Título do Documento",
  "description": "Descrição do Documento",
  "type": "ARTIGO",
  "file": "caminho/para/o/arquivo",
  "researchArea": "Área de Pesquisa",
  "library": "ID da Biblioteca",
  "date": "2023-05-25T12:34:56.789Z",
  "language": "PT",
  "authors": ["Autor 1", "Autor 2"],
  "keywords": ["Palavra-chave 1", "Palavra-chave 2"]
}
~~~
- **Resposta:**
~~~json
{
  "message": "Documento criado com sucesso!"
}
~~~

### Obter todos os documentos
- **URL:** `/documents`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "documents": [
    {
      "_id": "60d2ca57d6c9850015d49cb7",
      "title": "Título do Documento",
      "description": "Descrição do Documento",
      "type": "ARTIGO",
      "file": "caminho/para/o/arquivo",
      "researchArea": "Área de Pesquisa",
      "library": "ID da Biblioteca",
      "date": "2023-05-25T12:34:56.789Z",
      "language": "PT",
      "authors": ["Autor 1", "Autor 2"],
      "keywords": ["Palavra-chave 1", "Palavra-chave 2"],
      "createdAt": "2023-05-25T12:34:56.789Z",
      "updatedAt": "2023-05-25T12:34:56.789Z"
    }
  ],
  "totalSize": 1
}
~~~

### Obter um documento pelo ID
- **URL:** `/document/{id}`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "document": {
    "_id": "60d2ca57d6c9850015d49cb7",
    "title": "Título do Documento",
    "description": "Descrição do Documento",
    "type": "ARTIGO",
    "file": "caminho/para/o/arquivo",
    "researchArea": "Área de Pesquisa",
    "library": "ID da Biblioteca",
    "date": "2023-05-25T12:34:56.789Z",
    "language": "PT",
    "authors": ["Autor 1", "Autor 2"],
    "keywords": ["Palavra-chave 1", "Palavra-chave 2"],
    "createdAt": "2023-05-25T12:34:56.789Z",
    "updatedAt": "2023-05-25T12:34:56.789Z"
  }
}
~~~

### Atualizar um documento
- **URL:** `/document/{id}`
- **Método:** `PUT`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Corpo da Requisição:** Campos a serem atualizados no documento.
~~~json
{
  "title": "Título Atualizado",
  "description": "Descrição Atualizada",
  "type": "TESE",
  "file": "caminho/para/o/arquivo/atualizado",
  "researchArea": "Área de Pesquisa Atualizada",
  "library": "ID da Biblioteca Atualizada",
  "date": "2024-01-01T12:34:56.789Z",
  "language": "EN",
  "authors": ["Autor 1 Atualizado", "Autor 2 Atualizado"],
  "keywords": ["Palavra-chave 1 Atualizada", "Palavra-chave 2 Atualizada"]
}
~~~
- **Resposta:**
~~~json
{
  "message": "Documento atualizado com sucesso!",
  "document": {
    "_id": "60d2ca57d6c9850015d49cb7",
    "title": "Título Atualizado",
    "description": "Descrição Atualizada",
    "type": "TESE",
    "file": "caminho/para/o/arquivo/atualizado",
    "researchArea": "Área de Pesquisa Atualizada",
    "library": "ID da Biblioteca Atualizada",
    "date": "2024-01-01T12:34:56.789Z",
    "language": "EN",
    "authors": ["Autor 1 Atualizado", "Autor 2 Atualizado"],
    "keywords": ["Palavra-chave 1 Atualizada", "Palavra-chave 2 Atualizada"],
    "createdAt": "2023-05-25T12:34:56.789Z",
    "updatedAt": "2024-01-01T12:34:56.789Z"
  }
}
~~~

### Excluir um documento
- **URL:** `/document/{id}`
- **Método:** `DELETE`
- **Headers:** `Authorization: Bearer {token}` (Requer autenticação)
- **Resposta:**
~~~json
{
  "message": "Documento deletado com sucesso!"
}
~~~

### Obter idiomas dos documentos
- **URL:** `/documents/languages`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "languages": ["pt", "en", "es"]
}
~~~

### Obter tipos de documentos
- **URL:** `/documents/types`
- **Método:** `GET`
- **Resposta:** (Não requer autenticação)
~~~json
{
  "types": ["ARTIGO", "MONOGRAFIA", "TESE", "LIVRO", "DISSERTAÇÕES"]
}
~~~
