<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title><c:choose><c:when test="${mode eq 'edit'}">Editar</c:when><c:otherwise>Novo</c:otherwise></c:choose> Livro</title>
    <style>
        form { display:grid; gap:8px; max-width: 560px; }
        label { display:grid; gap:4px; }
        textarea { min-height: 120px; }
    </style>
</head>
<body>
<h1><c:choose><c:when test="${mode eq 'edit'}">Editar</c:when><c:otherwise>Novo</c:otherwise></c:choose> Livro</h1>
<form method="post" action="${pageContext.request.contextPath}/books/new">
    <c:if test="${mode eq 'edit'}">
        <input type="hidden" name="id" value="${book.id}" />
    </c:if>

    <label>Título
        <input type="text" name="title" value="${book.title}" required />
    </label>

    <label>Autor
        <input type="text" name="author" value="${book.author}" required />
    </label>

    <label>Ano de publicação
        <input type="number" name="yearPublished" value="${book.yearPublished}" required />
    </label>

    <label>Gênero
        <input type="text" name="genre" value="${book.genre}" required />
    </label>

    <label>Sinopse
        <textarea name="synopsis">${book.synopsis}</textarea>
    </label>

    <button type="submit">Salvar</button>
    <a href="${pageContext.request.contextPath}/books">Cancelar</a>
</form>
</body>
</html>