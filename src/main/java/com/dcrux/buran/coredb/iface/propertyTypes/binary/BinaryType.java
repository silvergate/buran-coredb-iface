package com.dcrux.buran.coredb.iface.propertyTypes.binary;

import com.dcrux.buran.coredb.iface.api.exceptions.ExpectableException;
import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public class BinaryType implements IType {

    public static final short MAX_LEN = 512;

    /**
     *
     */
    private static final long serialVersionUID = -5754666614893018322L;
    public static final TypeRef REF = new TypeRef((short) 8001);

    @Override
    public TypeRef getRef() {
        return REF;
    }

    private final boolean indexed;
    private final short maxLen;
    private final short minLen;

    public boolean isIndexed() {
        return indexed;
    }

    public static BinaryType indexed(int maxLen) {
        return new BinaryType((short) 0, (short) maxLen, true);
    }

    public static BinaryType c(int maxLen) {
        return new BinaryType((short) 0, (short) maxLen, false);
    }

    public BinaryType(short minLen, short maxLen, boolean indexed) {
        this.indexed = indexed;
        if (maxLen > MAX_LEN) throw new IllegalArgumentException("maxLen>MAX_LEN");
        this.maxLen = maxLen;
        this.minLen = minLen;
    }

    public short getMaxLen() {
        return maxLen;
    }

    public short getMinLen() {
        return minLen;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (this.indexed && (comparator.equals(BinaryEq.REF))) {
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
            return ps.getValue() instanceof byte[];
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
        final byte[] value = (byte[]) ds.getValue();
        if (value == null) return null;
        if (value.length < this.minLen) throw new ExpectableException("value.length<this.minLen");
        if (value.length > this.maxLen) throw new ExpectableException("value.length>this.maxLen");
        return value.clone();
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        if (value == null) return null;
        return ((byte[]) value).clone();
    }
}
