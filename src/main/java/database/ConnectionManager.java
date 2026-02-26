package database;

import config.ConfigReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {
    private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
    private static ThreadLocal<Connection> connection = new ThreadLocal<>();

    public static Connection getConnection() throws SQLException {
        if (connection.get() == null || connection.get().isClosed()) {
            String url = ConfigReader.get("database.url");
            String username = ConfigReader.get("database.username");
            String password = ConfigReader.get("database.password");
            String driver = ConfigReader.get("database.driver");
            try {
                Class.forName(driver);
                connection.set(DriverManager.getConnection(url, username, password));
                logger.info("DB connection established");
            } catch (Exception e) {
                logger.error("DB connection failed", e);
                throw new SQLException(e);
            }
        }
        return connection.get();
    }

    public static void closeConnection() {
        try {
            if (connection.get() != null && !connection.get().isClosed()) {
                connection.get().close();
                logger.info("DB connection closed");
            }
        } catch (SQLException e) {
            logger.error("Error closing DB connection", e);
        } finally {
            connection.remove();
        }
    }
}
