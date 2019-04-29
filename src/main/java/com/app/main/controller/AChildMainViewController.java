package com.app.main.controller;

import com.app.main.model.ApplicationModel;

public abstract class AChildMainViewController extends AViewController {
    private MainFrameViewController owner;

    public AChildMainViewController(ApplicationModel model) {
        super(model);
    }

    public MainFrameViewController getOwner() {
        return owner;
    }

    public void setOwner(MainFrameViewController owner) {
        this.owner = owner;
    }
}
