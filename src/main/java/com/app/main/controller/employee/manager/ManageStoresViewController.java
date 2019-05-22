package com.app.main.controller.employee.manager;

import com.app.database.Database;
import com.app.main.controller.employee.AChildEmployeeViewController;
import com.app.main.controller.employee.IEditorActionItem;
import com.app.main.model.ApplicationModel;
import com.app.main.model.store.StoreModel;
import com.app.main.model.user.AUserModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class ManageStoresViewController extends AChildEmployeeViewController implements IEditorActionItem {
    public JFXDrawer toolDrawer;
    public ScrollPane editMenu;
    public ScrollPane addMenu;
    public ScrollPane filterMenu;
    public ScrollPane searchMenu;
    public TableView<StoreModel> storesView;

    public TextField editStoreId;
    public TextField editStoreName;
    public TextField editStoreManagerName;
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
                editStoreManagerName.textProperty().unbindBidirectional(oldValue.managerNameProperty());
            }

            if (newValue != null) {
                editStoreId.setText(String.format("%d", newValue.getStoreId()));
                editStoreName.textProperty().bindBidirectional(newValue.nameProperty());
                editStoreManagerName.textProperty().bindBidirectional(newValue.managerNameProperty());
            }
        });
    }

    private void refreshStoresTable() {
        storesView.itemsProperty().set(FXCollections.observableArrayList(Database.INSTANCE.getStore().getStores()));
        storesView.refresh();
    }

    private void buildStoresTable() {
        TableColumn<StoreModel, Number> storeIdColumn = new TableColumn<>("Id");
        storeIdColumn.setCellValueFactory(param -> param.getValue().storeIdProperty());
        storesView.getColumns().add(storeIdColumn);

        TableColumn<StoreModel, String> storeNameColumn = new TableColumn<>("Name");
        storeNameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        storesView.getColumns().add(storeNameColumn);

        TableColumn<StoreModel, String> storeAddressColumn = new TableColumn<>("Address");
        storeAddressColumn.setCellValueFactory(param -> param.getValue().addressProperty());
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
        Database.INSTANCE.getStore().saveStore(editStore.get());
        refreshStoresTable();
        toolDrawer.close();
        editStore.set(null);
    }

    @FXML
    public void initialize() {
        toolDrawer.setDefaultDrawerSize(600);
        buildStoresTable();
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
