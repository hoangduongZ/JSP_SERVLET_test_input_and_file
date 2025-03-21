package com.duonghoang.test_input.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static DBUtil instance;
    private Connection connection;

    private DBUtil() {
        Properties properties = new Properties();
        try {
            properties.load(DBUtil.class.getResourceAsStream("/dbConfig.properties"));
            String url = properties.getProperty("url");
            String userName = properties.getProperty("user");
            String password = properties.getProperty("password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException | IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBUtil getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBUtil();
        }
        return instance;
    }

    public  Connection getConnection() {
        return connection;
    }


}
