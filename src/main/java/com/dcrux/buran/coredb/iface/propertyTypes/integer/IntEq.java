package com.dcrux.buran.coredb.iface.propertyTypes.integer;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.dcrux.buran.coredb.iface.propertyTypes.RangeType;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class IntEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 44);

    @Nullable
    private final Integer rhs;
    private final RangeType rangeType;

    public static IntEq eq(@Nullable Integer rhs) {
        return new IntEq(rhs, RangeType.equal);
    }

    public static IntEq gt(int rhs) {
        return new IntEq(rhs, RangeType.greater);
    }

    public static IntEq le(int rhs) {
        return new IntEq(rhs, RangeType.lesser);
    }

    private IntEq(@Nullable Integer rhs, RangeType rangeType) {
        this.rhs = rhs;
        this.rangeType = rangeType;
        if ((rhs == null) && (rangeType != RangeType.equal))
            throw new IllegalArgumentException("Null is only supported if RangeType is 'equals'.");
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
        if (this.rhs == null) {
            return false;
        }
        final int lhsInt = (Integer) lhs;
        switch (this.rangeType) {
            case equal:
                return lhs.equals(this.rhs);
            case greater:
                return lhsInt > this.rhs;
            case lesser:
                return lhsInt < this.rhs;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }
}
