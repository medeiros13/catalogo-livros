package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import com.gabriel.catalog.dao.GenreDao;
import com.gabriel.catalog.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/books/new")
public class BookCreateServlet extends HttpServlet {

    private ConnectionFactory cf(HttpServletRequest req) {
        return (ConnectionFactory) getServletContext().getAttribute("cf");
    }

    private void loadGenres(HttpServletRequest req) {
        try {
            req.setAttribute("genres", new GenreDao(cf(req)).findAll());
        } catch (Exception ignored) {}
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mode", "new");
        loadGenres(req);
        req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Book b = new Book();
        b.setTitle(req.getParameter("title"));
        b.setAuthor(req.getParameter("author"));
        b.setGenre(req.getParameter("genre"));
        b.setSynopsis(req.getParameter("synopsis"));

        // validação do ano
        Integer year = null;
        try { year = Integer.valueOf(req.getParameter("yearPublished")); } catch (Exception ignored) {}
        if (year == null) {
            req.setAttribute("mode", "new");
            req.setAttribute("error", "Ano inválido.");
            req.setAttribute("book", b);
            loadGenres(req);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            return;
        }
        b.setYearPublished(year);

        try {
            new BookDao(cf(req)).insert(b);
            req.getSession().setAttribute("flash_success", "Livro salvo com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        } catch (Exception e) {
            req.setAttribute("mode", "new");
            req.setAttribute("error", "Erro ao salvar: " + e.getMessage());
            req.setAttribute("book", b);
            loadGenres(req);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        }
    }
}
