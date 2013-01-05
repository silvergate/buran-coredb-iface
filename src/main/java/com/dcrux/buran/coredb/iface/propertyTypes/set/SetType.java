package com.dcrux.buran.coredb.iface.propertyTypes.set;

import com.dcrux.buran.coredb.iface.ByteContainer;
import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimGet;
import com.dcrux.buran.coredb.iface.propertyTypes.PrimSet;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author caelis
 */
public class SetType implements IType {

    public static final int MAX_LEN_BYTES = 256;
    public static final int MAX_NUM_OF_ELEMENTS = 512;

    public static final TypeRef REF = new TypeRef((short) 24);

    private final int maxNumOfElements;
    private final int maxLenOfBytes;

    public SetType(int maxNumOfElements, int maxLenOfBytes) {
        if (maxNumOfElements > MAX_NUM_OF_ELEMENTS) {
            throw new IllegalArgumentException("Max num of elements too high");
        }
        this.maxNumOfElements = maxNumOfElements;
        if (maxLenOfBytes > MAX_LEN_BYTES) {
            throw new IllegalArgumentException("Max num of bytes per element too high");
        }
        this.maxLenOfBytes = maxLenOfBytes;
    }

    public int getMaxNumOfElements() {
        return maxNumOfElements;
    }

    public int getMaxLenOfBytes() {
        return maxLenOfBytes;
    }

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
        if (SetContains.REF.equals(comparator)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(PrimSet.class)) {
            final PrimSet ps = (PrimSet) dataSetter;
            if (ps.getValue() instanceof Set<?>) {
                final Set<?> set = (Set<?>) ps.getValue();
                if (set.size() > this.maxNumOfElements) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (dataSetter.getClass().equals(SetAdd.class)) {
            final SetAdd setAdd = (SetAdd) dataSetter;
            if (setAdd.getData().length < this.maxLenOfBytes) {
                return true;
            } else {
                return false;
            }
        }
        if (dataSetter.getClass().equals(SetRemove.class)) {
            final SetRemove setRemove = (SetRemove) dataSetter;
            if (setRemove.getData().length < this.maxLenOfBytes) {
                return true;
            } else {
                return false;
            }
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
        if (dataSetter instanceof PrimSet) {
            final PrimSet ds = (PrimSet) dataSetter;
            final Collection<?> value = (Collection<?>) ds.getValue();
            final Set<ByteContainer> bcSet = new HashSet<ByteContainer>();
            for (final Object entryObj : value) {
                final ByteContainer byteContainer;
                if (entryObj instanceof ByteContainer) {
                    byteContainer = (ByteContainer) entryObj;
                } else {
                    byteContainer = new ByteContainer((byte[]) entryObj);
                }
                if (byteContainer.getNumOfBytes() > this.maxLenOfBytes) {
                    throw new IllegalArgumentException(
                            "Element has more bytes than allowed by this type");
                }
                byteContainer.seal();
                bcSet.add(byteContainer);
            }
            return bcSet;
        }

        if (dataSetter instanceof SetAdd) {
            final SetAdd setAdd = (SetAdd) dataSetter;
            final Set<ByteContainer> data;
            if (currentValue != null) {
                data = (Set<ByteContainer>) currentValue;
            } else {
                data = new HashSet<>();
            }
            if (data.size() >= this.maxNumOfElements) {
                throw new IllegalArgumentException("Too many elements in set");
            }
            data.add((new ByteContainer(setAdd.getData()).seal()));
            return data;
        }

        if (dataSetter instanceof SetRemove) {
            final SetRemove setRemove = (SetRemove) dataSetter;
            if (currentValue == null) {
                return null;
            }
            final Set<ByteContainer> data = (Set<ByteContainer>) currentValue;
            data.remove(new ByteContainer(setRemove.getData()));
            return data;
        }
        throw new IllegalArgumentException("Unsupported data setter");
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        final Set<?> valueAsSet = (Set<?>) value;
        final Set<ByteContainer> newSet = new HashSet<>();
        newSet.addAll((Set<ByteContainer>) valueAsSet);
        return newSet;
    }
}
