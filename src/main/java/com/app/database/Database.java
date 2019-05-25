package com.app.database;

import com.app.database.dao.customer.CustomerDAO;
import com.app.database.dao.model.ModelDAO;
import com.app.database.dao.store.StoreDAO;
import com.app.database.dao.supplier.SupplierDAO;
import com.app.database.dao.user.UserDAO;
import com.app.main.Util;
import com.app.main.model.AddressModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Database {
    public static final Database INSTANCE;
    private static final Logger logger = LogManager.getLogger(Database.class.getName());

    static {
        INSTANCE = new Database();
    }

    private final UserDAO user;
    private final ModelDAO model;
    private final StoreDAO store;
    private final CustomerDAO customer;
    private final SupplierDAO supplier;
    private String connectionPath;

    private Database() {
        Path path = Path.of(System.getProperty("user.dir"), "runtime", "database.db");
        connectionPath = String.format("jdbc:sqlite:%s", path.toString());
        if (!path.toFile().exists()) {
            try {
                assert path.toFile().createNewFile();
                initialiseDatabase();
                logger.debug("Database has been created");
            } catch (IOException e) {
                logger.error("Failed to initialise database", e);
                System.exit(-1);
            }
        }
        logger.debug("Database Connector: {}", connectionPath);

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            logger.fatal("Failed to find a valid JDBC driver for SQLite", e);
            System.exit(-1);
        }
        user = new UserDAO(this);
        model = new ModelDAO(this);
        store = new StoreDAO(this);
        customer = new CustomerDAO(this);
        supplier = new SupplierDAO(this);
    }

    @Nullable
    public static Date getDate(String value) {
        return getDate(value, null);
    }

    @Contract("null, _ -> param2")
    public static Date getDate(String value, @Nullable Date defaultValue) {
        if (value != null) {
            Date result = Util.parseDatabaseDate(value);
            if (result != null) return result;
        }
        return defaultValue;
    }

    private void initialiseDatabase() {
        URL location = getClass().getResource("/com/app/dbcreate.sql");
        logger.info("Loading Initialisation script: {}", location);
        try (BufferedReader br = new BufferedReader(new FileReader(location.getFile()))) {
            StringBuilder query = new StringBuilder();
            while (br.ready()) {
                query.append(String.format("%s\n", br.readLine()));
            }
            String dbCreate = query.toString();
            try (Connection connection = openConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(dbCreate);
                }
            } catch (SQLException e) {
                logger.error("Failed to create database", e);
                System.exit(-1);
            }
        } catch (Exception e) {
            logger.error("Failed to load database create script", e);
            System.exit(-1);
        }
    }

    public Connection openConnection() {
        try {
            return DriverManager.getConnection(connectionPath);
        } catch (SQLException e) {
            logger.fatal("Failed to connect to the SQLite database", e);
            System.exit(-1);
        }
        return null;
    }

    public int saveAddress(@NotNull PreparedStatement statement, @NotNull AddressModel address) throws SQLException {
        int counter = 1;
        logger.debug("Writing Address: {}", address);
        statement.setString(counter++, address.getAddress());
        statement.setString(counter++, address.getSuburb());
        statement.setString(counter++, address.getState());
        statement.setString(counter++, address.getPostcode());
        return counter;
    }

    @NotNull
    @Contract("_ -> new")
    public AddressModel loadAddress(@NotNull ResultSet result, String prefix) throws SQLException {
        return new AddressModel(
                result.getString(prefix + "address_address"),
                result.getString(prefix + "address_suburb"),
                result.getString(prefix + "address_state"),
                result.getString(prefix + "address_postcode")
        );
    }


    @NotNull
    @Contract("_ -> new")
    public AddressModel loadAddress(@NotNull ResultSet result) throws SQLException {
        return loadAddress(result, "");
    }

    public int saveStringArray(int initial, @NotNull PreparedStatement statement, ObservableList<String> array) throws SQLException {
        statement.setString(initial++, String.join(";", array));
        return initial;
    }

    public ObservableList<String> readStringArray(@NotNull ResultSet result, String name) throws SQLException {
        return FXCollections.observableArrayList(result.getString(name).split(";"));
    }

    public UserDAO getUser() {
        return user;
    }

    public ModelDAO getModel() {
        return model;
    }

    public StoreDAO getStore() {
        return store;
    }

    public CustomerDAO getCustomer() {
        return customer;
    }

    public SupplierDAO getSupplier() {
        return supplier;
    }
}
