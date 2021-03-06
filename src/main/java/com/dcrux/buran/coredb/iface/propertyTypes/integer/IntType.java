package com.dcrux.buran.coredb.iface.propertyTypes.integer;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class IntType implements IType {
    /**
     *
     */
    private static final long serialVersionUID = 8861115637013126790L;

    public static final TypeRef REF = new TypeRef((short) 22);

    private final boolean indexed;

    public IntType(boolean indexed) {
        this.indexed = indexed;
    }

    public IntType() {
        this(false);
    }

    public static IntType c() {
        return new IntType();
    }

    public static IntType indexed() {
        return new IntType(true);
    }

    public boolean isIndexed() {
        return indexed;
    }

    @Override
    public TypeRef getRef() {
        return REF;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (!this.indexed) return null;
        if (sorterRef.equals(IntNaturalSort.REF)) return IntNaturalSort.SINGLETON;
        if (sorterRef.equals(IntNaturalSort.REF_NH)) return IntNaturalSort.SINGLETON_NH;
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (!this.indexed) return false;
        if (comparator.equals(IntEq.REF)) {
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
            return ps.getValue() instanceof Integer;
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
        final Integer value = (Integer) ds.getValue();
        return value;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return (Integer) value;
    }
}
