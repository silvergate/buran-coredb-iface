package com.dcrux.buran.coredb.iface.propertyTypes.bool;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class BoolType implements IType {
    /**
     *
     */
    private static final long serialVersionUID = 8861115637013126790L;

    public static final TypeRef REF = new TypeRef((short) 11023);

    private final boolean indexed;

    public BoolType(boolean indexed) {
        this.indexed = indexed;
    }

    public BoolType() {
        this(false);
    }

    public static BoolType c() {
        return new BoolType();
    }

    public static BoolType indexed() {
        return new BoolType(true);
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
        if (sorterRef.equals(BoolNaturalSort.REF)) return BoolNaturalSort.SINGLETON;
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (!this.indexed) return false;
        if (comparator.equals(BoolEq.REF)) {
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
            return ps.getValue() instanceof Boolean;
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
        final Boolean value = (Boolean) ds.getValue();
        return value;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return (Boolean) value;
    }
}
