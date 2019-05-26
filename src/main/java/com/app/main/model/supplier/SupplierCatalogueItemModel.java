package com.app.main.model.supplier;

import com.app.main.model.catalogue.CatalogueItemModel;
import org.jetbrains.annotations.Contract;

public class SupplierCatalogueItemModel {
    private CatalogueItemModel catalogueItem;
    private SupplierItemModel supplierItem;

    @Contract(pure = true)
    public SupplierCatalogueItemModel(CatalogueItemModel catalogue, SupplierItemModel supplier) {
        catalogueItem = catalogue;
        supplierItem = supplier;
    }

    public CatalogueItemModel getCatalogueItem() {
        return catalogueItem;
    }

    public SupplierItemModel getSupplierItem() {
        return supplierItem;
    }
}
