package com.app.main.controller.employee;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class CustomersViewController extends AChildEmployeeViewController implements IEditorActionItem {
    private static final Logger logger = LogManager.getLogger(CustomersViewController.class.getName());

    /* Tool Drawer */
    public JFXDrawer toolDrawer;
    public ListView<String> addTypes;
    public ListView<String> addSubjects;

    /* Edit Control */
    public ScrollPane editMenu;
    public TextField editEmail;
    public TextField editCustomerID;
    public TextField editFirstName;
    public TextField editLastName;
    //public ChoiceBox<String> editAddress;

    @FXML
    public AddressViewController editAddressController;

    /* Add Control */
    public ScrollPane addMenu;
    public TextField addEmail;
    public TextField addCustomerID;
    public TextField addFirstName;
    public TextField addLastName;

    @FXML
    public AddressViewController addAddressController;
    //public ChoiceBox<String> addAddress;

    /* Filter Control */
    public ScrollPane filterMenu;

    /* Search Control */
    public ScrollPane searchMenu;
    public TextField searchWords;

//    /* Catalogue Table */
//    public TableView<CustomerModel> catalogueTable;
//
//    /* Add and Edit Values */
//    private ObjectProperty<CustomerModel> currentAddItem;
//    private ObjectProperty<CustomerModel> currentEditableItem;


    public CustomersViewController(ApplicationModel model) {
        super(model);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);

//        customersTable.setOnMouseClicked(event -> activateView(editMenu));

        addTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addTypes.setOnMouseClicked(event -> {

        });
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

    }

    @Override
    public void onAdd() {
        activateView(addMenu);
    }

    public void onCancelAdd() {
        toolDrawer.close();
    }

    public void onConfirmAdd() {
        ObservableList<String> selectedTypes = addTypes.getSelectionModel().getSelectedItems();
        for (String item : selectedTypes) {
            logger.info("Item {} has been selected", item);
        }
        ObservableList<String> selectedSubjects = addSubjects.getSelectionModel().getSelectedItems();
        for (String item : selectedSubjects) {
            logger.info("Item {} has been selected", item);
        }
    }

    @Override
    public void onFilter() {
        activateView(filterMenu);
    }

    @Override
    public void onSearch() {
        activateView(searchMenu);
    }

    public void resetSearch() {
        searchWords.setText("");
    }

    public void confirmSearch() {
        logger.info(
                "Looking for catalogue items that contain the following words: '{}'",
                searchWords.getText()
        );
    }
}
