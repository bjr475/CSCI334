package com.app.main.controller.employee.manager;

import com.app.database.Database;
import com.app.main.controller.AddressViewController;
import com.app.main.controller.employee.AChildEmployeeEditorActionViewController;
import com.app.main.model.AddressModel;
import com.app.main.model.ApplicationModel;
import com.app.main.model.store.StoreModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.EmployeeNameId;
import com.app.main.model.user.permissions.EmployeePermissions;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ManageStoresViewController extends AChildEmployeeEditorActionViewController {
    private static final Logger logger = LogManager.getLogger(ManageStoresViewController.class.getName());
    private static final StringConverter<EmployeeNameId> MANAGER_CONVERTER = new StringConverter<>() {
        private String combine(@NotNull EmployeeNameId employeeNameId) {
            return String.format("%s - %s", employeeNameId.getDisplay(), employeeNameId.getFirstName());
        }

        @NotNull
        @Override
        public String toString(@NotNull EmployeeNameId item) {
            return combine(item);
        }

        @Override
        public EmployeeNameId fromString(String string) {
            return Database.INSTANCE.getUser().getEmployeeNameIds().stream().filter(
                    employeeNameId -> combine(employeeNameId).equals(string)
            ).findFirst().orElse(null);
        }
    };
    public JFXDrawer toolDrawer;
    public ScrollPane filterMenu;
    public ScrollPane searchMenu;
    public TableView<StoreModel> storesView;
    public ScrollPane editMenu;
    public TextField editStoreId;
    public TextField editStoreName;
    public ChoiceBox<EmployeeNameId> editStoreManager;
    public AddressViewController editAddressController;
    public ScrollPane addMenu;
    public TextField addStoreName;
    public ChoiceBox<EmployeeNameId> addStoreManager;
    public AddressViewController addAddressController;
    private ObjectProperty<StoreModel> editStore;

    public ManageStoresViewController(ApplicationModel model) {
        super(model);
        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getUserType() == AUserModel.UserType.ADMIN) {
                refreshStoresTable();
            }
        });

        editStore = new SimpleObjectProperty<>(null);
        editStore.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                editStoreId.setText("-- Store ID --");
                editStoreName.textProperty().unbindBidirectional(oldValue.nameProperty());
                editAddressController.addressProperty().unbindBidirectional(oldValue.addressProperty());
                editStoreManager.setValue(null);
            }
            if (newValue != null) {
                editStoreId.setText(String.format("%d", newValue.getStoreId()));
                editStoreName.textProperty().bindBidirectional(newValue.nameProperty());
                EmployeeModel m = Database.INSTANCE.getUser().getEmployee(newValue.getManagerId());
                EmployeeNameId manager = new EmployeeNameId(m.getId(), m.getDisplayName(), m.getFirstName());
//                if (!editStoreManager.getItems().contains(manager)) editStoreManager.getItems().add(0, manager);
                editStoreManager.setValue(manager);
                editAddressController.addressProperty().bindBidirectional(newValue.addressProperty());
            }
        });
    }

    @Override
    protected void setUserEditable(@NotNull EmployeePermissions permissions) {

    }

    @Override
    protected void setAdminEditable() {

    }

    private void refreshStoresTable() {
        ArrayList<StoreModel> stores = Database.INSTANCE.getStore().getStores();
        storesView.itemsProperty().set(FXCollections.observableArrayList(stores));
        storesView.refresh();

        addStoreManager.setItems(FXCollections.observableArrayList(Database.INSTANCE.getUser().getEmployeeNameIds()));
        editStoreManager.setItems(FXCollections.observableArrayList(Database.INSTANCE.getUser().getEmployeeNameIds()));
    }

    private void buildStoresTable() {
        TableColumn<StoreModel, Number> storeIdColumn = new TableColumn<>("Id");
        storeIdColumn.setCellValueFactory(param -> param.getValue().storeIdProperty());
        storesView.getColumns().add(storeIdColumn);

        TableColumn<StoreModel, String> storeNameColumn = new TableColumn<>("Name");
        storeNameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        storesView.getColumns().add(storeNameColumn);

        TableColumn<StoreModel, AddressModel> storeAddressColumn = new TableColumn<>("Address");
        storeAddressColumn.setCellValueFactory(param -> param.getValue().addressProperty());
        storeAddressColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(AddressModel item, boolean empty) {
                if (item != null && !empty) {
                    setGraphic(new Label(String.format(
                            "%s %s %s %s",
                            item.getAddress(),
                            item.getSuburb(),
                            item.getState(),
                            item.getPostcode()
                    )));
                }
            }
        });
        storesView.getColumns().add(storeAddressColumn);

        TableColumn<StoreModel, String> storeManagerColumn = new TableColumn<>("Manager");
        storeManagerColumn.setCellValueFactory(param -> param.getValue().managerNameProperty());
        storesView.getColumns().add(storeManagerColumn);

        storesView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) openEdit(storesView.getSelectionModel().getSelectedItem());
        });
    }

    private void openEdit(StoreModel item) {
        if (item != null) {
            editStore.set(new StoreModel(item));
            activateView(editMenu);
        }
    }

    public void cancelEdit() {
        toolDrawer.close();
        editStore.set(null);
    }

    public void confirmEdit() {
        editStore.get().setManagerId(editStoreManager.getValue().getId());
        Database.INSTANCE.getStore().updateStore(editStore.get());
        refreshStoresTable();
        toolDrawer.close();
        editStore.set(null);
    }

    public void cancelAdd() {
        refreshStoresTable();
        toolDrawer.close();
        addStoreName.setText("");
        addStoreManager.setValue(null);
    }

    public void confirmAdd() {
        StoreModel model = new StoreModel(-1);
        model.setName(addStoreName.getText());
        model.setAddress(addAddressController.getAddress());
        EmployeeNameId nameId = addStoreManager.getValue();
        model.setManagerId(nameId == null ? 0 : nameId.getId());
        Database.INSTANCE.getStore().saveStore(model);
        cancelAdd();
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
        buildStoresTable();

        addStoreManager.setConverter(MANAGER_CONVERTER);
        editStoreManager.setConverter(MANAGER_CONVERTER);

        logger.debug("Address Controller Edit ({}) >> Add ({})", editAddressController, addAddressController);
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
        openEdit(storesView.getSelectionModel().getSelectedItem());
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
