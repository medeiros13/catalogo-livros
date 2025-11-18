# 🗄️ Database Design – Catálogo de Livros

## 📊 Modelo Relacional
### Tabela `genres`
- id (BIGINT, PK)
- name (VARCHAR)

### Tabela `books`
- id (BIGINT, PK)
- title
- author
- year
- genre_id (FK → genres.id)

## 🐦 Migrações Flyway
- V1: criação do schema
- V2: seed de gêneros
- V3: FK de gênero

## 🔒 Regras de Integridade
- FK obrigatória em `books.genre_id`
- Ano deve ser >= 1500