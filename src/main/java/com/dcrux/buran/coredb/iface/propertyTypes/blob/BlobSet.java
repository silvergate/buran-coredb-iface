package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.ByteContainer;
import com.dcrux.buran.coredb.iface.nodeClass.IDataSetter;

/**
 * Buran.
 *
 * @author: ${USER} Date: 17.01.13 Time: 21:58
 */
public class BlobSet implements IDataSetter {
    private final int pos;
    private final boolean allowOverwrite;
    private final ByteContainer data;

    public static BlobSet c(int pos, byte[] data) {
        return new BlobSet(pos, true, new ByteContainer(data));
    }

    public static BlobSet cAppendOnly(int pos, byte[] data) {
        return new BlobSet(pos, false, new ByteContainer(data));
    }

    public BlobSet(int pos, boolean allowOverwrite, ByteContainer data) {
        if (data.getNumOfBytes() < 1) throw new IllegalArgumentException("data.getNumOfBytes()<1");
        this.pos = pos;
        this.allowOverwrite = allowOverwrite;
        this.data = data.seal();
    }

    public int getPos() {
        return pos;
    }

    public boolean isAllowOverwrite() {
        return allowOverwrite;
    }

    public ByteContainer getData() {
        return data;
    }
}
