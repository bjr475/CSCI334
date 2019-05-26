package com.app.main.controller.employee;

import javafx.beans.property.BooleanProperty;

public interface IEditorActionItem {
    boolean hasButtons();

    BooleanProperty editProperty();

    BooleanProperty addProperty();

    void onEdit();

    void onAdd();
}
