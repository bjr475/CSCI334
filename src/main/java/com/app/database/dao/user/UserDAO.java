package com.app.database.dao.user;

import com.app.database.Database;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.AdminModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.EmployeeStoreModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class.getName());
    private static final String SQL_TRY_LOGIN = "SELECT id AS user_id, user_type\n" +
            "FROM (\n" +
            "         SELECT display_name, password, id, 0x01 AS user_type\n" +
            "         FROM EMPLOYEE E\n" +
            "         UNION\n" +
            "         SELECT display_name, password, id, 0x02 AS user_type\n" +
            "         FROM ADMIN A\n" +
            "     )\n" +
            "WHERE display_name = ?\n" +
            "  AND password = ?;";
    private static final String SQL_GET_ADMIN = "SELECT * FROM ADMIN WHERE ADMIN.id = ?";
    private static final String SQL_GET_EMPLOYEE = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE.id = ?";
    private static final String SQL_GET_EMPLOYEE_STORE = "SELECT name FROM STORE WHERE STORE.id = ?";

    private final Database database;

    @Contract(pure = true)
    public UserDAO(Database database) {
        this.database = database;
    }

    public User login(String username, String password) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_TRY_LOGIN)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    logger.info("Found user: ({}) >> {}", username, result);
                    int id = result.getInt("user_id");
                    int type = result.getInt("user_type");
                    if (type == 0x01) return new User(id, AUserModel.UserType.EMPLOYEE);
                    if (type == 0x02) return new User(id, AUserModel.UserType.ADMIN);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed trying to log user in: {}", username, e);
        }
        return null;
    }

    private void setUserCommon(AUserModel model, ResultSet result) throws SQLException {
        model.setFirstName(result.getString("first_name"));
        model.setLastName(result.getString("last_name"));
        model.setDisplayName(result.getString("display_name"));
        model.setPassword(result.getString("password"));
        model.setEmail(result.getString("email"));
    }

    public AdminModel getAdmin(int id) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ADMIN)) {
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    AdminModel model = new AdminModel(result.getInt("id"));
                    setUserCommon(model, result);
                    return model;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get admin account: {}", id, e);
        }
        return null;
    }

    @Contract("null -> null")
    private EmployeePermissions readEmployeePermissions(InputStream stream) {
        if (stream != null) {
            try (ObjectInputStream ois = new ObjectInputStream(stream)) {
                EmployeePermissions permissions = (EmployeePermissions) ois.readObject();
                logger.info("Employee permissions have been loaded: {}", permissions);
                return permissions;
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Failed to read employee permissions", e);
            }
        }
        return null;
    }

    private ByteArrayInputStream writeEmployeePermissions(EmployeePermissions permissions) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(stream)) {
                oos.writeObject(permissions);
                logger.info("Employee permissions have been written: {}", permissions);
                return new ByteArrayInputStream(stream.toByteArray());
            }
        }
    }

    @Nullable
    private EmployeeStoreModel getEmployeeStore(Connection connection, int storeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_EMPLOYEE_STORE)) {
            statement.setInt(1, storeId);
            ResultSet result = statement.executeQuery();
            if (result.next()) return new EmployeeStoreModel(storeId, result.getString("name"));
        }
        return null;
    }

    public EmployeeModel getEmployee(int id) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_EMPLOYEE)) {
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    EmployeeModel model = new EmployeeModel(result.getInt("id"));
                    setUserCommon(model, result);
                    model.setStore(getEmployeeStore(connection, result.getInt("store")));
                    model.setPermissions(readEmployeePermissions(result.getBinaryStream("permissions")));
                    logger.info("Loaded Employee Store: {}", model.getStore());
                    return model;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get admin account: {}", id, e);
        }
        return null;
    }

}
