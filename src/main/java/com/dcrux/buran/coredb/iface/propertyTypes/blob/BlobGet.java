package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.nodeClass.IDataGetter;

/**
 * Buran.
 *
 * @author: ${USER} Date: 17.01.13 Time: 21:37
 */
public final class BlobGet implements IDataGetter {
    private final int skip;
    private final int length;
    private final boolean strictLength;

    public static BlobGet c(int length) {
        return new BlobGet(0, length, true);
    }

    public BlobGet(int skip, int length, boolean strictLength) {
        this.skip = skip;
        this.length = length;
        if (length < 1) throw new IllegalArgumentException("length<1");
        this.strictLength = strictLength;
    }

    public int getSkip() {
        return skip;
    }

    public int getLength() {
        return length;
    }

    public boolean isStrictLength() {
        return strictLength;
    }
}
