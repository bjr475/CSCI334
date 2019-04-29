package com.app.main.model;

import java.util.Date;

public class EmployeeModel extends UserModel {
    public EmployeeModel() {
        super();
    }

    public EmployeeModel(String userID, Date createdTime, String email, String firstName, String lastName, String mobile, String password) {
        super(userID, createdTime, email, firstName, lastName, mobile, password);
    }
}
