package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import com.gabriel.catalog.dao.GenreDao;
import com.gabriel.catalog.model.Book;
import com.gabriel.catalog.model.Genre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/books/new")
public class BookCreateServlet extends HttpServlet {

    private ConnectionFactory cf(HttpServletRequest req) {
        return (ConnectionFactory) req.getServletContext().getAttribute("cf");
    }

    private void loadGenres(HttpServletRequest req) throws Exception {
        var genreDao = new GenreDao(cf(req));
        var genres = genreDao.findAll();
        req.setAttribute("genres", genres);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("mode", "new");
        req.setAttribute("book", new Book());

        try {
            loadGenres(req);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String title = trim(req.getParameter("title"));
        String author = trim(req.getParameter("author"));
        String yearStr = trim(req.getParameter("yearPublished"));
        String genreStr = trim(req.getParameter("genreId"));
        String synopsis = trim(req.getParameter("synopsis"));

        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setSynopsis(synopsis != null ? synopsis : "");

        List<String> errors = new ArrayList<>();

        if (title == null || title.isBlank()) {
            errors.add("O título é obrigatório.");
        }

        // Ano (opcional, mas se preenchido precisa ser válido)
        if (yearStr != null && !yearStr.isBlank()) {
            try {
                int year = Integer.parseInt(yearStr);
                b.setYearPublished(year);
            } catch (NumberFormatException e) {
                errors.add("Ano inválido.");
            }
        }

        // Gênero (opcional ou obrigatório, dependendo da sua regra; aqui vou considerar obrigatório)
        if (genreStr == null || genreStr.isBlank()) {
            errors.add("Selecione um gênero.");
        } else {
            try {
                Long genreId = Long.valueOf(genreStr);
                b.setGenreId(genreId);
            } catch (NumberFormatException e) {
                errors.add("Gênero inválido.");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("mode", "new");
            req.setAttribute("book", b);
            req.setAttribute("error", String.join(" ", errors));
            try {
                loadGenres(req);
                req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return;
        }

        try {
            new BookDao(cf(req)).insert(b);
            req.getSession().setAttribute("flash_success", "Livro salvo com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            req.setAttribute("mode", "new");
            req.setAttribute("error", "Erro ao salvar: " + e.getMessage());
            req.setAttribute("book", b);
            try {
                loadGenres(req);
                req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        }
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }
}
