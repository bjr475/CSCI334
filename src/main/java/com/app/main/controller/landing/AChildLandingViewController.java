package com.app.main.controller.landing;

import com.app.main.controller.AViewController;
import com.app.main.model.ApplicationModel;

public abstract class AChildLandingViewController extends AViewController {
    private LandingViewController owner;

    public AChildLandingViewController(ApplicationModel model) {
        super(model);
    }

    public LandingViewController getOwner() {
        return owner;
    }

    public void setOwner(LandingViewController owner) {
        this.owner = owner;
    }
}
