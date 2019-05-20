package com.app.main.model.user;

public class AdminModel extends AUserModel {

    public AdminModel(int userId) {
        super(userId);
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }
}
