package com.app.main.controller.employee.supplier;

import com.app.main.controller.AViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.supplier.SupplierContactModel;
import com.jfoenix.controls.JFXDialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SupplierContactViewController extends AViewController {
    private static final Logger logger = LogManager.getLogger(SupplierContactViewController.class.getName());

    public TextField name;
    public TextField phone;
    public TextField email;
    public JFXDialog dialog;
    private ContactCallback contactCallback;
    private SupplierContactModel contactModel;

    public SupplierContactViewController(ApplicationModel model) {
        super(model);
        contactModel = new SupplierContactModel();
        contactCallback = null;
    }

    public SupplierContactModel getContactModel() {
        return contactModel;
    }

    public void setContactModel(SupplierContactModel contactModel) {
        this.contactModel = contactModel;
        logger.info("Loading Contact Model: {}", contactModel);
        if (contactModel != null) {
            name.setText(contactModel.getName());
            phone.setText(contactModel.getPhone());
            email.setText(contactModel.getEmail());
        } else {
            name.setText("-- Value --");
            phone.setText("-- Value --");
            email.setText("-- Value --");
        }
    }

    public void confirm() {
        contactModel.setName(name.getText());
        contactModel.setEmail(email.getText());
        contactModel.setPhone(phone.getText());
        cancel();
        logger.info("Confirm Contact Action: {}", contactModel);
        if (contactCallback != null) contactCallback.confirm(contactModel);
        contactCallback = null;
    }

    public void cancel() {
        name.setText("");
        phone.setText("");
        email.setText("");
        dialog.close();
    }

    public void open(StackPane parent, ContactCallback callback) {
        contactCallback = callback;
        dialog.show(parent);
    }

    public interface ContactCallback {
        void confirm(SupplierContactModel contact);
    }
}
