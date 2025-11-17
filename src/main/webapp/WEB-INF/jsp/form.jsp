<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/jsp/_header.jsp">
    <jsp:param name="pageTitle"
               value="${mode eq 'edit' ? 'Editar' : 'Novo'} • Catálogo"/>
</jsp:include>

<h1 class="mb-3">
    <c:choose>
        <c:when test="${mode eq 'edit'}">Editar</c:when>
        <c:otherwise>Novo</c:otherwise>
    </c:choose>
    Livro
</h1>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
        <c:out value="${error}"/>
    </div>
</c:if>

<div class="card p-4">
    <form class="row g-3" method="post"
          action="${pageContext.request.contextPath}${mode eq 'edit' ? '/books/edit' : '/books/new'}">

        <c:if test="${mode eq 'edit'}">
            <input type="hidden" name="id" value="${book.id}"/>
        </c:if>

        <div class="col-md-6">
            <label class="form-label" for="title">Título</label>
            <input type="text"
                   class="form-control"
                   id="title"
                   name="title"
                   value="<c:out value='${book.title}'/>"
                   required/>
        </div>

        <div class="col-md-6">
            <label class="form-label" for="author">Autor</label>
            <input type="text"
                   class="form-control"
                   id="author"
                   name="author"
                   value="<c:out value='${book.author}'/>"/>
        </div>

        <div class="col-md-3">
            <label class="form-label" for="yearPublished">Ano</label>
            <input
                    type="text"
                    class="form-control yearpicker"
                    id="yearPublished"
                    name="yearPublished"
                    value="<c:out value='${book.yearPublished}'/>"
                    autocomplete="off"
                    inputmode="numeric"
                    pattern="[0-9]{4}"
                    title="Informe um ano com 4 dígitos (por exemplo, 1993)"
                    aria-describedby="yearHelp"
                    data-initial="${book.yearPublished}"
            />
            <div id="yearHelp" class="form-text">
                Selecione o ano no calendário ou digite 4 dígitos.
            </div>
        </div>


        <div class="col-md-9">
            <label class="form-label" for="genreId">Gênero</label>
            <select class="form-select" id="genreId" name="genreId" required>
                <option value="">Selecione...</option>
                <c:forEach var="g" items="${genres}">
                    <option value="${g.id}"
                            <c:if test="${book.genreId == g.id}">selected</c:if>>
                        <c:out value="${g.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-12">
            <label class="form-label" for="synopsis">Sinopse</label>
            <textarea class="form-control"
                      id="synopsis"
                      name="synopsis"
                      rows="5"><c:out value="${book.synopsis}"/></textarea>
        </div>

        <div class="col-12 d-flex gap-2">
            <button class="btn btn-brand text-white" type="submit">Salvar</button>
            <a class="btn btn-outline-secondary"
               href="${pageContext.request.contextPath}/books">Cancelar</a>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
