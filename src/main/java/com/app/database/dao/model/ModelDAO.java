package com.app.database.dao.model;

import com.app.database.Database;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class ModelDAO {
    private static final Logger logger = LogManager.getLogger(ModelDAO.class.getName());
    private static final String SQL_GET_MODELS = "SELECT M.id            AS id,\n" +
            "       M.name          AS name,\n" +
            "       M.type          AS type,\n" +
            "       M.price         AS retail_price,\n" +
            "       M.subject       AS subject,\n" +
            "       M.description   AS description,\n" +
            "       M.stocked       AS stocked_on\n" +
            "FROM MODEL M;";
    private static final String SQL_GET_SUPPLIER = "SELECT MS.price AS price,\n" +
            "       S.name   AS name\n" +
            "FROM MODEL\n" +
            "         LEFT JOIN MODEL_SUPPLIER MS ON MODEL.id = MS.model_id\n" +
            "         LEFT JOIN SUPPLIER S ON MS.supplier_id = S.id\n" +
            "WHERE MODEL.id = ?;";
    private static final String SQL_GET_STORES = "SELECT MS.quantity AS count,\n" +
            "       S.name      AS name\n" +
            "FROM MODEL\n" +
            "         LEFT JOIN MODEL_STORE MS ON MODEL.id = MS.model_id\n" +
            "         LEFT JOIN STORE S ON MS.store_id = S.id\n" +
            "WHERE MODEL.id = ?;";
    private static final String SQL_UPDATE_MODEL = "UPDATE MODEL\n" +
            "SET name        = ?,\n" +
            "    description = ?,\n" +
            "    subject     = ?,\n" +
            "    type        = ?,\n" +
            "    stocked     = ?,\n" +
            "    price       = ?\n" +
            "WHERE id = ?;";
    private static final String SQL_SAVE_MODEL = "INSERT INTO MODEL (id, name, type, price, subject, description)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private final Database database;

    @Contract(pure = true)
    public ModelDAO(Database database) {
        this.database = database;
    }

    private CatalogueItemModel loadItemModel(@NotNull ResultSet result) throws SQLException {
        CatalogueItemModel model = new CatalogueItemModel(result.getString("id"));
        model.setName(result.getString("name"));
        model.setType(result.getString("type"));
        model.setSubject(result.getString("subject"));
        model.setDescription(result.getString("description"));
        model.setPrice(result.getDouble("retail_price"));
        return model;
    }

    private CatalogueItemSupplierModel loadItemModelSuppliers(@NotNull ResultSet result) throws SQLException {
        CatalogueItemSupplierModel model = new CatalogueItemSupplierModel();
        model.setName(result.getString("name"));
        model.setPrice(result.getDouble("price"));
        return model;
    }

    private CatalogueItemLocationModel loadItemModelLocations(@NotNull ResultSet result) throws SQLException {
        CatalogueItemLocationModel model = new CatalogueItemLocationModel();
        model.setCount(result.getInt("count"));
        model.setStore(result.getString("name"));
        return model;
    }

    private ArrayList<CatalogueItemModel> loadItems(Connection connection, @NotNull ResultSet result) throws SQLException {
        ArrayList<CatalogueItemModel> items = new ArrayList<>();
        while (result.next()) {
            CatalogueItemModel item = loadItemModel(result);
            try (PreparedStatement supplierStatement = connection.prepareStatement(SQL_GET_SUPPLIER)) {
                supplierStatement.setString(1, item.getItemId());
                ResultSet supplierResult = supplierStatement.executeQuery();
                while (supplierResult.next()) item.getSuppliers().add(loadItemModelSuppliers(supplierResult));
            } catch (SQLException e) {
                logger.error("Failed to load catalogue item supplier models", e);
            }
            try (PreparedStatement storesStatement = connection.prepareStatement(SQL_GET_STORES)) {
                storesStatement.setString(1, item.getItemId());
                ResultSet storesResult = storesStatement.executeQuery();
                while (storesResult.next()) item.getStores().add(loadItemModelLocations(storesResult));
            }
            items.add(item);
        }
        return items;
    }

    public ArrayList<CatalogueItemModel> getModels() {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_MODELS)) {
                ResultSet result = statement.executeQuery();
                return loadItems(connection, result);
            }
        } catch (SQLException e) {
            logger.error("Failed to load catalogue item models", e);
        }
        return new ArrayList<>();
    }

    public void updateModel(@NotNull CatalogueItemModel model) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MODEL)) {
                statement.setString(1, model.getName());
                statement.setString(2, model.getDescription());
                statement.setString(3, model.getSubject());
                statement.setString(4, model.getType());
                statement.setTimestamp(5, new Timestamp(model.getStockedOn().getTime()));
                statement.setDouble(6, model.getPrice());
                statement.setString(7, model.getItemId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to update a model: {}", model.getItemId(), e);
        }
    }

    public boolean saveModel(@NotNull CatalogueItemModel model) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE_MODEL)) {
                statement.setString(1, model.getItemId());
                statement.setString(2, model.getName());
                statement.setString(3, model.getType());
                statement.setDouble(4, model.getPrice());
                statement.setString(5, model.getSubject());
                statement.setString(6, model.getDescription());
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to update a model: {}", model.getItemId(), e);
        }
        return false;
    }
}
