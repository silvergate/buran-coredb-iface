package com.dcrux.buran.coredb.iface.propertyTypes.longInt;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class LongNaturalSort implements ISorter {

    public static final SorterRef REF = SorterRefs.NATURAL_NL;
    public static final SorterRef REF_NH = SorterRefs.NATURAL_NH;
    public static final LongNaturalSort SINGLETON = new LongNaturalSort(false);
    public static final LongNaturalSort SINGLETON_NH = new LongNaturalSort(true);

    private final boolean nh;

    public LongNaturalSort(boolean nh) {
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
        return ((Long) o2).compareTo((Long) o1);
    }
}
