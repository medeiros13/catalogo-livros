package com.gabriel.catalog.dao;

import com.gabriel.catalog.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final ConnectionFactory cf;

    public BookDao(ConnectionFactory cf) { this.cf = cf; }

    public void insert(Book b) throws SQLException {
        String sql = """
            INSERT INTO BOOKS (TITLE, AUTHOR, YEAR_PUBLISHED, GENRE_ID, SYNOPSIS)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getYearPublished());
            ps.setLong(4, b.getGenreId());
            ps.setString(5, b.getSynopsis());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) b.setId(rs.getLong(1));
            }
        }
    }

    public void update(Book b) throws SQLException {
        String sql = """
            UPDATE BOOKS
               SET TITLE=?, AUTHOR=?, YEAR_PUBLISHED=?, GENRE_ID=?, SYNOPSIS=?
             WHERE ID=?
        """;
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setInt(3, b.getYearPublished());
            ps.setLong(4, b.getGenreId());
            ps.setString(5, b.getSynopsis());
            ps.setLong(6, b.getId());
            ps.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM BOOKS WHERE ID=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public Book findById(long id) throws SQLException {
        String sql = """
            SELECT b.ID,
                   b.TITLE,
                   b.AUTHOR,
                   b.YEAR_PUBLISHED,
                   b.GENRE_ID,
                   g.NAME AS GENRE_NAME,
                   b.SYNOPSIS
              FROM BOOKS b
              JOIN GENRES g ON g.ID = b.GENRE_ID
             WHERE b.ID = ?
        """;
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return map(rs);
            }
        }
    }

    public List<Book> findAll() throws SQLException {
        String sql = """
            SELECT b.ID,
                   b.TITLE,
                   b.AUTHOR,
                   b.YEAR_PUBLISHED,
                   b.GENRE_ID,
                   g.NAME AS GENRE_NAME,
                   b.SYNOPSIS
              FROM BOOKS b
              JOIN GENRES g ON g.ID = b.GENRE_ID
          ORDER BY b.TITLE
        """;
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Book> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    public List<Book> searchByTitleOrAuthor(String q) throws SQLException {
        String like = "%" + q + "%";
        String sql = """
            SELECT b.ID,
                   b.TITLE,
                   b.AUTHOR,
                   b.YEAR_PUBLISHED,
                   b.GENRE_ID,
                   g.NAME AS GENRE_NAME,
                   b.SYNOPSIS
              FROM BOOKS b
              JOIN GENRES g ON g.ID = b.GENRE_ID
             WHERE LOWER(b.TITLE)  LIKE LOWER(?)
                OR LOWER(b.AUTHOR) LIKE LOWER(?)
          ORDER BY b.TITLE
        """;
        try (Connection c = cf.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                List<Book> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    private Book map(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setId(rs.getLong("ID"));
        b.setTitle(rs.getString("TITLE"));
        b.setAuthor(rs.getString("AUTHOR"));
        b.setYearPublished(rs.getInt("YEAR_PUBLISHED"));
        b.setGenreId(rs.getLong("GENRE_ID"));
        b.setGenre(rs.getString("GENRE_NAME"));
        b.setSynopsis(rs.getString("SYNOPSIS"));
        return b;
    }
}
