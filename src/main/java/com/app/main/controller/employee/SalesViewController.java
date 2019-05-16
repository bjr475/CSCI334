package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

public class SalesViewController extends AChildEmployeeViewController implements IEditorActionItem {

    public JFXDrawer toolDrawer;
    public TableView tableView;
    public ScrollPane searchMenu;
    public ScrollPane addMenu;
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
        activateView(editMenu);
    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    @Override
    public void onFilter() {

    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}
