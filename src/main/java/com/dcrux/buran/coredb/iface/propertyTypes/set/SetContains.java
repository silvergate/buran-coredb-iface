package com.dcrux.buran.coredb.iface.propertyTypes.set;

import com.dcrux.buran.coredb.iface.ByteContainer;
import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author caelis
 */
public class SetContains implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 47);

    private final byte[] rhs;

    public SetContains(byte[] rhs) {
        if (rhs.length > SetType.MAX_LEN_BYTES) {
            throw new IllegalArgumentException("Invalid length");
        }
        this.rhs = rhs;
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        if (lhs == null) {
            return false;
        }
        final Set<ByteContainer> lhsAsSet = (Set<ByteContainer>) lhs;
        return lhsAsSet.contains(new ByteContainer(this.rhs));
    }
}
