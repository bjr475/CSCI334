package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

public class CustomersViewController extends AChildEmployeeViewController implements IEditorActionItem {
    public JFXDrawer toolDrawer;

    @FXML
    public TableView tableView;

    @FXML
    public JFXRadioButton creditLineT, creditLineF, clubMemberT, clubMemberF;

    public BorderPane searchMenu;
    public BorderPane editMenu;
    public BorderPane addMenu;

    public CustomersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

        ToggleGroup creditLine = new ToggleGroup();
        creditLineT.setToggleGroup(creditLine);
        creditLineF.setToggleGroup(creditLine);

        ToggleGroup clubMember = new ToggleGroup();
        clubMemberT.setToggleGroup(clubMember);
        clubMemberF.setToggleGroup(clubMember);

        tableView.setOnMouseClicked(event -> activatePane(editMenu));
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

    }

    @Override
    public void onSearch() {
        activatePane(searchMenu);
    }
}
