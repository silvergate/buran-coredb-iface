package com.dcrux.buran.coredb.iface.propertyTypes;

import com.dcrux.buran.coredb.iface.ByteContainer;
import com.dcrux.buran.coredb.iface.nodeClass.IDataSetter;

import java.util.Set;

/**
 * @author caelis
 */
public final class PrimSet implements IDataSetter {
    private final Object value;

    private PrimSet(Object value) {
        this.value = value;
    }

    public static PrimSet string(final String value) {
        if (value == null) throw new IllegalArgumentException("value==null");
        return new PrimSet(value);
    }

    public static PrimSet integer(final int value) {
        return new PrimSet(value);
    }

    public static PrimSet set(Set<ByteContainer> setEntries) {
        if (setEntries == null) throw new IllegalArgumentException("setEntries==null");
        return new PrimSet(setEntries);
    }

    public static PrimSet extinct() {
        return new PrimSet(null);
    }

    public Object getValue() {
        return value;
    }
}
