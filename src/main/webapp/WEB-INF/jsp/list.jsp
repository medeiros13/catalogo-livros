<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle" value="Livros • Catálogo"/>
</jsp:include>

<!-- Flash messages -->
<c:if test="${not empty sessionScope.flash_success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <c:out value="${sessionScope.flash_success}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"
                aria-label="Close"></button>
    </div>
    <c:remove var="flash_success" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.flash_error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <c:out value="${sessionScope.flash_error}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert"
                aria-label="Close"></button>
    </div>
    <c:remove var="flash_error" scope="session"/>
</c:if>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="mb-0">Livros</h1>
    <a class="btn btn-brand text-white"
       href="${pageContext.request.contextPath}/books/new">+ Novo</a>
</div>

<div class="card p-3 mb-3">
    <form class="row g-2 mb-0"
          action="${pageContext.request.contextPath}/books/search"
          method="get">
        <div class="col-md-10">
            <label class="visually-hidden" for="q">Buscar</label>
            <input type="search"
                   class="form-control"
                   id="q"
                   name="q"
                   placeholder="Buscar por título ou autor..."
                   value="<c:out value='${q}'/>"
                   aria-label="Buscar por título ou autor"/>
        </div>
        <div class="col-md-2 d-grid">
            <button type="submit" class="btn btn-outline-secondary">Buscar</button>
        </div>
    </form>
</div>

<c:if test="${not empty books}">
    <div class="card">
        <div class="table-responsive">
            <table class="table align-middle mb-0">
                <thead>
                <tr>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Ano</th>
                    <th>Gênero</th>
                    <th class="text-end">Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${books}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/books/view?id=${b.id}">
                                <c:out value="${b.title}"/>
                            </a>
                        </td>
                        <td><c:out value="${b.author}"/></td>
                        <td><c:out value="${b.yearPublished}"/></td>
                        <td><c:out value="${b.genre}"/></td>
                        <td class="text-end">
                            <div class="d-inline-flex gap-2">
                                <a class="btn btn-sm btn-outline-secondary"
                                   href="${pageContext.request.contextPath}/books/edit?id=${b.id}">
                                    Editar
                                </a>
                                <form action="${pageContext.request.contextPath}/books/delete"
                                      method="post"
                                      onsubmit="return confirm('Deseja realmente excluir este livro?');">
                                    <input type="hidden" name="id" value="${b.id}"/>
                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                        Excluir
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<c:if test="${empty books}">
    <div class="alert alert-warning mt-3">
        Nenhum livro encontrado.
    </div>
</c:if>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
