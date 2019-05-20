package com.app.main.model;

import com.app.main.model.user.AUserModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ApplicationModel {

    private ObjectProperty<AUserModel> currentUser;

    public ApplicationModel() {
        currentUser = new SimpleObjectProperty<>(null);
    }

    public AUserModel getCurrentUser() {
        return currentUser.get();
    }

    public void setCurrentUser(AUserModel currentUser) {
        this.currentUser.set(currentUser);
    }

    public ObjectProperty<AUserModel> currentUserProperty() {
        return currentUser;
    }
}

