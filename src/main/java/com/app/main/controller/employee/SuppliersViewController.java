package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

public class SuppliersViewController extends AChildEmployeeViewController implements IEditorActionItem {
    public JFXDrawer toolDrawer;
    public TableView tableView;
    public ScrollPane searchMenu;
    public ScrollPane viewMenu;
    public ScrollPane addMenu;

    public SuppliersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
        tableView.setOnMouseClicked(event -> activateControl(viewMenu));
    }

    private void activateControl(@NotNull Control pane) {
        pane.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onEdit() {
        activateControl(viewMenu);
    }

    @Override
    public void onAdd() {
        activateControl(addMenu);
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateControl(searchMenu);
    }
}
