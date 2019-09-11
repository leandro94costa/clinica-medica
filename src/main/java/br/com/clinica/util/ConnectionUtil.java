package br.com.clinica.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionUtil {

    private static ResourceBundle config;

    private static ConnectionUtil conexaoUtil;

    private ConnectionUtil() {

        config = ResourceBundle.getBundle("application");
    }

    public static ConnectionUtil getInstance() {

        if (conexaoUtil == null) {

            conexaoUtil = new ConnectionUtil();
        }

        return conexaoUtil;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(config.getString("driver"));

        return DriverManager.getConnection(config.getString("url"),
                config.getString("user"), config.getString("password"));
    }
}
