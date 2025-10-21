<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>${book.title}</title>
    <style>
        .meta { color:#444; }
    </style>
</head>
<body>
<h1>${book.title}</h1>
<p class="meta"><strong>Autor:</strong> ${book.author} · <strong>Ano:</strong> ${book.yearPublished} · <strong>Gênero:</strong> ${book.genre}</p>
<p>${book.synopsis}</p>
<p>
    <a href="${pageContext.request.contextPath}/books/edit?id=${book.id}">Editar</a> ·
    <a href="${pageContext.request.contextPath}/books">Voltar</a>
</p>
</body>
</html>