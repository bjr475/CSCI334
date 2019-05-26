package com.app.database.dao.supplier;

import com.app.database.Database;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.supplier.SupplierCatalogueItemModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.app.main.model.supplier.SupplierItemModel;
import com.app.main.model.supplier.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAO {
    private static final Logger logger = LogManager.getLogger(SupplierDAO.class.getName());
    private static final String SQL_GET_SUPPLIERS = "SELECT *\n" +
            "FROM SUPPLIER;";
    private static final String SQL_GET_SUPPLIER_CONTACTS = "SELECT *\n" +
            "FROM SUPPLIER_CONTACT\n" +
            "WHERE supplier = ?";
    private static final String SQL_GET_SUPPLIER_ITEMS = "SELECT *\n" +
            "FROM (SELECT MS.price AS model_price,\n" +
            "             M.name   AS model_name,\n" +
            "             M.id     AS model_id\n" +
            "      FROM MODEL_SUPPLIER MS\n" +
            "               LEFT JOIN MODEL M ON MS.model_id = M.id\n" +
            "      WHERE MS.supplier_id = ?);";
    private static final String SQL_SAVE_SUPPLIER = "INSERT INTO SUPPLIER (address_address, address_suburb, address_state, address_postcode, name, delivery_details)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_SAVE_SUPPLIER_CONTACT = "INSERT INTO SUPPLIER_CONTACT (supplier, name, email, phone)\n" +
            "VALUES (?, ?, ?, ?);";
    private static final String SQL_SAVE_SUPPLIER_ITEMS = "INSERT INTO MODEL_SUPPLIER (model_id, supplier_id, price)\n" +
            "VALUES (?, ?, ?);";
    private final Database database;

    @Contract(pure = true)
    public SupplierDAO(Database database) {
        this.database = database;
    }

    private ArrayList<SupplierContactModel> getSupplierContacts(@NotNull SupplierModel supplier, Connection connection) throws SQLException {
        ArrayList<SupplierContactModel> contacts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_SUPPLIER_CONTACTS)) {
            statement.setInt(1, supplier.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SupplierContactModel model = new SupplierContactModel();
                model.setPhone(result.getString("phone"));
                model.setEmail(result.getString("email"));
                model.setPrimary(result.getBoolean("current"));
                model.setName(result.getString("name"));
                logger.info("Found supplier contact: {}", model);
                contacts.add(model);
            }
        }
        return contacts;
    }

    private ArrayList<SupplierItemModel> getSupplierItems(@NotNull SupplierModel supplier, Connection connection) throws SQLException {
        ArrayList<SupplierItemModel> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_SUPPLIER_ITEMS)) {
            statement.setInt(1, supplier.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SupplierItemModel model = new SupplierItemModel();
                model.setId(result.getInt("model_id"));
                model.setName(result.getString("model_name"));
                model.setPrice(result.getDouble("model_price"));
                logger.info("Found Supplier Item: {}", model);
                items.add(model);
            }
        }
        return items;
    }

    public ArrayList<SupplierModel> getSuppliers() {
        ArrayList<SupplierModel> suppliers = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_SUPPLIERS)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    SupplierModel model = new SupplierModel(result.getInt("id"));
                    model.setName(result.getString("name"));
                    model.setAddress(database.loadAddress(result));
                    model.setContactDetails(FXCollections.observableArrayList(getSupplierContacts(model, connection)));
                    model.setModels(FXCollections.observableArrayList(getSupplierItems(model, connection)));
                    logger.info("Found Supplier: {}", model);
                    suppliers.add(model);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load all the suppliers", e);
        }
        return suppliers;
    }

    public void saveSupplier(@NotNull SupplierModel supplier, ObservableList<SupplierCatalogueItemModel> items) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE_SUPPLIER)) {
                int count = database.saveAddress(statement, supplier.getAddress());
                statement.setString(count++, supplier.getName());
                statement.setString(count, "Delivery Details");
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    int supplierId = keys.getInt(1);
                    logger.info("Created new supplier with id: {}", supplierId);
                    for (SupplierContactModel detail : supplier.getContactDetails()) {
                        try (PreparedStatement scStatement = connection.prepareStatement(SQL_SAVE_SUPPLIER_CONTACT)) {
                            scStatement.setInt(1, supplierId);
                            scStatement.setString(2, detail.getName());
                            scStatement.setString(3, detail.getEmail());
                            scStatement.setString(4, detail.getPhone());
                            scStatement.executeUpdate();
                        } catch (SQLException e) {
                            logger.error("Failed to save new supplier contact", e);
                        }
                    }
                    for (SupplierCatalogueItemModel item : items) {
                        CatalogueItemModel catalogue = item.getCatalogueItem();
                        SupplierItemModel supplierItem = item.getSupplierItem();
                        int modelId = database.getModel().saveModel(catalogue);
                        try (PreparedStatement iStatement = connection.prepareStatement(SQL_SAVE_SUPPLIER_ITEMS)) {
                            iStatement.setInt(1, modelId);
                            iStatement.setInt(2, supplierId);
                            iStatement.setDouble(3, supplierItem.getPrice());
                            iStatement.executeUpdate();
                        } catch (SQLException e) {
                            logger.error("Failed to save new supplier item", e);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to save new supplier", e);
        }
    }
}
