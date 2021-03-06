package com.dcrux.buran.coredb.iface.propertyTypes.bool;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * @author caelis
 */
public class BoolNaturalSort implements ISorter {

    public static final SorterRef REF = SorterRefs.NATURAL_NL;
    public static final SorterRef REF_NH = SorterRefs.NATURAL_NH;
    public static final BoolNaturalSort SINGLETON = new BoolNaturalSort(false);
    public static final BoolNaturalSort SINGLETON_NH = new BoolNaturalSort(true);


    private final boolean nh;

    public BoolNaturalSort(boolean nh) {
        this.nh = nh;
    }

    @Override
    public int compare(Object o1, Object o2) {
        //TODO: Check this!
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return this.nh ? -1 : 1;
        }
        if (o2 == null) {
            return this.nh ? 1 : -1;
        }
        return ((Boolean) o2).compareTo((Boolean) o1);
    }
}
