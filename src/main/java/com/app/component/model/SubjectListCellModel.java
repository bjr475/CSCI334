package com.app.component.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SubjectListCellModel {
    private StringProperty subjectArea;

    public SubjectListCellModel(String type) {
        subjectArea = new SimpleStringProperty(type);
    }

    public String getSubjectArea() {
        return subjectArea.get();
    }

    void setSubjectArea(String type) {
        subjectArea.setValue(type);
    }
}

