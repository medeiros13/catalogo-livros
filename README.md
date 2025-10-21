# Catálogo de Livros – JSP/Servlets/JDBC (H2)

> Feito para funcionar no **Tomcat 11.0+** (Jakarta EE 11 / Servlets 6.1), com **JDK 21+**. Você tem JDK 25 – ok.

## 1) Criar o projeto no IntelliJ
1. **New Project → Maven** (sem archetype). GroupId `com.gabriel.catalog` ArtifactId `catalogo-livros` Packaging `war`.
2. **Diretório do projeto**: `D:\Projects\Personal\catalogo-livros` (ou `D:\Projects\Personal\<nome do projeto>` conforme você preferir).
3. Cole o `pom.xml` deste repositório.
4. `File → Project Structure → Project SDK`: selecione seu JDK 25 (ou 21+).

## 2) Configurar Tomcat 11 no IntelliJ (Ultimate)
1. Baixe o **Tomcat 11.0.x** (zip) e descompacte em `D:\Programs\apache-tomcat-11.0.13`. Descompacte em `D:\Programs\apache-tomcat-11.0.13`.
2. `Run → Edit Configurations…` → `+` → **Tomcat Server (Local)**.
3. Em **Application Server**, selecione a pasta do Tomcat.
4. Em **Deployment**, adicione o artefato: `catalogo-livros:war exploded`. Context path: `/catalogo-livros` (ou `/`).
5. Em **Server → VM options** (opcional): `-Dfile.encoding=UTF-8`.

## 3) Estrutura e codificação
- Crie os pacotes e arquivos conforme a árvore acima.
- Certifique-se de que os JSPs estão em `src/main/webapp/`.

## 4) Banco de dados
- Usamos **H2 em arquivo** agora em `D:/Projects/Personal/catalogo-livros/catalogo-livros/data/catalogo_livros_db` (crie a pasta `D:\Projects\Personal\catalogo-livros\catalogo-livros\data` se não existir).
- O **listener** executa `src/main/resources/db/init.sql` na primeira subida.

## 5) Executar
- `mvn clean package` (ou direto pelo IntelliJ) → Run a configuração do Tomcat.
- Acesse: `http://localhost:8080/catalogo-livros/`

## 6) Funcionalidades cobrindo os requisitos
- **Interface web**: JSPs `list.jsp`, `form.jsp`, `view.jsp`.
- **Cadastro**: `/books/new` (GET/POST).
- **Listagem**: `/books`.
- **Detalhes**: `/books/view?id=...`.
- **Editar**: `/books/edit?id=...` (GET/POST).
- **Excluir**: `/books/delete` (POST).
- **Busca simples**: `/books/search?q=...` (case-insensitive por título/autor).
- **Persistência**: JDBC/H2 (arquivo local).

## 7) Dicas de quem vem do .NET
- Pense nos **Servlets** como seus **Controllers** minimalistas.
- Os **JSPs** são Views server-side (com JSTL como helpers de laço/condição).
- O **DAO** aqui faz o papel de repositório ADO.NET (PreparedStatement = SqlCommand; ResultSet = SqlDataReader).

## 8) Próximos passos (opcionais)
- Validações server-side nos formulários.
- Paginação na listagem.
- Trocar H2 por MySQL/PostgreSQL mantendo o `BookDao`.
- Adicionar testes de integração (Testcontainers) e CSS melhor.