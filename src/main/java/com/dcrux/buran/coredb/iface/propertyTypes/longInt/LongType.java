package com.dcrux.buran.coredb.iface.propertyTypes.longInt;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class LongType implements IType {
    /**
     *
     */
    private static final long serialVersionUID = 8861115637013126790L;

    public static final TypeRef REF = new TypeRef((short) 9543);

    private final boolean indexed;

    public LongType(boolean indexed) {
        this.indexed = indexed;
    }

    public LongType() {
        this(false);
    }

    public static LongType c() {
        return new LongType();
    }

    public static LongType indexed() {
        return new LongType(true);
    }

    @Override
    public TypeRef getRef() {
        return REF;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (!this.indexed) return null;
        if (sorterRef.equals(LongNaturalSort.REF)) return LongNaturalSort.SINGLETON;
        if (sorterRef.equals(LongNaturalSort.REF_NH)) return LongNaturalSort.SINGLETON_NH;
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (!this.indexed) return false;
        if (comparator.equals(LongEq.REF)) {
            return true;
        }
        if (comparator.equals(Exists.REF)) return true;
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(PrimSet.class)) {
            final PrimSet ps = (PrimSet) dataSetter;
            if (ps.getValue() == null) return true;
            return ps.getValue() instanceof Long;
        }
        return false;
    }

    @Override
    public boolean supports(IDataGetter dataGetter) {
        if (dataGetter.getClass().equals(PrimGet.class)) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object setData(IDataSetter dataSetter, @Nullable Object currentValue) {
        final PrimSet ds = (PrimSet) dataSetter;
        final Long value = (Long) ds.getValue();
        return value;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return (Long) value;
    }
}
