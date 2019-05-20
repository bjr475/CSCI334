package com.app.database.dao.user;

import com.app.main.model.user.AUserModel;
import org.jetbrains.annotations.Contract;

public class User {
    private int id;
    private AUserModel.UserType type;

    @Contract(pure = true)
    public User(int userId, AUserModel.UserType userType) {
        id = userId;
        type = userType;
    }


    public int getId() {
        return id;
    }

    public AUserModel.UserType getType() {
        return type;
    }
}
