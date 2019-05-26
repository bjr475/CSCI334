package com.app.database.dao.customer;

import com.app.database.Database;
import com.app.main.model.CustomerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {
    private static final Logger logger = LogManager.getLogger(CustomerDAO.class.getName());
    private static final String SQL_INSERT_CUSTOMER = "INSERT INTO CUSTOMER (address_address, address_suburb, address_state, address_postcode, email, first_name, last_name,\n" +
            "                      balance, member_status, subject, type)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_CUSTOMER = "UPDATE CUSTOMER\n" +
            "SET address_address  = ?,\n" +
            "    address_suburb   = ?,\n" +
            "    address_state    = ?,\n" +
            "    address_postcode = ?,\n" +
            "    email            = ?,\n" +
            "    first_name       = ?,\n" +
            "    last_name        = ?,\n" +
            "    balance          = ?,\n" +
            "    member_status    = ?,\n" +
            "    subject          = ?,\n" +
            "    type             = ?\n" +
            "WHERE id = ?;";
    private static final String SQL_GET_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String SQL_GET_CUSTOMER = SQL_GET_CUSTOMERS + "\nWHERE id = ?";
    private final Database database;

    @Contract(pure = true)
    public CustomerDAO(Database database) {
        this.database = database;
    }

    private CustomerModel loadCustomer(@NotNull ResultSet result) throws SQLException {
        CustomerModel model = new CustomerModel();
        model.setId(result.getInt("id"));
        model.setFirstName(result.getString("first_name"));
        model.setLastName(result.getString("last_name"));
        model.setEmail(result.getString("email"));
        model.setAddress(database.loadAddress(result));
        model.setCreditLine(result.getDouble("balance"));
        model.setClubMember(result.getBoolean("member_status"));
        model.setSubjectAreas(database.readStringArray(result, "subject"));
        model.setModelTypes(database.readStringArray(result, "type"));
        return model;
    }

    public ArrayList<CustomerModel> getCustomers() {
        ArrayList<CustomerModel> customers = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_CUSTOMERS)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) customers.add(loadCustomer(result));
            }
        } catch (SQLException e) {
            logger.error("Failed to get all customers", e);
        }
        return customers;
    }

    public CustomerModel getCustomer(int customer) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_CUSTOMER)) {
                statement.setInt(1, customer);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    return loadCustomer(result);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load customer with id: {}", customer);
        }
        return null;
    }

    private int setCustomerStatement(PreparedStatement statement, @NotNull CustomerModel customer) throws SQLException {
        int counter = database.saveAddress(statement, customer.getAddress());
        statement.setString(counter++, customer.getEmail());
        statement.setString(counter++, customer.getFirstName());
        statement.setString(counter++, customer.getLastName());
        statement.setDouble(counter++, customer.getCreditLine());
        statement.setBoolean(counter++, customer.isClubMember());
        counter = database.saveStringArray(counter, statement, customer.getSubjectAreas());
        return database.saveStringArray(counter, statement, customer.getModelTypes());
    }

    public int insertCustomer(CustomerModel customer) {
        logger.info("Inserting Customer: {}", customer);
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CUSTOMER)) {
                setCustomerStatement(statement, customer);
                statement.executeUpdate();
                ResultSet genKey = statement.getGeneratedKeys();
                if (genKey.next()) return genKey.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to insert new customer: {}", customer, e);
        }
        return -1;
    }

    public void updateCustomer(CustomerModel customer) {
        logger.info("Updating Customer: {}", customer);
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CUSTOMER)) {
                int counter = setCustomerStatement(statement, customer);
                statement.setInt(counter, customer.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to update customer: {}", customer, e);
        }
    }
}
