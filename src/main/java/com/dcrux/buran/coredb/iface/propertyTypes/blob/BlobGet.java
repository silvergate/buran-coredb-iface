package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.nodeClass.IDataGetter;

/**
 * Buran.
 *
 * @author: ${USER} Date: 17.01.13 Time: 21:37
 */
public final class BlobGet implements IDataGetter {
    private final long skip;
    private final long length;
    private final boolean strictLength;

    public static BlobGet c(int length) {
        return new BlobGet(0, length, true);
    }

    public static BlobGet asMuchAs(long length) {
        return new BlobGet(0, length, false);
    }

    public static BlobGet cSkip(int skip, int length) {
        return new BlobGet(skip, length, true);
    }

    public BlobGet(long skip, long length, boolean strictLength) {
        this.skip = skip;
        this.length = length;
        if (length < 1) throw new IllegalArgumentException("length<1");
        this.strictLength = strictLength;
    }

    public long getSkip() {
        return skip;
    }

    public long getLength() {
        return length;
    }

    public boolean isStrictLength() {
        return strictLength;
    }
}
