package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="BookSearchServlet", urlPatterns={"/books/search"})
public class BookSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try {
            req.setAttribute("q", q);
            req.setAttribute("books", (q == null || q.isBlank()) ? dao.findAll() : dao.search(q));
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}