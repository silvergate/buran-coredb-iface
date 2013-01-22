package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import java.io.Serializable;

/**
 * Buran.
 *
 * @author: ${USER} Date: 16.01.13 Time: 00:09
 */
public final class BlobData implements Serializable {
    private final BinaryBlocks data;

    public BlobData(BinaryBlocks data) {
        this.data = data;
    }

    public int getLength() {
        return this.data.getLength();
    }

    public BinaryBlocks getData() {
        return data;
    }
}
