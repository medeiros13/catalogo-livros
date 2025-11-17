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

@WebServlet(urlPatterns = "/books/edit")
public class BookEditServlet extends HttpServlet {

    private ConnectionFactory cf(HttpServletRequest req) {
        return (ConnectionFactory) req.getServletContext().getAttribute("cf");
    }

    private void loadGenres(HttpServletRequest req) throws Exception {
        GenreDao genreDao = new GenreDao(cf(req));
        List<Genre> genres = genreDao.findAll();
        req.setAttribute("genres", genres);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        Long id;

        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("flash_error", "ID de livro inválido.");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        BookDao dao = new BookDao(cf(req));
        try {
            Book book = dao.findById(id);
            if (book == null) {
                req.getSession().setAttribute("flash_error", "Livro não encontrado.");
                resp.sendRedirect(req.getContextPath() + "/books");
                return;
            }

            req.setAttribute("mode", "edit");
            req.setAttribute("book", book);
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

        var idStr = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("flash_error", "ID de livro inválido.");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        var title = trim(req.getParameter("title"));
        var author = trim(req.getParameter("author"));
        var yearStr = trim(req.getParameter("yearPublished"));
        var genreStr = trim(req.getParameter("genreId"));
        var synopsis = trim(req.getParameter("synopsis"));

        Book b = new Book();
        b.setId(id);
        b.setTitle(title);
        b.setAuthor(author);
        b.setSynopsis(synopsis != null ? synopsis : "");

        List<String> errors = new ArrayList<>();

        if (title == null || title.isBlank()) {
            errors.add("O título é obrigatório.");
        }

        if (yearStr != null && !yearStr.isBlank()) {
            try {
                int year = Integer.parseInt(yearStr);
                b.setYearPublished(year);
            } catch (NumberFormatException e) {
                errors.add("Ano inválido.");
            }
        }

        if (genreStr == null || genreStr.isBlank()) {
            errors.add("Selecione um gênero.");
        } else {
            try {
                var genreId = Long.valueOf(genreStr);
                b.setGenreId(genreId);
            } catch (NumberFormatException e) {
                errors.add("Gênero inválido.");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("mode", "edit");
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
            new BookDao(cf(req)).update(b);
            req.getSession().setAttribute("flash_success", "Livro atualizado com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            req.setAttribute("mode", "edit");
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
