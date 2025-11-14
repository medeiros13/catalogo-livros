package com.gabriel.catalog.web;

import com.gabriel.catalog.dao.ConnectionFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var ctx  = sce.getServletContext();
        var url  = ctx.getInitParameter("JDBC_URL");
        var user = ctx.getInitParameter("JDBC_USER");
        var pass = ctx.getInitParameter("JDBC_PASSWORD");

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(pass);

        try {
            var flyway = Flyway.configure()
                    .dataSource(ds)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            // 1x para corrigir histórico/checksum/migrations inválidas
            flyway.repair();

            // aplica V1, V2 ...
            flyway.migrate();

            // deixa a ConnectionFactory para os DAOs
            ctx.setAttribute("cf", new ConnectionFactory(url, user, pass));
        } catch (Exception e) {
            // loga o motivo real no catalina.log
            e.printStackTrace();
            throw new RuntimeException("Falha ao inicializar Flyway/H2: " + e.getMessage(), e);
        }
    }
}
