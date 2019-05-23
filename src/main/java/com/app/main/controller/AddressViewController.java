package com.app.main.controller;

import com.app.main.model.AddressModel;
import com.app.main.model.ApplicationModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Contract;

public class AddressViewController {

    private final ApplicationModel model;
    private final ObjectProperty<AddressModel> address;

    public TextField addressLine;
    public TextField addressSuburb;
    public ChoiceBox<String> addressState;
    public TextField addressPostcode;

    @Contract(pure = true)
    public AddressViewController(ApplicationModel model) {
        this.model = model;
        address = new SimpleObjectProperty<>();


        address.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                addressLine.textProperty().unbindBidirectional(oldValue.addressProperty());
                addressSuburb.textProperty().unbindBidirectional(oldValue.suburbProperty());
                addressState.valueProperty().unbindBidirectional(oldValue.stateProperty());
                addressPostcode.textProperty().unbindBidirectional(oldValue.postcodeProperty());

                addressLine.setText("Address");
                addressSuburb.setText("Suburb");
                addressState.setValue("State");
                addressPostcode.setText("Postcode");
            }
            if (newValue != null) {
                addressLine.textProperty().bindBidirectional(newValue.addressProperty());
                addressSuburb.textProperty().bindBidirectional(newValue.suburbProperty());
                addressState.valueProperty().bindBidirectional(newValue.stateProperty());
                addressPostcode.textProperty().bindBidirectional(newValue.postcodeProperty());
            }
        });
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

    public AddressModel getAddress() {
        return address.get();
    }

    public ObjectProperty<AddressModel> addressProperty() {
        return address;
    }
}
