package com.app.main.controller.employee.sales;

import com.app.database.Database;
import com.app.main.Util;
import com.app.main.model.sales.SaleItemModel;
import com.app.main.model.sales.SaleModel;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

class SalesUtil {
    static void buildSalesTable(@NotNull TableView<SaleModel> sales, SalesTableCallback callback) {
        TableColumn<SaleModel, Number> saleNoColumn = new TableColumn<>("Sale No.");
        saleNoColumn.setCellValueFactory(param -> param.getValue().idProperty());
        saleNoColumn.setCellFactory(Util::getIdCell);
        saleNoColumn.setPrefWidth(200);
        sales.getColumns().add(saleNoColumn);
        TableColumn<SaleModel, Number> customerNoColumn = new TableColumn<>("Customer No.");
        customerNoColumn.setCellValueFactory(param -> param.getValue().customerProperty().get().idProperty());
        customerNoColumn.setCellFactory(Util::getIdCell);
        customerNoColumn.setPrefWidth(200);
        sales.getColumns().add(customerNoColumn);
        TableColumn<SaleModel, Boolean> customerClubColumn = new TableColumn<>("Club Member");
        customerClubColumn.setCellValueFactory(param -> param.getValue().customerProperty().get().clubMemberProperty());
        customerClubColumn.setPrefWidth(200);
        sales.getColumns().add(customerClubColumn);
        TableColumn<SaleModel, Number> totalColumn = new TableColumn<>("Total");
        totalColumn.setCellValueFactory(param -> param.getValue().totalProperty());
        totalColumn.setCellFactory(Util::getPriceCell);
        totalColumn.setPrefWidth(200);
        sales.getColumns().add(totalColumn);

        sales.setRowFactory(param -> {
            TableRow<SaleModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) callback.callback(row.getItem());
            });
            return row;
        });
    }

    static void buildItemsTable(@NotNull TableView<SaleItemModel> itemsTable) {
        TableColumn<SaleItemModel, Number> numberColumn = new TableColumn<>("Item No.");
        numberColumn.setCellValueFactory(param -> param.getValue().getItem().idProperty());
        numberColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                if (!empty && item != null) setGraphic(new Label(Util.formatId(item)));
                else super.updateItem(item, empty);
            }
        });
        itemsTable.getColumns().add(numberColumn);
        TableColumn<SaleItemModel, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> param.getValue().getItem().nameProperty());
        itemsTable.getColumns().add(nameColumn);
        TableColumn<SaleItemModel, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty());
        itemsTable.getColumns().add(quantityColumn);
        TableColumn<SaleItemModel, Number> discountColumn = new TableColumn<>("Discount");
        discountColumn.setCellValueFactory(param -> param.getValue().discountProperty());
        itemsTable.getColumns().add(discountColumn);
    }

    static double loadSaleResult(ObservableList<SaleItemModel> items, @NotNull TableView<SaleItemModel> table, Text subtotal, Text discount, Text gst, Text total) {
        table.setItems(items);
        table.refresh();
        double basePrice = 0.0;
        double discountPrice = 0.0;
        for (SaleItemModel item : items) {
            double modelPrice = Database.INSTANCE.getModel().getModel(item.getItem().getId()).getPrice() * item.getQuantity();
            basePrice += modelPrice;
            discountPrice += (modelPrice * (item.getDiscount() / 100.));
        }
        double totalBeforeGST = basePrice - discountPrice;
        double gstPrice = totalBeforeGST * 0.15;
        double totalPrice = totalBeforeGST + gstPrice;

        subtotal.setText(Util.formatPrice(basePrice));
        discount.setText(Util.formatPrice(discountPrice));
        gst.setText(Util.formatPrice(gstPrice));
        total.setText(Util.formatPrice(totalPrice));
        return totalPrice;
    }

    public interface SalesTableCallback {
        void callback(SaleModel item);
    }
}
