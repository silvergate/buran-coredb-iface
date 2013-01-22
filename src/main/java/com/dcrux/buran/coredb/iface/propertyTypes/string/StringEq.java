package com.dcrux.buran.coredb.iface.propertyTypes.string;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.dcrux.buran.coredb.iface.propertyTypes.RangeType;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class StringEq implements ICmp {
    public static final CmpRef REF = new CmpRef((short) 21);

    @Nullable
    private final String rhs;
    private final RangeType type;

    private StringEq(@Nullable String rhs, RangeType type) {
        if ((type != RangeType.equal) && (rhs == null))
            throw new IllegalArgumentException("Null is only allowed if RangeType.equal");
        this.rhs = rhs;
        this.type = type;
    }

    public static StringEq eq(String rhs) {
        if (rhs == null) throw new IllegalArgumentException("rhs==null");
        return new StringEq(rhs, RangeType.equal);
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    public RangeType getType() {
        return type;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        switch (this.type) {
            case equal:
                if ((lhs == null) && (this.rhs == null)) {
                    return true;
                }
                if (lhs == null) {
                    return false;
                }
                return lhs.equals(this.rhs);
            case greater:
                return String.CASE_INSENSITIVE_ORDER.compare((String) lhs, this.rhs) > 0;
            case lesser:
                return String.CASE_INSENSITIVE_ORDER.compare((String) lhs, this.rhs) < 0;
        }
        throw new IllegalArgumentException("Unknown type.");
    }
}
