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
import java.util.List;

@WebServlet(urlPatterns = "/books/search")
public class BookSearchServlet extends HttpServlet {

    private ConnectionFactory cf(HttpServletRequest req) {
        return (ConnectionFactory) getServletContext().getAttribute("cf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var q = req.getParameter("q");

        try {
            var dao = new BookDao(cf(req));
            var books = (q == null || q.isBlank())
                    ? dao.findAll()
                    : dao.searchByTitleOrAuthor(q);

            req.setAttribute("books", books);
            req.setAttribute("q", q == null ? "" : q);
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}