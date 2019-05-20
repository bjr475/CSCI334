package com.app.main.model.user;

import com.app.main.model.user.permissions.EmployeePermissions;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class EmployeeModel extends AUserModel {
    private ObjectProperty<EmployeeStoreModel> store;
    private ObjectProperty<EmployeePermissions> permissions;

    public EmployeeModel(int userId) {
        super(userId);
        store = new SimpleObjectProperty<>(null);
        permissions = new SimpleObjectProperty<>(new EmployeePermissions());
    }

    @Override
    public UserType getUserType() {
        return UserType.EMPLOYEE;
    }

    public EmployeeStoreModel getStore() {
        return store.get();
    }

    public void setStore(EmployeeStoreModel store) {
        this.store.set(store);
    }

    public ObjectProperty<EmployeeStoreModel> storeProperty() {
        return store;
    }

    public EmployeePermissions getPermissions() {
        return permissions.get();
    }

    public void setPermissions(EmployeePermissions permissions) {
        this.permissions.set(permissions);
    }

    public ObjectProperty<EmployeePermissions> permissionsProperty() {
        return permissions;
    }
}
