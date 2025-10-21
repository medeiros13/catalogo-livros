<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle" value="Início • Catálogo"/>
</jsp:include>

<div class="card p-4">
    <h1 class="mb-3">Catálogo de Livros</h1>
    <p class="text-muted">Cadastre, pesquise e gerencie seus livros.</p>
    <div class="d-flex gap-2">
        <a class="btn btn-brand text-white" href="${pageContext.request.contextPath}/books">Ver livros</a>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/books/new">Cadastrar novo</a>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
