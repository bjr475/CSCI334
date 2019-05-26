package com.app.database.dao.model;

import com.app.database.Database;
import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import com.app.main.model.catalogue.CatalogueItemLocationModel;
import com.app.main.model.catalogue.CatalogueItemModel;
import com.app.main.model.catalogue.CatalogueItemSupplierModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            "FROM MODEL M";
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
    private static final String SQL_SAVE_MODEL = "INSERT INTO MODEL (name, type, price, subject, description)\n" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_GET_MODEL = SQL_GET_MODELS + "\nWHERE M.id = ?;";
    private static final String SQL_GET_ID_NAME_MODELS = "SELECT M.id   AS id,\n" +
            "       M.name AS name\n" +
            "FROM MODEL M";
    private static final String SQL_GET_ID_NAME_MODEL = SQL_GET_ID_NAME_MODELS + "\nWHERE M.id = ?";
    private final Database database;

    @Contract(pure = true)
    public ModelDAO(Database database) {
        this.database = database;
    }

    private CatalogueItemModel loadItemModel(@NotNull ResultSet result) throws SQLException {
        CatalogueItemModel model = new CatalogueItemModel(result.getInt("id"));
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

    private CatalogueItemModel loadItem(Connection connection, ResultSet result) throws SQLException {
        CatalogueItemModel item = loadItemModel(result);
        try (PreparedStatement supplierStatement = connection.prepareStatement(SQL_GET_SUPPLIER)) {
            supplierStatement.setInt(1, item.getItemId());
            ResultSet supplierResult = supplierStatement.executeQuery();
            while (supplierResult.next()) item.getSuppliers().add(loadItemModelSuppliers(supplierResult));
        } catch (SQLException e) {
            logger.error("Failed to load catalogue item supplier models", e);
        }
        try (PreparedStatement storesStatement = connection.prepareStatement(SQL_GET_STORES)) {
            storesStatement.setInt(1, item.getItemId());
            ResultSet storesResult = storesStatement.executeQuery();
            while (storesResult.next()) item.getStores().add(loadItemModelLocations(storesResult));
        }
        return item;
    }

    private ArrayList<CatalogueItemModel> loadItems(Connection connection, @NotNull ResultSet result) throws SQLException {
        ArrayList<CatalogueItemModel> items = new ArrayList<>();
        while (result.next()) {
            items.add(loadItem(connection, result));
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

    public CatalogueItemModel getModel(int id) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_MODEL)) {
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) return loadItem(connection, result);
            }
        } catch (SQLException e) {
            logger.error("Failed to load model: {}", id, e);
        }
        return null;
    }

    public CatalogueItemIdNameModel getIdNameModel(int model_id) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ID_NAME_MODEL)) {
                statement.setInt(1, model_id);
                ResultSet result = statement.executeQuery();
                return new CatalogueItemIdNameModel(
                        result.getInt("id"),
                        result.getString("name")
                );
            }
        } catch (SQLException e) {
            logger.error("Failed to load model with id: {}", model_id, e);
        }
        return null;
    }

    public ArrayList<CatalogueItemIdNameModel> getIdNameModels() {
        ArrayList<CatalogueItemIdNameModel> models = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ID_NAME_MODELS)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    models.add(new CatalogueItemIdNameModel(
                            result.getInt("id"),
                            result.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load ID Name Models", e);
        }
        return models;
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
                statement.setInt(7, model.getItemId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to update a model: {}", model.getItemId(), e);
        }
    }

    public int saveModel(@NotNull CatalogueItemModel model) {
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE_MODEL)) {
                statement.setString(1, model.getName());
                statement.setString(2, model.getType());
                statement.setDouble(3, model.getPrice());
                statement.setString(4, model.getSubject());
                statement.setString(5, model.getDescription());
                statement.executeUpdate();
                ResultSet key = statement.getGeneratedKeys();
                if (key.next()) return key.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to update a model: {}", model.getItemId(), e);
        }
        return -1;
    }

    public ArrayList<CatalogueItemModel> searchModels(String keywords) {
        ArrayList<CatalogueItemModel> items = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT id\n" +
                    "FROM MODEL_SEARCH\n" +
                    "WHERE name MATCH ?\n" +
                    "   OR type MATCH ?\n" +
                    "   OR subject MATCH ?\n" +
                    "   OR description MATCH ?")) {
                statement.setString(1, keywords);
                statement.setString(2, keywords);
                statement.setString(3, keywords);
                statement.setString(4, keywords);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    logger.info("Found Model with id: {}", id);
                    items.add(getModel(id));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load customers from search query: {}", keywords, e);
        }
        return items;
    }
}
