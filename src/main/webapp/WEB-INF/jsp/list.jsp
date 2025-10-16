<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="https://jakarta.ee/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Livros</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background: #f8f8f8; }
        .actions a, .actions form { display: inline-block; margin-right: 6px; }
        .top { display:flex; gap:12px; align-items:center; justify-content:space-between; margin: 16px 0; }
    </style>
</head>
<body>
<div class="top">
    <h1>Livros</h1>
    <div>
        <a href="${pageContext.request.contextPath}/books/new">+ Novo</a>
        <a href="${pageContext.request.contextPath}/">Início</a>
    </div>
</div>

<form action="${pageContext.request.contextPath}/books/search" method="get">
    <input type="text" name="q" value="${q}" placeholder="Buscar por título ou autor" />
    <button type="submit">Buscar</button>
</form>

<table>
    <thead>
    <tr>
        <th>Título</th>
        <th>Autor</th>
        <th>Ano</th>
        <th>Gênero</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${books}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/books/view?id=${b.id}">${b.title}</a></td>
            <td>${b.author}</td>
            <td>${b.yearPublished}</td>
            <td>${b.genre}</td>
            <td class="actions">
                <a href="${pageContext.request.contextPath}/books/edit?id=${b.id}">Editar</a>
                <form action="${pageContext.request.contextPath}/books/delete" method="post" onsubmit="return confirm('Excluir este item?');">
                    <input type="hidden" name="id" value="${b.id}" />
                    <button type="submit">Excluir</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty books}">
    <p>Nenhum livro encontrado.</p>
</c:if>
</body>
</html>