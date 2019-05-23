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
                suburb.textProperty().unbindBidirectional(oldValue.suburbProperty());
                state.valueProperty().unbindBidirectional(oldValue.stateProperty());
                postcode.textProperty().unbindBidirectional(oldValue.postcodeProperty());

                addressLine.setText("Address");
                suburb.setText("Suburb");
                state.setValue("State");
                postcode.setText("Postcode");
            }
            if (newValue != null) {
                addressLine.textProperty().unbindBidirectional(newValue.addressProperty());
                suburb.textProperty().unbindBidirectional(newValue.suburbProperty());
                state.valueProperty().unbindBidirectional(newValue.stateProperty());
                postcode.textProperty().unbindBidirectional(newValue.postcodeProperty());
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
