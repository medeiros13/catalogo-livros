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

@WebServlet(urlPatterns = "/books/edit")
public class BookEditServlet extends HttpServlet {

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
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            req.getSession().setAttribute("flash_error", "ID inválido para edição.");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        try {
            var dao = new BookDao(cf(req));
            var book = dao.findById(Long.parseLong(idStr));
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            req.getSession().setAttribute("flash_error", "ID inválido para edição.");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        Book b = new Book();
        b.setId(Long.valueOf(idStr));
        b.setTitle(req.getParameter("title"));
        b.setAuthor(req.getParameter("author"));
        b.setGenre(req.getParameter("genre"));
        b.setSynopsis(req.getParameter("synopsis"));

        Integer year = null;
        try { year = Integer.valueOf(req.getParameter("yearPublished")); } catch (Exception ignored) {}
        if (year == null) {
            req.setAttribute("mode", "edit");
            req.setAttribute("error", "Ano inválido.");
            req.setAttribute("book", b);
            loadGenres(req);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            return;
        }
        b.setYearPublished(year);

        try {
            new BookDao(cf(req)).update(b);
            req.getSession().setAttribute("flash_success", "Livro atualizado com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        } catch (Exception e) {
            req.setAttribute("mode", "edit");
            req.setAttribute("error", "Falha ao atualizar: " + e.getMessage());
            req.setAttribute("book", b);
            loadGenres(req);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        }
    }
}
