package com.app.database.dao.store;

import com.app.database.Database;
import com.app.main.model.AddressModel;
import com.app.main.model.store.StoreModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreDAO {
    private static final Logger logger = LogManager.getLogger(StoreDAO.class.getName());
    private static final String SQL_GET_STORES = "SELECT *\n" +
            "FROM (SELECT S.id               AS store_id,\n" +
            "             S.name             AS store_name,\n" +
            "             S.address_address  AS store_address_address,\n" +
            "             S.address_suburb   AS store_address_suburb,\n" +
            "             S.address_state    AS store_address_state,\n" +
            "             S.address_postcode AS store_address_postcode,\n" +
            "             E.id               AS store_manager_id,\n" +
            "             E.first_name       AS store_manager_name\n" +
            "      FROM STORE S\n" +
            "               LEFT JOIN EMPLOYEE E ON S.manager = E.id);";
    private static final String SQL_UPDATE_STORE = "UPDATE STORE\n" +
            "SET address_address  = ?,\n" +
            "    address_suburb   = ?,\n" +
            "    address_state    = ?,\n" +
            "    address_postcode = ?,\n" +
            "    name             = ?,\n" +
            "    manager          = ?\n" +
            "WHERE id = ?;";
    private static final String SQL_SAVE_STORE = "INSERT INTO STORE (address_address, address_suburb, address_state, address_postcode, name, manager)\n" +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private Database database;

    @Contract(pure = true)
    public StoreDAO(Database database) {
        this.database = database;
    }

    @NotNull
    @Contract("_ -> new")
    private AddressModel loadAddress(@NotNull ResultSet result) throws SQLException {
        return new AddressModel(
                result.getString("store_address_address"),
                result.getString("store_address_suburb"),
                result.getString("store_address_state"),
                result.getString("store_address_postcode")
        );
    }

    private int saveAddress(@NotNull PreparedStatement statement, @NotNull AddressModel address) throws SQLException {
        int counter = 1;
        logger.debug(
                "Writing Address: {} {} {} {}",
                address.getAddress(), address.getSuburb(),
                address.getState(), address.getPostcode()
        );
        statement.setString(counter++, address.getAddress());
        statement.setString(counter++, address.getSuburb());
        statement.setString(counter++, address.getState());
        statement.setString(counter++, address.getPostcode());
        return counter;
    }

    public ArrayList<StoreModel> getStores() {
        ArrayList<StoreModel> stores = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_STORES)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    StoreModel store = new StoreModel(result.getInt("store_id"));
                    store.setName(result.getString("store_name"));
                    store.setAddress(loadAddress(result));
                    store.setManagerId(result.getInt("store_manager_id"));
                    store.setManagerName(result.getString("store_manager_name"));
                    stores.add(store);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load stores", e);
        }
        return stores;
    }

    public void updateStore(@NotNull StoreModel store) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STORE)) {
                int counter = saveAddress(statement, store.getAddress());
                statement.setString(counter++, store.getName());
                statement.setInt(counter++, store.getManagerId());
                statement.setInt(counter, store.getStoreId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to update the store with id: {}", store.getStoreId(), e);
        }
    }

    public void saveStore(@NotNull StoreModel store) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE_STORE)) {
                int counter = saveAddress(statement, store.getAddress());
                statement.setString(counter++, store.getName());
                statement.setInt(counter, store.getManagerId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to save store: {}", store.getName(), e);
        }
    }
}
