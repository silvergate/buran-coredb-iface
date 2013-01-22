package com.dcrux.buran.coredb.iface.propertyTypes.binary;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author caelis
 */
public class BinaryEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 800);

    @Nullable
    private final byte[] rhs;

    public BinaryEq(@Nullable byte[] rhs) {
        this.rhs = rhs;
    }

    public static BinaryEq c(byte[] rhs) {
        if (rhs == null) throw new IllegalArgumentException("rhs==null");
        return new BinaryEq(rhs);
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
        return Arrays.equals((byte[]) lhs, this.rhs);
    }
}
