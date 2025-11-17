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

@WebServlet(name = "BookViewServlet", urlPatterns = {"/books/view"})
public class BookViewServlet extends HttpServlet {

    private ConnectionFactory cf() {
        return (ConnectionFactory) getServletContext().getAttribute("cf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var idParam = req.getParameter("id");
        long id;

        try {
            id = Long.parseLong(idParam);
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("flash_error", "ID de livro inválido.");
            resp.sendRedirect(req.getContextPath() + "/books");
            return;
        }

        BookDao dao = new BookDao(cf());
        try {
            Book book = dao.findById(id);
            if (book == null) {
                req.getSession().setAttribute("flash_error", "Livro não encontrado.");
                resp.sendRedirect(req.getContextPath() + "/books");
                return;
            }

            req.setAttribute("book", book);
            req.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}