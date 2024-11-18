package org.mf.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

public class DBConnectionPool {

    private static final HikariDataSource dataSource;

    private static final int MAX_POOL_SIZE = 20;
    private static final int MIN_IDLE = 5;
    private static final int IDLE_TIMEOUT = 60000;
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int MAX_LIFETIME = 1800000;


    static {
        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setMaximumPoolSize(MAX_POOL_SIZE);      // maximum of 20 connections
        config.setMinimumIdle(MIN_IDLE);           // minimum of 5 connections available
        config.setIdleTimeout(IDLE_TIMEOUT);       // if open connection is not used for 1 min, close connection
        config.setConnectionTimeout(CONNECTION_TIMEOUT); // wait 10 sec max for a connection
        config.setMaxLifetime(MAX_LIFETIME);     // 30 min lifespan for each connection

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }



}
