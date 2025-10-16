package com.gabriel.catalog.dao;

import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final String url;
    private final String user;
    private final String pass;

    public ConnectionFactory(String url, String user, String pass) {
        this.url = url; this.user = user; this.pass = pass;
    }

    public static ConnectionFactory fromContext(ServletContext ctx) {
        String url = ctx.getInitParameter("JDBC_URL");
        String user = ctx.getInitParameter("JDBC_USER");
        String pass = ctx.getInitParameter("JDBC_PASSWORD");
        return new ConnectionFactory(url, user, pass);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}