package com.app.database;

import com.app.database.dao.model.ModelDAO;
import com.app.database.dao.store.StoreDAO;
import com.app.database.dao.user.UserDAO;
import com.app.main.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Database {
    public static final Database INSTANCE;
    private static final Logger logger = LogManager.getLogger(Database.class.getName());

    static {
        INSTANCE = new Database();
    }

    private String connectionPath;
    private final UserDAO user;
    private final ModelDAO model;
    private final StoreDAO store;

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

    public UserDAO getUser() {
        return user;
    }

    public ModelDAO getModel() {
        return model;
    }

    public StoreDAO getStore() {
        return store;
    }
}
