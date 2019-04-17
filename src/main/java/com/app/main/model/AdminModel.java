package com.app.main.model;

import java.util.Date;

public class AdminModel extends UserModel {
    public AdminModel() {
        super();
    }

    public AdminModel(String userID, Date createdTime, String email, String firstName, String lastName, String mobile, String password) {
        super(userID, createdTime, email, firstName, lastName, mobile, password);
    }
}
