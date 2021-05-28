package com.web.connection;

import com.web.exception.ConnectionException;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolFactory {
    private static Driver driver;
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3307/statistics_db?useSSL=false&serverTimezone=Europe/Minsk";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";
    private static final int POOL_SIZE = 6;

    ConnectionPoolFactory() {

    }

    ConnectionPool createPool() throws ConnectionException {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            List<ProxyConnection> connections = createConnections();
            return new ConnectionPool(POOL_SIZE, connections);
        } catch (SQLException exception) {
            throw new ConnectionException(exception.getMessage(), exception);
        }
    }

    private List createConnections() throws SQLException {
        List<ProxyConnection> connections = new ArrayList<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connections.add(proxyConnection);
        }
        return connections;
    }

    static void deregisterDriver() throws SQLException {
        DriverManager.deregisterDriver(driver);
    }
}
