package com.app.database.dao.customer;

import com.app.database.Database;
import com.app.main.model.CustomerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {
    private static final Logger logger = LogManager.getLogger(CustomerDAO.class.getName());
    private static final String SQL_SAVE_CUSTOMER = "INSERT INTO CUSTOMER (address_address, address_suburb, address_state, address_postcode, email, first_name, last_name,\n" +
            "                      balance, member_status, subject, type)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final Database database;

    @Contract(pure = true)
    public CustomerDAO(Database database) {
        this.database = database;
    }

    public void saveCustomer(CustomerModel customer) {
        logger.info("Saving Customer: {}", customer);
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE_CUSTOMER)) {
                int counter = database.saveAddress(statement, customer.getAddress());
                statement.setString(counter++, customer.getEmail());
                statement.setString(counter++, customer.getFirstName());
                statement.setString(counter++, customer.getLastName());
                statement.setDouble(counter++, customer.getCreditLine());
                statement.setBoolean(counter++, customer.isClubMember());
                counter = database.saveStringArray(counter, statement, customer.getModelTypes());
                database.saveStringArray(counter, statement, customer.getSubjectAreas());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to save new customer: {}", customer, e);
        }
    }

    public ArrayList<CustomerModel> getCustomers() {
        ArrayList<CustomerModel> customers = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
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
                    customers.add(model);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get all customers", e);
        }
        return customers;
    }
}
