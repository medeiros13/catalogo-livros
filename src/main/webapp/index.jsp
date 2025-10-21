<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Catálogo de Livros</title>
    <style>
        body { font-family: system-ui, Arial, sans-serif; margin: 24px; }
        a.button { display:inline-block; padding:8px 12px; border:1px solid #ccc; border-radius:8px; text-decoration:none; }
        .search { margin: 16px 0; }
    </style>
</head>
<body>
<h1>Catálogo de Livros</h1>
<p>
    <a class="button" href="${pageContext.request.contextPath}/books">Ver livros</a> ·
    <a class="button" href="${pageContext.request.contextPath}/books/new">Cadastrar novo</a>
</p>
<form class="search" action="${pageContext.request.contextPath}/books/search" method="get">
    <label>Buscar por título ou autor: <input type="text" name="q" /></label>
    <button type="submit">Buscar</button>
</form>
</body>
</html>