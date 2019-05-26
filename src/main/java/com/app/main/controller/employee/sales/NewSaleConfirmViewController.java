package com.app.main.controller.employee.sales;

import com.app.database.Database;
import com.app.main.Util;
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

    @Override
    public void setOwner(SalesViewController owner) {
        super.setOwner(owner);
        owner.buildItemsTable(itemsTable);
    }

    public void back() {
        getOwner().newSaleConfirmBack();
    }

    public void confirm() {
        getOwner().newSaleConfirm();
    }

    public void setItems(ObservableList<SaleItemModel> saleItems) {
        itemsTable.setItems(saleItems);
        itemsTable.refresh();

        double basePrice = 0.0;
        double discountPrice = 0.0;
        for (SaleItemModel item : saleItems) {
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
        saleTotal = totalPrice;
    }

    public double getSaleTotal() {
        return saleTotal;
    }
}
