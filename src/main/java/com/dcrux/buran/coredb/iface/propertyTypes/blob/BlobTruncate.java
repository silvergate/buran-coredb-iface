package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.nodeClass.IDataSetter;
import com.google.common.base.Optional;

/**
 * Buran.
 *
 * @author: ${USER} Date: 23.01.13 Time: 22:55
 */
public class BlobTruncate implements IDataSetter {
    private final Optional<Long> numberOfBytes;

    public static BlobTruncate all() {
        return new BlobTruncate(Optional.<Long>absent());
    }

    public static BlobTruncate c(long numberOfBytes) {
        return new BlobTruncate(Optional.<Long>of(numberOfBytes));
    }

    private BlobTruncate(Optional<Long> numberOfBytes) {
        this.numberOfBytes = numberOfBytes;
    }

    public Optional<Long> getNumberOfBytes() {
        return numberOfBytes;
    }
}
