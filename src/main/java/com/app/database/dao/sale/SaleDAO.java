package com.app.database.dao.sale;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.model.sales.SaleItemModel;
import com.app.main.model.sales.SaleModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleDAO {
    private static final Logger logger = LogManager.getLogger(SaleDAO.class.getName());
    private static final String SQL_ADD_SALE = "INSERT INTO SALE (customer, employee, final_price)\n" +
            "VALUES (?, ?, ?)";
    private static final String SQL_ADD_SALE_ITEM = "INSERT INTO SALE_ITEM (sale_id, model_id, quantity, discount)\n" +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_SALES = "SELECT * FROM SALE";
    private final Database database;

    public SaleDAO(Database database) {
        this.database = database;
    }

    private ArrayList<SaleItemModel> getSaleItems(Connection connection, int id) throws SQLException {
        ArrayList<SaleItemModel> items = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM SALE_ITEM WHERE sale_id = ?")) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SaleItemModel item = new SaleItemModel();
                item.setItem(database.getModel().getIdNameModel(result.getInt("model_id")));
                item.setQuantity(result.getInt("quantity"));
                item.setDiscount(result.getDouble("discount"));
                items.add(item);
            }
        }
        return items;
    }

    public ArrayList<SaleModel> getSales() {
        ArrayList<SaleModel> sales = new ArrayList<>();
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_GET_SALES)) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    SaleModel sale = new SaleModel(result.getInt("id"));
                    sale.setTotal(result.getDouble("final_price"));
                    sale.setTransactionDate(Util.parseDatabaseDate(result.getString("date")));
                    sale.setCustomer(database.getCustomer().getCustomer(result.getInt("customer")));
                    sale.setItems(FXCollections.observableArrayList(getSaleItems(connection, sale.getId())));
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to read all sales", e);
        }
        return sales;
    }

    private void insertSaleItems(Connection connection, int saleId, @NotNull ObservableList<SaleItemModel> items) {
        logger.info("Inserting sales items into database: {}", items);
        for (SaleItemModel item : items) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_SALE_ITEM)) {
                statement.setInt(1, saleId);
                statement.setInt(2, item.getItem().getId());
                statement.setInt(3, item.getQuantity());
                statement.setDouble(4, item.getQuantity());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Failed to insert new sale item: {}", item, e);
            }
        }
    }

    public void insertSale(int customer, int employee, double price, ObservableList<SaleItemModel> items) {
        logger.info("Insert sale into database");
        try (Connection connection = database.openConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_SALE)) {
                statement.setInt(1, customer);
                statement.setInt(2, employee);
                statement.setDouble(3, price);
                statement.executeUpdate();
                ResultSet genKey = statement.getGeneratedKeys();
                if (genKey.next()) insertSaleItems(connection, genKey.getInt(1), items);
            }
        } catch (SQLException e) {
            logger.error("Failed to insert new sale", e);
        }
    }
}
