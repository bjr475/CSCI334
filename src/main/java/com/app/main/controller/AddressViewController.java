package com.app.main.controller;

import com.app.main.model.ApplicationModel;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddressViewController {

    private final ApplicationModel model;
    public TextField addressLine;
    public TextField suburb;
    public ChoiceBox<String> state;
    public TextField postcode;

    public AddressViewController(ApplicationModel model) {
        this.model = model;
    }

    public ApplicationModel getModel() {
        return model;
    }

    public void setEditable(boolean canModify) {
        addressLine.setEditable(canModify);
        suburb.setEditable(canModify);
        state.setDisable(canModify);
        postcode.setEditable(canModify);
    }
}
