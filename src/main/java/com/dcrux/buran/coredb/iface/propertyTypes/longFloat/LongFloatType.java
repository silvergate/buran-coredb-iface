package com.dcrux.buran.coredb.iface.propertyTypes.longFloat;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class LongFloatType implements IType {
    /**
     *
     */
    private static final long serialVersionUID = 8861115637013126790L;

    public static final TypeRef REF = new TypeRef((short) 15002);

    private final boolean indexed;

    public LongFloatType(boolean indexed) {
        this.indexed = indexed;
    }

    public LongFloatType() {
        this(false);
    }

    public static LongFloatType c() {
        return new LongFloatType();
    }

    public static LongFloatType cIndexed() {
        return new LongFloatType(true);
    }

    @Override
    public TypeRef getRef() {
        return REF;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (!this.indexed) return null;
        if (sorterRef.equals(LongFloatSort.REF)) return LongFloatSort.SINGLETON;
        if (sorterRef.equals(LongFloatSort.REF_NH)) return LongFloatSort.SINGLETON_NH;
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (!this.indexed) return false;
        if (comparator.equals(LongFloatEq.REF)) {
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
            return ps.getValue() instanceof Double;
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
        final Double value = (Double) ds.getValue();
        return value;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return (Double) value;
    }
}
