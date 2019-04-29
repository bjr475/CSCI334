package com.app.main.controller;

import com.app.main.model.ApplicationModel;

public abstract class AViewController {
    private ApplicationModel model;

    public AViewController(ApplicationModel model) {
        this.model = model;
    }

    public ApplicationModel getModel() {
        return model;
    }
}
