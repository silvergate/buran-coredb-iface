package com.dcrux.buran.coredb.iface.propertyTypes.integer;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class IntType implements IType {
    public static final TypeRef REF = new TypeRef((short) 22);

    @Override
    public TypeRef getRef() {
        return REF;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (comparator.equals(IntEq.REF)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(PrimSet.class)) {
            final PrimSet ps = (PrimSet) dataSetter;
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
