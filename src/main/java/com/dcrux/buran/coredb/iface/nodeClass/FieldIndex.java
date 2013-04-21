package com.dcrux.buran.coredb.iface.nodeClass;

/**
 * Buran.
 *
 * @author: ${USER} Date: 22.04.13 Time: 00:12
 */
public class FieldIndex {
    private final short index;

    public FieldIndex(short index) {
        this.index = index;
    }

    public static FieldIndex c(int index) {
        if (index > Short.MAX_VALUE) {
            throw new IllegalArgumentException("index>Short.MAX_VALUE");
        }
        return c((short) index);
    }

    public static FieldIndex c(short index) {
        return new FieldIndex(index);
    }

    public short getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldIndex fieldIndex = (FieldIndex) o;

        if (index != fieldIndex.index) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) index;
    }
}
