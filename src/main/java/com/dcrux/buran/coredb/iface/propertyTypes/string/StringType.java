package com.dcrux.buran.coredb.iface.propertyTypes.string;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class StringType implements IType {

    public static final TypeRef REF = new TypeRef((short) 1);

    @Override
    public TypeRef getRef() {
        return REF;
    }

    private final boolean unicodeSorting;
    private final boolean unicodeRangeQuery;
    private final boolean equalsQuery;

    public boolean isUnicodeSorting() {
        return unicodeSorting;
    }

    public boolean isEqualsQuery() {
        return equalsQuery;
    }

    public StringType(boolean unicodeSorting, boolean unicodeRangeQuery, boolean equalsQuery) {
        this.unicodeSorting = unicodeSorting;
        this.unicodeRangeQuery = unicodeRangeQuery;
        this.equalsQuery = equalsQuery;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (sorterRef.equals(StringUnicodeSort.REF)) {
            return StringUnicodeSort.SINGLETON;
        }
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (this.equalsQuery && (comparator.equals(StringEq.REF))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(PrimSet.class)) {
            final PrimSet ps = (PrimSet) dataSetter;
            return ps.getValue() instanceof String;
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
        final String value = (String) ds.getValue();
        return value;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return (String) value;
    }
}