package tcmapp.database;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.database.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestCaseDAO {

    public void createTestCase(String name, String description) {
        String sql = "INSERT INTO test_cases (name, description) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating test case: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return ComponentAccessor.getComponent(DatabaseConfiguration.class).getDatasource().getConnection();
    }
}
