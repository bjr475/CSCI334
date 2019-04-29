function addNewDialog(dialog, parent) {
    dialog.show(parent);
}

function addNewDialog(dialog, parent, check) {
    if(check.isSelected()) {
        dialog.show(parent);
    }
}