package com.app.main.controller.employee.sales;

import com.app.main.model.ApplicationModel;
import com.app.main.model.sales.SaleItemModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class NewSaleConfirmViewController extends AChildSalesViewController {
    public TableView<SaleItemModel> itemsTable;
    public Text subtotal;
    public Text discount;
    public Text gst;
    public Text total;
    private double saleTotal;

    public NewSaleConfirmViewController(ApplicationModel model) {
        super(model);
        saleTotal = 0.0;
    }


    public void initialize() {
        SalesUtil.buildItemsTable(itemsTable);
    }

    public void back() {
        getOwner().newSaleConfirmBack();
    }

    public void confirm() {
        getOwner().newSaleConfirm();
    }

    public void setItems(ObservableList<SaleItemModel> saleItems) {
        saleTotal = SalesUtil.loadSaleResult(saleItems, itemsTable, subtotal, discount, gst, total);
    }

    public double getSaleTotal() {
        return saleTotal;
    }
}
