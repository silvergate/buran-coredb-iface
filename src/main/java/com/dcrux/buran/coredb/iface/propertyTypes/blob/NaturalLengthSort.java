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

    public static final SorterRef REF = SorterRefs.NATURAL;
    public static final NaturalLengthSort SINGLETON = new NaturalLengthSort();

    @Override
    public ISorter getSingleton() {
        return SINGLETON;
    }

    @Override
    public SorterRef getRef() {
        return REF;
    }

    @Override
    public int compare(Object o1, Object o2) {
        //TODO: Check this!
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        BlobData d1 = (BlobData) o1;
        BlobData d2 = (BlobData) o2;
        return ((Integer) d1.getLength()).compareTo((Integer) d2.getLength());
    }
}
