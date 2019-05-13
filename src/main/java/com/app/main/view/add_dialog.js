function addNewDialog(dialog, parent) {
    dialog.show(parent);
}

function addNewDialogCheck(dialog, parent, check) {
    if(check.isSelected()) {
        dialog.show(parent);
    }
}