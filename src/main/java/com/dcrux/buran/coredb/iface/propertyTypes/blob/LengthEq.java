package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.dcrux.buran.coredb.iface.propertyTypes.RangeType;

import javax.annotation.Nullable;

/**
 * Buran.
 *
 * @author: ${USER} Date: 17.01.13 Time: 22:00
 */
public class LengthEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 234);

    private final int rhs;
    private final RangeType rangeType;

    public LengthEq(RangeType rangeType, int rhs) {
        this.rhs = rhs;
        this.rangeType = rangeType;
    }

    public int getRhs() {
        return rhs;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        if (lhs == null) return false;
        final BlobData data = (BlobData) lhs;
        switch (this.rangeType) {
            case equal:
                return data.getLength() == getRhs();
            case greater:
                return data.getLength() > getRhs();
            case lesser:
                return data.getLength() < getRhs();
            default:
                throw new IllegalArgumentException("Unknown range type");
        }
    }
}
