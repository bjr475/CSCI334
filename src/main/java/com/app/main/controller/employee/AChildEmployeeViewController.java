package com.app.main.controller.employee;

import com.app.main.controller.AViewController;
import com.app.main.controller.EmployeeViewController;
import com.app.main.model.ApplicationModel;

public abstract class AChildEmployeeViewController extends AViewController {
    private EmployeeViewController owner;

    public AChildEmployeeViewController(ApplicationModel model) {
        super(model);
    }

    public EmployeeViewController getOwner() {
        return owner;
    }

    public void setOwner(EmployeeViewController owner) {
        this.owner = owner;
    }
}
