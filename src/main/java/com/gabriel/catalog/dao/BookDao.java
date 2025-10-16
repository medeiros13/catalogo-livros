package com.gabriel.catalog.dao;

import com.gabriel.catalog.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final ConnectionFactory cf;

    public BookDao(ConnectionFactory cf) {
        this.cf = cf;
    }

    public List<Book> findAll() throws SQLException {
        String sql = "SELECT ID, TITLE, AUTHOR, YEAR_PUBLISHED, GENRE, SYNOPSIS FROM BOOKS ORDER BY TITLE";
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Book> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    public Book findById(long id) throws SQLException {
        String sql = "SELECT ID, TITLE, AUTHOR, YEAR_PUBLISHED, GENRE, SYNOPSIS FROM BOOKS WHERE ID=?";
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<Book> search(String q) throws SQLException {
        String like = "%" + q.toLowerCase() + "%";
        String sql = "SELECT ID, TITLE, AUTHOR, YEAR_PUBLISHED, GENRE, SYNOPSIS FROM BOOKS " +
                "WHERE LOWER(TITLE) LIKE ? OR LOWER(AUTHOR) LIKE ? ORDER BY TITLE";
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                List<Book> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    public void insert(Book b) throws SQLException {
        String sql = "INSERT INTO BOOKS (TITLE, AUTHOR, YEAR_PUBLISHED, GENRE, SYNOPSIS) VALUES (?,?,?,?,?)";
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getYearPublished());
            ps.setString(4, b.getGenre());
            ps.setString(5, b.getSynopsis());
            ps.executeUpdate();
        }
    }

    public void update(Book b) throws SQLException {
        String sql = "UPDATE BOOKS SET TITLE=?, AUTHOR=?, YEAR_PUBLISHED=?, GENRE=?, SYNOPSIS=? WHERE ID=?";
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getYearPublished());
            ps.setString(4, b.getGenre());
            ps.setString(5, b.getSynopsis());
            ps.setLong(6, b.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        try (Connection c = cf.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM BOOKS WHERE ID=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Book map(ResultSet rs) throws SQLException {
        return new Book(
                rs.getLong("ID"),
                rs.getString("TITLE"),
                rs.getString("AUTHOR"),
                rs.getInt("YEAR_PUBLISHED"),
                rs.getString("GENRE"),
                rs.getString("SYNOPSIS")
        );
    }
}