package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import org.jetbrains.annotations.NotNull;

public class ManageEmployeesViewController extends AChildEmployeeViewController implements IEditorActionItem {

    public JFXDrawer toolDrawer;
    public ScrollPane editMenu;
    public ScrollPane addMenu;
    public ScrollPane filterMenu;
    public ScrollPane searchMenu;

    public ManageEmployeesViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
    }

    private void activateView(@NotNull ScrollPane view) {
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
        activateView(filterMenu);
    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }
}

