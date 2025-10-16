package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="BookDeleteServlet", urlPatterns={"/books/delete"})
public class BookDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try { dao.delete(id); resp.sendRedirect(req.getContextPath() + "/books"); }
        catch (Exception e) { throw new ServletException(e); }
    }
}