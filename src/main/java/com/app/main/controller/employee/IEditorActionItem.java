package com.app.main.controller.employee;

import javafx.beans.property.BooleanProperty;

public interface IEditorActionItem {
    boolean hasButtons();

    BooleanProperty editProperty();

    BooleanProperty addProperty();

    BooleanProperty filterProperty();

    BooleanProperty searchProperty();

    void onEdit();

    void onAdd();

    void onFilter();

    void onSearch();
}
