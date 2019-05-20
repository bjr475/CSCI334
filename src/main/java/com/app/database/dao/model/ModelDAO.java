package com.app.database.dao.model;

import com.app.database.Database;
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
    private static final String SQL_GET_SUPPLIER = "SELECT * FROM MODEL";
    private static final String SQL_GET_STORE_MODELS = "SELECT M.id            AS id,\n" +
            "       M.name          AS name,\n" +
            "       M.type          AS type,\n" +
            "       M.price         AS retail_price,\n" +
            "       M.subject       AS subject,\n" +
            "       M.description   AS description,\n" +
            "       M.stocked       AS stocked_on\n" +
            "FROM MODEL M\n" +
            "         LEFT JOIN MODEL_STORE MStore ON M.id = MStore.model_id\n" +
            "WHERE MStore.store_id = ?;";

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

    public ArrayList<CatalogueItemModel> getItems() {
        ArrayList<CatalogueItemModel> items = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_MODELS)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    CatalogueItemModel item = loadItemModel(result);
//                    try (PreparedStatement supplierStatement = connection.prepareStatement(SQL_GET_SUPPLIER)) {
//                        supplierStatement.setInt(1, );
//                    }
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to load catalogue item models", e);
        }
        return items;
    }

    public ArrayList<CatalogueItemModel> getItemsFromStore(int storeId) {
        ArrayList<CatalogueItemModel> items = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_STORE_MODELS)) {
                statement.setInt(1, storeId);
                ResultSet result = statement.executeQuery();
                while (result.next()) items.add(loadItemModel(result));
            }
        } catch (SQLException e) {
            logger.error("Failed to load catalogue item models", e);
        }
        return items;
    }
}
