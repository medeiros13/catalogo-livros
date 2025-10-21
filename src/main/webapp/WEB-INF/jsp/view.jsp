<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle" value="${book.title} • Catálogo"/>
</jsp:include>

<div class="card p-4">
    <h1 class="mb-1">${book.title}</h1>
    <p class="text-muted mb-3">
        <strong>Autor:</strong> ${book.author} ·
        <strong>Ano:</strong> ${book.yearPublished} ·
        <strong>Gênero:</strong> ${book.genre}
    </p>
    <p style="white-space: pre-line;">${book.synopsis}</p>

    <div class="mt-3 d-flex gap-2">
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/books/edit?id=${book.id}">Editar</a>
        <a class="btn btn-brand text-white" href="${pageContext.request.contextPath}/books">Voltar</a>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
