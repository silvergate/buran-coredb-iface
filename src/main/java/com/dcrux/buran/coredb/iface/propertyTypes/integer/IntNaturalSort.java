package com.dcrux.buran.coredb.iface.propertyTypes.integer;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class IntNaturalSort implements ISorter {

    public static final SorterRef REF = SorterRefs.NATURAL_NL;
    public static final SorterRef REF_NH = SorterRefs.NATURAL_NH;
    public static final IntNaturalSort SINGLETON = new IntNaturalSort(false);
    public static final IntNaturalSort SINGLETON_NH = new IntNaturalSort(true);

    public IntNaturalSort(boolean nh) {
        this.nh = nh;
    }

    private final boolean nh;

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
        return ((Integer) o2).compareTo((Integer) o1);
    }
}
