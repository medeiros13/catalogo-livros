# 📘 Technical Overview – Catálogo de Livros

## 🧱 Arquitetura Geral
A aplicação segue o padrão MVC clássico:

- **Model**: `Book`, `Genre`
- **DAO Layer**: JDBC + H2 (via ConnectionFactory)
- **Controller**: Servlets
- **View**: JSP + JSTL
- **Database**: H2 + Flyway

## 🔄 Fluxo HTTP
1. Usuário envia requisição → Servlet
2. Servlet valida input e chama DAO
3. DAO acessa H2 usando PreparedStatement
4. Servlet envia dados para JSP
5. JSP renderiza resposta HTML

## 📂 Estrutura das pastas
```
src/main/java/com/gabriel/catalog/
 ├── model
 ├── dao
 └── servlet
src/main/webapp/
 ├── WEB-INF/views
 └── assets
```