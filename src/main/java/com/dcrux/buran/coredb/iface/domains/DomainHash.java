package com.dcrux.buran.coredb.iface.domains;

import java.util.Arrays;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 01:24
 */
public class DomainHash {
    private final byte[] hash;

    private DomainHash(byte[] hash) {
        if (hash.length != 48) {
            throw new IllegalArgumentException("Hash has to be 364bit");
        }
        this.hash = hash;
    }

    public static DomainHash create(byte[] hash) {
        return new DomainHash(hash.clone());
    }

    public byte[] getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainHash that = (DomainHash) o;

        if (!Arrays.equals(hash, that.hash)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }
}
