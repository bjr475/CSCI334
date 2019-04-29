package com.app.main.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ApplicationModel {
    public static final AdminModel EMPTY_ADMIN = new AdminModel();

    private ObjectProperty<EmployeeModel> currentEmployee;
    private ObjectProperty<AdminModel> currentAdmin;

    public ApplicationModel() {
        currentAdmin = new SimpleObjectProperty<>(EMPTY_ADMIN);
        currentEmployee = new SimpleObjectProperty<>(null);
    }

    public AdminModel getCurrentAdmin() {
        return currentAdmin.get();
    }

    public ObjectProperty<AdminModel> currentAdminProperty() {
        return currentAdmin;
    }

    public EmployeeModel getCurrentEmployee() {
        return currentEmployee.get();
    }

    public void setCurrentEmployee(EmployeeModel currentEmployee) {
        this.currentEmployee.set(currentEmployee);
    }

    public ObjectProperty<EmployeeModel> currentEmployeeProperty() {
        return currentEmployee;
    }
}

