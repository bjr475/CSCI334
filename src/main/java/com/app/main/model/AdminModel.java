package com.app.main.model;

import java.util.Date;

public class AdminModel extends EmployeeModel {
    public AdminModel() {
        super();
    }

    public AdminModel(String userID, Date createdTime, String email, String firstName, String lastName, String mobile, String password, String permissions) {
        super(userID, createdTime, email, firstName, lastName, mobile, password, permissions);
    }
}
