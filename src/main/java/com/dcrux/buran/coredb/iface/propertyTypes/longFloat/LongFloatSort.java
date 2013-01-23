package com.dcrux.buran.coredb.iface.propertyTypes.longFloat;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class LongFloatSort implements ISorter {

    public static final SorterRef REF = SorterRefs.NATURAL_NL;
    public static final SorterRef REF_NH = SorterRefs.NATURAL_NH;
    public static final LongFloatSort SINGLETON = new LongFloatSort(false);
    public static final LongFloatSort SINGLETON_NH = new LongFloatSort(true);

    private final boolean nh;

    public LongFloatSort(boolean nh) {
        this.nh = nh;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return this.nh ? -1 : 1;
        }
        if (o2 == null) {
            return this.nh ? 1 : -1;
        }
        return ((Double) o2).compareTo((Double) o1);
    }
}
