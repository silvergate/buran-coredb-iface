package com.dcrux.buran.coredb.iface.propertyTypes.string;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class StringEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 21);

    @Nullable
    private final String rhs;

    public StringEq(@Nullable String rhs) {
        this.rhs = rhs;
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        if ((lhs == null) && (this.rhs == null)) {
            return true;
        }
        if (lhs == null) {
            return false;
        }
        return lhs.equals(this.rhs);
    }
}
