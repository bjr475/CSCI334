package com.app.main.controller;

import com.app.main.model.ApplicationModel;

public class AddressViewController {

    private final ApplicationModel model;

    public AddressViewController(ApplicationModel model) {
        this.model = model;
    }

    public ApplicationModel getModel() {
        return model;
    }
}
