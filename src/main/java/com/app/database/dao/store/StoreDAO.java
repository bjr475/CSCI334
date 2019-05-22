package com.app.database.dao.store;

import com.app.database.Database;
import com.app.main.model.store.StoreModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreDAO {
    private static final Logger logger = LogManager.getLogger(StoreDAO.class.getName());
    private static final String SQL_GET_STORES = "SELECT *\n" +
            "FROM (SELECT S.id         AS store_id,\n" +
            "             S.name       AS store_name,\n" +
            "             S.address    AS store_address,\n" +
            "             E.id AS store_manager_id,\n" +
            "             E.first_name AS store_manager_name\n" +
            "      FROM STORE S\n" +
            "               LEFT JOIN EMPLOYEE E ON S.manager = E.id);";
    private static final String SQL_UPDATE_STORE = "UPDATE STORE\n" +
            "SET name    = ?,\n" +
            "    address = ?,\n" +
            "    manager = ?\n" +
            "WHERE id = ?;";
    private Database database;

    public StoreDAO(Database database) {
        this.database = database;
    }

    public ArrayList<StoreModel> getStores() {
        ArrayList<StoreModel> stores = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_STORES)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    StoreModel store = new StoreModel(result.getInt("store_id"));
                    store.setName(result.getString("store_name"));
                    store.setAddress(result.getString("store_address"));
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

    public void saveStore(StoreModel store) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STORE)) {
                statement.setString(1, store.getName());
            }
        } catch (SQLException e) {
            logger.error("Failed to update the store with id: {}", store.getStoreId(), e);
        }
    }
}
