package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="BookListServlet", urlPatterns={"/books"})
public class BookListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cf = (ConnectionFactory) getServletContext().getAttribute("cf");
        var dao = new BookDao(cf);
        try {
            req.setAttribute("books", dao.findAll());
            req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}