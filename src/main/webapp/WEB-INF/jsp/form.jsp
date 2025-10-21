<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle" value="${mode eq 'edit' ? 'Editar' : 'Novo'} • Catálogo"/>
</jsp:include>

<h1 class="mb-3"><c:choose><c:when test="${mode eq 'edit'}">Editar</c:when><c:otherwise>Novo</c:otherwise></c:choose> Livro</h1>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card p-4">
    <form class="row g-3" method="post" action="${pageContext.request.contextPath}${mode eq 'edit' ? '/books/edit' : '/books/new'}">
        <c:if test="${mode eq 'edit'}">
            <input type="hidden" name="id" value="${book.id}" />
        </c:if>

        <div class="col-md-8">
            <label class="form-label">Título</label>
            <input class="form-control" type="text" name="title" value="${book.title}" required />
        </div>

        <div class="col-md-4">
            <label class="form-label">Ano de publicação</label>
            <input class="form-control" type="number" name="yearPublished" value="${book.yearPublished}" required />
        </div>

        <div class="col-md-6">
            <label class="form-label">Autor</label>
            <input class="form-control" type="text" name="author" value="${book.author}" required />
        </div>

        <div class="col-md-6">
            <label class="form-label">Gênero</label>
            <input class="form-control" type="text" name="genre" value="${book.genre}" required />
        </div>

        <div class="col-12">
            <label class="form-label">Sinopse</label>
            <textarea class="form-control" name="synopsis" rows="5">${book.synopsis}</textarea>
        </div>

        <div class="col-12 d-flex gap-2">
            <button class="btn btn-brand text-white" type="submit">Salvar</button>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/books">Cancelar</a>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
