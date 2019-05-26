package com.app.main.controller.employee.sales;

import com.app.main.controller.AViewController;
import com.app.main.model.ApplicationModel;

public abstract class AChildSalesViewController extends AViewController {
    private SalesViewController owner;

    public AChildSalesViewController(ApplicationModel model) {
        super(model);
    }

    public SalesViewController getOwner() {
        return owner;
    }

    public void setOwner(SalesViewController owner) {
        this.owner = owner;
    }
}
