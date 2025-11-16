package com.gabriel.catalog.dao;

import com.gabriel.catalog.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDao {
    private final ConnectionFactory cf;
    public GenreDao(ConnectionFactory cf) { this.cf = cf; }

    public List<Genre> findAll() throws Exception {
        String sql = "SELECT ID, NAME FROM GENRES ORDER BY NAME";
        try (var con = cf.getConnection();
             var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            List<Genre> list = new ArrayList<>();
            while (rs.next()) {
                Genre g = new Genre();
                g.setId(rs.getLong("ID"));
                g.setName(rs.getString("NAME"));
                list.add(g);
            }
            return list;
        }
    }
}
