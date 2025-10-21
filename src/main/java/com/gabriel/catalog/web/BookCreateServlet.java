package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import com.gabriel.catalog.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/books/new")
public class BookCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mode", "create");
        req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var b = new Book();
        b.setTitle(req.getParameter("title"));
        b.setAuthor(req.getParameter("author"));
        b.setYearPublished(Integer.parseInt(req.getParameter("yearPublished")));
        b.setGenre(req.getParameter("genre"));
        b.setSynopsis(req.getParameter("synopsis"));
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try { dao.insert(b); resp.sendRedirect(req.getContextPath() + "/books"); }
        catch (Exception e) { throw new ServletException(e); }
    }
}