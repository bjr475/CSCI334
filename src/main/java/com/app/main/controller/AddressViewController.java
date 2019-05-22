package com.app.main.controller;

import com.app.main.model.ApplicationModel;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddressViewController {

    private final ApplicationModel model;
    public TextField addressLine;
    public TextField addressSuburb;
    public ChoiceBox<String> addressState;
    public TextField addressPostcode;

    public AddressViewController(ApplicationModel model) {
        this.model = model;
    }

    public ApplicationModel getModel() {
        return model;
    }

    public void setEditable(boolean state) {
        addressLine.setEditable(state);
        addressSuburb.setEditable(state);
        addressState.setDisable(!state);
        addressPostcode.setEditable(state);
    }
}
