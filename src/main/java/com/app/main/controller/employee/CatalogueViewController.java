package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.app.main.model.CatalogueModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class CatalogueViewController extends AChildEmployeeViewController implements IEditorActionItem {
    public JFXDrawer toolDrawer;
    public TableView<CatalogueModel> tableView;
    public BorderPane searchMenu;
    public BorderPane filterMenu;
    public BorderPane addMenu;
    public BorderPane editMenu;

    public CatalogueViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        tableView.selectionModelProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue != null) {
                String rowID = tableView.getSelectionModel().getSelectedItem().toString();
            }
        });
    }

    private void activatePane(@NotNull Pane pane) {
        pane.toFront();
        toolDrawer.open();
    }

    @Override
    public boolean hasButtons() {
        return true;
    }

    @Override
    public void onEdit() {
        activatePane(editMenu);
    }

    @Override
    public void onAdd() {
        activatePane(addMenu);
    }

    @Override
    public void onFilter() {
        activatePane(filterMenu);
    }

    @Override
    public void onSearch() {
        activatePane(searchMenu);
    }

    public void onCancelSearch() {
        // TODO cancel search action
        toolDrawer.close();
    }
}
