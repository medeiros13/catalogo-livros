package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="BookViewServlet", urlPatterns={"/books/view"})
public class BookViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try { req.setAttribute("book", dao.findById(id));
            req.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}