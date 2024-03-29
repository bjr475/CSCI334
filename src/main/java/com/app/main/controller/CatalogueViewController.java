package com.app.main.controller;

import com.app.main.controller.landing.LandingLoginViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CatalogueViewController extends AChildMainViewController {
    public JFXHamburger mainMenu;
    public JFXDrawer mainDrawer;

    @FXML
    public LandingLoginViewController landingloginController;

    @FXML
    public CustomersViewController customersController;

    @FXML
    public SuppliersViewController suppliersController;

    @FXML
    public SalesViewController salesController;

    @FXML
    public JFXButton catalogue, customer, suppliers, sales, settings, logout;

    @FXML
    public JFXButton editButton, addButton, filterButton, searchButton;

    @FXML
    public BorderPane modMenu, viewMenu;

    @FXML
    public JFXDrawer tableDisplay;

    @FXML
    public TableView tableView;

    @FXML
    public JFXCheckBox newSupplierCheck;

    @FXML
    public Text displayResponse;

    @FXML
    public Pane outsideMenu;

    @FXML
    public JFXDialog addSupplierDialog;

    @FXML
    public ScrollPane modMenuScroll;

    public CatalogueViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        ControllerUtil.prepareDrawer(mainDrawer, mainMenu);

        tableDisplay.toFront();

        editButton.setOnMouseClicked(event -> viewMenu.toFront());
        addButton.setOnMouseClicked(event -> modMenu.toFront());
        tableView.setOnMouseClicked(event -> viewMenu.toFront());

    }

    @FXML
    protected void onCancel(ActionEvent event) {
        tableDisplay.toFront();
        displayResponse.setText("");
        resetMenu();
    }

    @FXML
    protected void onSave(ActionEvent event) {
        displayResponse.setText("Catalogue item saved.");
    }

    @FXML
    protected void onEdit(ActionEvent event) {
        modMenu.toFront();
    }

/*    @FXML
    protected void onAddSupplier(ActionEvent event) {
        addSupplierDialog.toFront();
    }*/

    @FXML
    public void resetMenu() {
        modMenuScroll.setVvalue(0);
    }


    @FXML
    private void handleClickTableView(MouseEvent click) {
        /*UserList userlist = tableUser.getSelectionModel().getSelectedItem();
        if (userlist != null) {
            usr.username = userlist.getUsername();
            dal.getUserId(usr);
            txtUsername.setText(usr.username);
            txtFullname.setText(usr.fullname);
        }*/
    }

    public void confirmAddSupplier() {
        //SupplierModel supplierModel = supplierModel.getSupplierModel();

        addSupplierDialog.close();
    }

    public void addSupplierDialogReset() {
        //supplierModel.reset();
    }
}
