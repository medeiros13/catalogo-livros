<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle" value="Livros • Catálogo"/>
</jsp:include>

<!-- Flash messages -->
<c:if test="${not empty sessionScope.flash_success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${sessionScope.flash_success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flash_success" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.flash_error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${sessionScope.flash_error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flash_error" scope="session"/>
</c:if>

<div class="d-flex align-items-center justify-content-between mb-3">
    <h1 class="m-0">Livros</h1>
    <div class="d-flex gap-2">
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/">Início</a>
        <a class="btn btn-brand text-white" href="${pageContext.request.contextPath}/books/new">+ Novo</a>
    </div>
</div>

<form class="row g-2 mb-3" action="${pageContext.request.contextPath}/books/search" method="get">
    <div class="col-sm-9">
        <input class="form-control" type="text" name="q" value="${q}" placeholder="Buscar por título ou autor" />
    </div>
    <div class="col-sm-3 d-grid">
        <button class="btn btn-outline-primary" type="submit">Buscar</button>
    </div>
</form>

<div class="card p-0">
    <div class="table-responsive">
        <table class="table align-middle m-0">
            <thead>
            <tr>
                <th>Título</th>
                <th>Autor</th>
                <th class="text-center">Ano</th>
                <th>Gênero</th>
                <th class="text-end">Ações</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="b" items="${books}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/books/view?id=${b.id}">${b.title}</a></td>
                    <td>${b.author}</td>
                    <td class="text-center"><span class="badge badge-soft">${b.yearPublished}</span></td>
                    <td>${b.genre}</td>
                    <td class="text-end">
                        <div class="d-inline-flex gap-2">
                            <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/books/edit?id=${b.id}">Editar</a>
                            <form action="${pageContext.request.contextPath}/books/delete" method="post"
                                  onsubmit="return confirm('Excluir este item?');">
                                <input type="hidden" name="id" value="${b.id}" />
                                <button class="btn btn-sm btn-outline-danger" type="submit">Excluir</button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<c:if test="${empty books}">
    <div class="alert alert-warning mt-3">Nenhum livro encontrado.</div>
</c:if>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
