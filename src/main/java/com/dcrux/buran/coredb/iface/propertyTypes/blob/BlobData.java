package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import java.io.ByteArrayOutputStream;

/**
 * Buran.
 *
 * @author: ${USER} Date: 16.01.13 Time: 00:09
 */
public final class BlobData {
    private final ByteArrayOutputStream data;

    public BlobData(ByteArrayOutputStream data) {
        this.data = data;
    }

    public int getLength() {
        return this.data.size();
    }

    public ByteArrayOutputStream getData() {
        return data;
    }
}
