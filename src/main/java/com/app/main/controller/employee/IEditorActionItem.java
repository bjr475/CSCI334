package com.app.main.controller.employee;

public interface IEditorActionItem {
    boolean hasButtons();

    void onEdit();

    void onAdd();

    void onFilter();

    void onSearch();
}
