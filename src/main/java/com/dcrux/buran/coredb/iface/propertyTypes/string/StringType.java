package com.dcrux.buran.coredb.iface.propertyTypes.string;

import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class StringType implements IType {

    /**
     *
     */
    private static final long serialVersionUID = -5754666614893018322L;
    public static final TypeRef REF = new TypeRef((short) 1);

    public static final short MAX_LEN_INDEXED = 4096;
    public static final int MAX_LEN = 262144;

    private final int maxLen;
    private final boolean indexed;


    @Override
    public TypeRef getRef() {
        return REF;
    }

    public static StringType indexed(int maxLen) {
        return new StringType(maxLen, true);
    }

    public static StringType c(int maxLen) {
        return new StringType(maxLen, false);
    }

    private StringType(int maxLen, boolean indexed) {
        if (indexed) {
            if (maxLen > MAX_LEN_INDEXED)
                throw new IllegalArgumentException("maxLen>MAX_LEN_INDEXED");
        } else {
            if (maxLen > MAX_LEN) throw new IllegalArgumentException("maxLen>MAX_LEN");
        }
        this.maxLen = maxLen;
        this.indexed = indexed;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (!this.indexed) return null;
        if (sorterRef.equals(StringSort.REF)) {
            return StringSort.SINGLETON;
        }
        if (sorterRef.equals(StringSort.REF_NH)) {
            return StringSort.SINGLETON_NH;
        }
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (this.indexed && (comparator.equals(StringEq.REF))) {
            return true;
        }
        if (this.indexed && (comparator.equals(Exists.REF))) return true;
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(PrimSet.class)) {
            final PrimSet ps = (PrimSet) dataSetter;
            if (ps.getValue() == null) return true;
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
