package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import com.gabriel.catalog.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/books/new")
public class BookCreateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mode", "new");
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

        String yearStr = req.getParameter("yearPublished");
        Integer year = null;
        try {
            year = Integer.valueOf(yearStr);
        } catch (Exception ignored) {
        }
        if (year == null) {
            // volta para o form com erro, sem salvar
            req.setAttribute("mode", "new");
            req.setAttribute("error", "Ano inválido.");
            req.setAttribute("book", b); // campos preenchidos voltam
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
            return;
        }
        b.setYearPublished(year);

        try {
            ConnectionFactory cf = (ConnectionFactory) getServletContext().getAttribute("cf");
            new BookDao(cf).insert(b);

            req.getSession().setAttribute("flash_success", "Livro salvo com sucesso!");
            resp.sendRedirect(req.getContextPath() + "/books");
        } catch (Exception e) {
            req.setAttribute("mode", "new");
            req.setAttribute("error", "Erro ao salvar: " + e.getMessage());
            req.setAttribute("book", b);
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        }
    }
}
