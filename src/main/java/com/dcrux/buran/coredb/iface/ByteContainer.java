package com.dcrux.buran.coredb.iface;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Arrays;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 18:05
 */
@ThreadSafe
public class ByteContainer {
    private volatile byte[] data;
    private boolean sealed;

    public byte[] getData() {
        return this.data.clone();
    }

    /**
     * Makes this container immune to changes in the backing array. Does nothing if already sealed.
     * This method is not thread-safe.
     */
    public ByteContainer seal() {
        if (!this.sealed) {
            synchronized (this) {
                if (!this.sealed) {
                    this.sealed = true;
                    this.data = this.data.clone();
                }
            }
        }
        return this;
    }

    public ByteContainer(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("data must not be null");
        }
        this.data = data;
    }

    public int getNumOfBytes() {
        return this.data.length;
    }

    public byte get(int index) {
        return this.data[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ByteContainer that = (ByteContainer) o;

        if (!Arrays.equals(data, that.data)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
