package com.example.fashionshop.cus_comp;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ReadOnlyObjectWrapperTableColume<T> implements Callback<TableColumn.CellDataFeatures<T, T>, ObservableValue<T>> {

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<T, T> cellDataFeatures) {
        return new ReadOnlyObjectWrapper<>(cellDataFeatures.getValue());
    }

}
