package com.dcrux.buran.coredb.iface.propertyTypes.longInt;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.dcrux.buran.coredb.iface.propertyTypes.RangeType;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class LongEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 9034);

    @Nullable
    private final Long rhs;
    private final RangeType rangeType;

    public static LongEq eq(@Nullable Long rhs) {
        return new LongEq(rhs, RangeType.equal);
    }

    public static LongEq gt(long rhs) {
        return new LongEq(rhs, RangeType.greater);
    }

    public static LongEq le(long rhs) {
        return new LongEq(rhs, RangeType.lesser);
    }

    private LongEq(@Nullable Long rhs, RangeType rangeType) {
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
        final long lhsInt = (Long) lhs;
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
