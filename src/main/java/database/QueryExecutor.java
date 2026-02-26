package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryExecutor {
    private static final Logger logger = LogManager.getLogger(QueryExecutor.class);

    public static List<Map<String, Object>> executeSelect(String query, Object... params) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            logger.info("Executed query: {}", query);
        } catch (SQLException e) {
            logger.error("Query execution failed", e);
            throw e;
        }
        return resultList;
    }

    public static boolean validateRecordExists(String query, Object... params) throws SQLException {
        return !executeSelect(query, params).isEmpty();
    }

    public static boolean validateColumnValue(String query, String column, Object expected, Object... params) throws SQLException {
        List<Map<String, Object>> results = executeSelect(query, params);
        for (Map<String, Object> row : results) {
            if (Objects.equals(row.get(column), expected)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareUIAndDBData(Object uiData, String query, String column, Object... params) throws SQLException {
        List<Map<String, Object>> results = executeSelect(query, params);
        for (Map<String, Object> row : results) {
            if (Objects.equals(row.get(column), uiData)) {
                return true;
            }
        }
        return false;
    }
}
