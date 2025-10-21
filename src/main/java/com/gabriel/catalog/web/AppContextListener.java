package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var ctx = sce.getServletContext();
        var cf = ConnectionFactory.fromContext(ctx);

        try (Connection c = cf.getConnection(); Statement st = c.createStatement()) {
            // Lê o init.sql do classpath: /WEB-INF/classes/db/init.sql
            try (InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("db/init.sql")) {
                if (is != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) sb.append(line).append('\n');
                        for (String sql : sb.toString().split(";\\s*\\n")) {
                            String s = sql.trim();
                            if (!s.isEmpty()) st.execute(s);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ctx.setAttribute("cf", cf);
    }
}
