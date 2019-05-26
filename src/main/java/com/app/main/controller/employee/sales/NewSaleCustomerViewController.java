package com.app.main.controller.employee.sales;

import com.app.main.controller.AddressViewController;
import com.app.main.model.ApplicationModel;
import com.app.main.model.customer.CustomerModel;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class NewSaleCustomerViewController extends AChildSalesViewController {
    public TextField email;
    public TextField firstName;
    public TextField lastName;
    public AddressViewController addressController;
    public CheckBox clubMember;
    public ListView<String> subjects;
    public ListView<String> types;

    public NewSaleCustomerViewController(ApplicationModel model) {
        super(model);
    }

    public void back() {
        getOwner().newSaleCustomerBack();
    }

    public void next() {
        getOwner().newSaleCustomerNext();
    }

    public CustomerModel getCustomer() {
        CustomerModel model = new CustomerModel();
        model.setEmail(email.getText());
        model.setFirstName(firstName.getText());
        model.setLastName(lastName.getText());
        model.setAddress(addressController.getAddress());
        model.setClubMember(clubMember.isSelected());
        model.setSubjectAreas(subjects.selectionModelProperty().get().getSelectedItems());
        model.setModelTypes(types.selectionModelProperty().get().getSelectedItems());
        return model;
    }
}
