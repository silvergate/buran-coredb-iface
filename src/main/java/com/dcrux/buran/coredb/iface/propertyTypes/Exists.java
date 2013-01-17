package com.dcrux.buran.coredb.iface.propertyTypes;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class Exists implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 232);

    public static Exists c() {
        return new Exists();
    }

    public Exists() {
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        return (lhs != null);
    }
}
