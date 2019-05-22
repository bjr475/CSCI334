package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class SalesViewController extends AChildEmployeeViewController implements IEditorActionItem {

    public JFXDrawer toolDrawer;
    public TableView tableView;
    public ScrollPane searchMenu;

    /*       Add menu         */
    public ScrollPane addMenu;
    public GridPane saleItemGrid;
    public GridPane saleCustomerGrid;
    public GridPane saleConfirmGrid;
    public ToggleButton newCustomer;

    /*       Edit menu        */
    public ScrollPane editMenu;


    public SalesViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
    }

    private void activateView(@NotNull Control view) {
        view.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onEdit() {
//        activateView(editMenu);
    }

    public void onCancelEdit() {
        editMenu.setVvalue(0);
        toolDrawer.close();
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {
        addMenu.setVvalue(0);
        toolDrawer.close();
    }

    public void onSaleNext() {
        addMenu.setVvalue(0);
        if (newCustomer.isSelected()) {
            saleCustomerGrid.toFront();
        } else {
            saleConfirmGrid.toFront();
        }
    }

    public void onCustomerNext() {
        addMenu.setVvalue(0);
        saleConfirmGrid.toFront();
    }

    public void onCustomerBack() {
        addMenu.setVvalue(0);
        saleItemGrid.toFront();
    }

    public void onConfirmBack() {
        addMenu.setVvalue(0);
        if (newCustomer.isSelected()) {
            saleCustomerGrid.toFront();
        } else {
            saleItemGrid.toFront();
        }
    }

    public void onConfirmAdd() {

    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}
