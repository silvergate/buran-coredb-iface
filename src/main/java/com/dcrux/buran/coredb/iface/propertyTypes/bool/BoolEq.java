package com.dcrux.buran.coredb.iface.propertyTypes.bool;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.dcrux.buran.coredb.iface.propertyTypes.RangeType;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class BoolEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 11233);

    @Nullable
    private final Boolean rhs;
    private final RangeType rangeType;

    public static BoolEq eq(@Nullable Boolean rhs) {
        return new BoolEq(rhs, RangeType.equal);
    }

    public static BoolEq gt(boolean rhs) {
        return new BoolEq(rhs, RangeType.greater);
    }

    public static BoolEq le(boolean rhs) {
        return new BoolEq(rhs, RangeType.lesser);
    }

    private BoolEq(@Nullable Boolean rhs, RangeType rangeType) {
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
        final boolean lhsBool = (Boolean) lhs;
        switch (this.rangeType) {
            case equal:
                return lhs.equals(this.rhs);
            case greater:
                return lhsBool;
            case lesser:
                return !lhsBool;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }
}
