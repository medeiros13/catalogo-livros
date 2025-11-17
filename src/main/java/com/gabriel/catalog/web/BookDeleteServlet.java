package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.BookDao;
import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/books/delete")
public class BookDeleteServlet extends HttpServlet {

    private ConnectionFactory cf() {
        return (ConnectionFactory) getServletContext().getAttribute("cf");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var ctx = req.getContextPath();
        var idStr = req.getParameter("id");

        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("flash_error", "ID de livro inválido.");
            resp.sendRedirect(ctx + "/books");
            return;
        }

        try {
            new BookDao(cf()).delete(id);
            req.getSession().setAttribute("flash_success", "Livro excluído com sucesso!");
        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", "Falha ao excluir: " + e.getMessage());
        }

        resp.sendRedirect(ctx + "/books");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/books");
    }
}