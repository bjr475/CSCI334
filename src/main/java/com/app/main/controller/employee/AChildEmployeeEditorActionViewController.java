package com.app.main.controller.employee;

import com.app.main.model.ApplicationModel;
import com.app.main.model.user.AUserModel;
import com.app.main.model.user.EmployeeModel;
import com.app.main.model.user.permissions.EmployeePermissions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import org.jetbrains.annotations.NotNull;

public abstract class AChildEmployeeEditorActionViewController extends AChildEmployeeViewController implements IEditorActionItem {
    private BooleanProperty edit;
    private BooleanProperty add;
    private BooleanProperty filter;
    private BooleanProperty search;

    public AChildEmployeeEditorActionViewController(ApplicationModel model) {
        super(model);

        edit = new SimpleBooleanProperty(true);
        add = new SimpleBooleanProperty(true);
        filter = new SimpleBooleanProperty(true);
        search = new SimpleBooleanProperty(true);

        model.currentUserProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getUserType() == AUserModel.UserType.EMPLOYEE) {
                    setUserEditable(((EmployeeModel) newValue).getPermissions());
                } else {
                    edit.set(true);
                    add.set(true);
                    filter.set(true);
                    search.set(true);
                    setAdminEditable();
                }
            }
        });
    }

    protected void setEditable(boolean state, @NotNull TextInputControl... textInputs) {
        for (TextInputControl input : textInputs) {
            input.setEditable(state);
        }
    }

    protected void setEditable(boolean state, @NotNull ButtonBase... buttons) {
        for (ButtonBase button : buttons) {
            button.setDisable(!state);
        }
    }

    protected void setEditable(boolean state, @NotNull ListView... views) {
        for (ListView view : views) {
            view.setEditable(state);
        }
    }

    @SafeVarargs
    protected final void setEditable(boolean state, @NotNull ChoiceBox<String>... choices) {
        for (ChoiceBox choice : choices) {
            choice.setVisible(state);
        }
    }

    protected void setEditable(boolean state, @NotNull TableView... tables) {
        for (TableView table : tables) {
            table.setEditable(state);
        }
    }

    protected abstract void setUserEditable(@NotNull EmployeePermissions permissions);

    protected abstract void setAdminEditable();

    public BooleanProperty editProperty() {
        return edit;
    }

    public BooleanProperty addProperty() {
        return add;
    }

    public BooleanProperty filterProperty() {
        return filter;
    }

    public BooleanProperty searchProperty() {
        return search;
    }
}
