package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import com.gabriel.catalog.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name="BookEditServlet", urlPatterns={"/books/edit"})
public class BookEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try { req.setAttribute("book", dao.findById(id)); req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        var b = new Book();
        b.setId(Long.parseLong(req.getParameter("id")));
        b.setTitle(req.getParameter("title"));
        b.setAuthor(req.getParameter("author"));
        b.setYearPublished(Integer.parseInt(req.getParameter("yearPublished")));
        b.setGenre(req.getParameter("genre"));
        b.setSynopsis(req.getParameter("synopsis"));
        var dao = new BookDao((ConnectionFactory) getServletContext().getAttribute("cf"));
        try { dao.update(b); resp.sendRedirect(req.getContextPath() + "/books"); }
        catch (Exception e) { throw new ServletException(e); }
    }
}