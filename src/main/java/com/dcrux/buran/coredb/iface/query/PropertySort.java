package com.dcrux.buran.coredb.iface.query;

import com.dcrux.buran.coredb.iface.nodeClass.FieldIndex;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;

/**
 * @author caelis
 */
public class PropertySort implements ISorting {
    private final short fieldIndex;
    private final SortDirection order;
    private final SorterRef sorter;

    public short getFieldIndex() {
        return fieldIndex;
    }

    public SortDirection getOrder() {
        return order;
    }

    public SorterRef getSorter() {
        return sorter;
    }

    public static PropertySort c(FieldIndex fieldIndex, SorterRef sorter, SortDirection order) {
        return new PropertySort(fieldIndex.getIndex(), sorter, order);
    }

    public PropertySort(short fieldIndex, SorterRef sorter, SortDirection order) {
        this.fieldIndex = fieldIndex;
        this.order = order;
        this.sorter = sorter;
    }
}
