package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.nodeClass.ISorter;
import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;
import com.dcrux.buran.coredb.iface.propertyTypes.SorterRefs;

/**
 * Buran.
 *
 * @author: ${USER} Date: 18.01.13 Time: 23:34
 */
public class NaturalLengthSort implements ISorter {

    public static final SorterRef REF = SorterRefs.NATURAL_NL;
    public static final SorterRef REF_NH = SorterRefs.NATURAL_NH;
    public static final NaturalLengthSort SINGLETON = new NaturalLengthSort(false);
    public static final NaturalLengthSort SINGLETON_NH = new NaturalLengthSort(true);

    private final boolean nh;

    public NaturalLengthSort(boolean nh) {
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
        BlobData d1 = (BlobData) o1;
        BlobData d2 = (BlobData) o2;
        return ((Long) d1.getLength()).compareTo((Long) d2.getLength());
    }
}
