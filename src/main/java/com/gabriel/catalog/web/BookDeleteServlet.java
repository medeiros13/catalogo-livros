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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        String ctx = req.getContextPath();

        if (idStr == null || idStr.isBlank()) {
            req.getSession().setAttribute("flash_error", "ID inválido para exclusão.");
            resp.sendRedirect(ctx + "/books");
            return;
        }

        try {
            long id = Long.parseLong(idStr);
            ConnectionFactory cf = (ConnectionFactory) getServletContext().getAttribute("cf");
            new BookDao(cf).delete(id);

            req.getSession().setAttribute("flash_success", "Livro excluído com sucesso!");
            resp.sendRedirect(ctx + "/books");
        } catch (Exception e) {
            req.getSession().setAttribute("flash_error", "Falha ao excluir: " + e.getMessage());
            resp.sendRedirect(ctx + "/books");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/books");
    }
}
