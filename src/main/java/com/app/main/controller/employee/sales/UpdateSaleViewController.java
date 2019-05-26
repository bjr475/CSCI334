package com.app.main.controller.employee.sales;

import com.app.main.Util;
import com.app.main.model.ApplicationModel;
import com.app.main.model.sales.SaleItemModel;
import com.app.main.model.sales.SaleModel;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

public class UpdateSaleViewController extends AChildSalesViewController {
    public TableView<SaleItemModel> itemsTable;
    public Text date;
    public Text customer;
    public Text subtotal;
    public Text discount;
    public Text gst;
    public Text total;

    public UpdateSaleViewController(ApplicationModel model) {
        super(model);
    }

    public void initialize() {
        SalesUtil.buildItemsTable(itemsTable);
    }

    public void setSale(@NotNull SaleModel model) {
        date.setText(Util.formatPrintDate(model.getTransactionDate()));
        customer.setText(String.format("%s %s", model.getCustomer().getFirstName(), model.getCustomer().getLastName()));

        SalesUtil.loadSaleResult(model.getItems(), itemsTable, subtotal, discount, gst, total);
    }

    public void refund(ActionEvent event) {

    }
}
