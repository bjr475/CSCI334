package com.app.main.controller.employee;

import com.app.main.model.customer.CustomerModel;
import com.app.main.model.catalogue.CatalogueItemIdNameModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SearchableComboBoxUtil {
    public static final SearchableComboBoxUtilComparator<CatalogueItemIdNameModel> CATALOGUE_COMPARATOR = (input, object) -> {
        if (object.getName().contains(input)) return true;
        try {
            return object.getId() == Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return false;
        }
    };
    public static final SearchableComboBoxUtilComparator<CustomerModel> CUSTOMER_COMPARATOR = (input, object) -> {
        if (input.contains(object.toString())) return true;
        try {
            return input.contains(Integer.toString(object.getId()));
        } catch (NumberFormatException e) {
            return false;
        }
    };

    public static <T> void createSearchableComboBox(@NotNull ComboBox<T> box, SearchableComboBoxUtilComparator<T> comparator) {
        ObservableList<T> data = box.getItems();
        box.getEditor().focusedProperty().addListener(observable -> {
            if (box.getSelectionModel().getSelectedIndex() < 0) box.getEditor().setText(null);
        });
        box.addEventHandler(KeyEvent.KEY_PRESSED, t -> box.hide());
        box.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<>() {
            private boolean moveCaretToPos = false;
            private int caretPos;

            private boolean boxNotNull() {
                return box.getEditor().getText() != null;
            }

            private void notNullMove() {
                if (boxNotNull()) moveCaret(box.getEditor().getText().length());
            }

            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: {
                        caretPos = -1;
                        notNullMove();
                        return;
                    }
                    case DOWN: {
                        if (!box.isShowing()) box.show();
                        caretPos = -1;
                        notNullMove();
                        return;
                    }
                    case BACK_SPACE:
                    case DELETE: {
                        if (boxNotNull()) {
                            moveCaretToPos = true;
                            caretPos = box.getEditor().getCaretPosition();
                        }
                        break;
                    }
                    case ENTER:
                    case RIGHT:
                    case LEFT:
                    case SHIFT:
                    case CONTROL:
                    case HOME:
                    case END:
                    case TAB:
                        return;
                    default:
                        break;
                }
                if (event.isControlDown()) return;

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T aData : data) {
                    if (aData != null && boxNotNull() && comparator.matches(box.getEditor().getText(), aData)) {
                        list.add(aData);
                    }
                }
                String t = boxNotNull() ? "" : box.getEditor().getText();
                if (list.size() == 0) box.setItems(data);
                else box.setItems(list);
                box.getEditor().setText(t);
                if (!moveCaretToPos) caretPos = -1;
                moveCaret(t.length());
                if (!list.isEmpty()) box.show();
            }

            private void moveCaret(int textLength) {
                box.getEditor().positionCaret(caretPos == -1 ? textLength : caretPos);
                moveCaretToPos = false;
            }
        });
    }

    public static void setCatalogueIdModelConverter(@NotNull ComboBox<CatalogueItemIdNameModel> box) {
        box.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CatalogueItemIdNameModel> call(ListView<CatalogueItemIdNameModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CatalogueItemIdNameModel item, boolean empty) {
                        if (!empty && item != null) {
                            setGraphic(new Label(item.toStringValue()));
                            if (isSelected()) updateSelected(false);
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                };
            }
        });

        box.setConverter(new StringConverter<>() {
            @Override
            public String toString(CatalogueItemIdNameModel item) {
                if (item == null) return null;
                return item.toStringValue();
            }

            @Override
            public CatalogueItemIdNameModel fromString(String string) {
                return box.getItems().stream().filter(
                        item -> item.toStringValue().equals(string)
                ).findFirst().orElse(null);
            }
        });
    }

    public static void setCustomerModelConverter(@NotNull ComboBox<CustomerModel> box) {
        box.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CustomerModel> call(ListView<CustomerModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CustomerModel item, boolean empty) {
                        if (!empty && item != null) {
                            setGraphic(new Label(item.toStringValue()));
                            if (isSelected()) updateSelected(false);
                        } else {
                            super.updateItem(item, empty);
                        }
                    }
                };
            }
        });

        box.setConverter(new StringConverter<>() {
            @Override
            public String toString(CustomerModel item) {
                if (item == null) return null;
                return item.toStringValue();
            }

            @Override
            public CustomerModel fromString(String string) {
                return box.getItems().stream().filter(
                        item -> item.toStringValue().equals(string)
                ).findFirst().orElse(null);
            }
        });
    }

    @Nullable
    public static <T> T getComboBoxValue(@NotNull ComboBox<T> comboBox) {
        if (comboBox.getSelectionModel().getSelectedIndex() < 0) return null;
        return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
    }

    public static <T> void clearComboBoxValue(@NotNull ComboBox<T> comboBox) {
        comboBox.getSelectionModel().clearSelection();
    }

    public interface SearchableComboBoxUtilComparator<T> {
        boolean matches(String input, T object);
    }
}