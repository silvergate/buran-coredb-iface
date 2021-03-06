package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.api.exceptions.ExpectableException;
import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.propertyTypes.Exists;

import javax.annotation.Nullable;
import java.text.MessageFormat;

/**
 * Buran.
 *
 * @author: ${USER} Date: 15.01.13 Time: 21:09
 */
public class BlobType implements IType {

    /**
     *
     */
    private static final long serialVersionUID = -2757882164219422526L;
    public static final TypeRef REF = new TypeRef((short) 233);
    public static long MAX_LENGTH = Long.MAX_VALUE;

    private final boolean indexed;
    private final long maxLength;

    public static BlobType indexed(long maxLength) {
        return new BlobType(maxLength, true);
    }

    public BlobType(long maxLength, boolean indexed) {
        this.maxLength = maxLength;
        this.indexed = indexed;
    }

    @Override
    public TypeRef getRef() {
        return REF;
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        if (!this.indexed) return null;
        if (sorterRef.getRef() == NaturalLengthSort.REF.getRef())
            return NaturalLengthSort.SINGLETON;
        if (sorterRef.getRef() == NaturalLengthSort.REF_NH.getRef())
            return NaturalLengthSort.SINGLETON_NH;
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (!this.indexed) return false;
        if (comparator.getId() == LengthEq.REF.getId()) return true;
        if (comparator.getId() == Exists.REF.getId()) return true;
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter instanceof BlobSet) return true;
        return false;
    }

    @Override
    public boolean supports(IDataGetter dataGetter) {
        if (dataGetter instanceof BlobGet) return true;
        if (dataGetter instanceof LengthGet) return true;
        return false;
    }

    @Nullable
    @Override
    public Object setData(IDataSetter dataSetter, @Nullable Object currentValue) {
        if (dataSetter instanceof BlobSet) {
            final BlobSet setter = (BlobSet) dataSetter;
            final BlobData data;
            if (currentValue != null) data = (BlobData) currentValue;
            else data = new BlobData(new BinaryBlocks());

        /* Set data */
            final int pos = setter.getPos();
            if (pos > data.getLength()) throw new ExpectableException(
                    "Position in data setter is higher than latest byte in " +
                            "blob. A blob does not support gaps.");
            if (!setter.isAllowOverwrite()) {
                if ((pos < data.getLength()) && (setter.getData().getNumOfBytes() > 0)) {
                    throw new ExpectableException(
                            "The given position lies inside existing data. The " +
                                    "command would overwrite existing data. Aborting.");
                }
            }

        /* Check maximum length */
            final long currentLen = data.getLength();
            final long overlapping = data.getLength() - pos;
            final long newLen = currentLen + setter.getData().getNumOfBytes() - overlapping;
            if (newLen > this.maxLength) {
                throw new ExpectableException(MessageFormat
                        .format("This blob supports at max {0}. " +
                                "After the operation the blow would grow to {1} bytes.",
                                this.maxLength, newLen));
            }

            final boolean written =
                    data.getData().setData(setter.getPos(), setter.getData().getData(), true);
            if (!written) throw new IllegalStateException("Should not happen!");

            return data;
        }

        if (dataSetter instanceof BlobTruncate) {
            final BlobTruncate blobTruncate = (BlobTruncate) dataSetter;
            final BlobData data;
            if (currentValue != null) data = (BlobData) currentValue;
            else data = null;
            if (blobTruncate.getNumberOfBytes().isPresent() && (data == null)) {
                throw new ExpectableException("The blob property is missing. Cannot perform a " +
                        "truncate with defined length on a missing type");
            }

            if (data == null) {
                /* Do nothing */
                return null;
            }

            /* Truncate */
            data.getData().cropBytes(blobTruncate.getNumberOfBytes().get());
        }

        throw new ExpectableException("Unsupported data setter.");
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        final BlobData data;
        if (value != null) data = (BlobData) value;
        else data = null;
        if (dataGetter instanceof LengthGet) {
            final LengthGet lengthGet = (LengthGet) dataGetter;
            if (data == null) return 0;
            else return data.getLength();
        }

        if (dataGetter instanceof BlobGet) {
            final BlobGet blobGet = (BlobGet) dataGetter;
            if (data == null) throw new ExpectableException("Blob is empty");
            long toIndex = blobGet.getSkip() + blobGet.getLength() - 1;
            if (toIndex >= data.getLength()) {
                /* We need to shorten */
                if (blobGet.isStrictLength()) {
                    /* We're not allowed to shorten */
                    throw new ExpectableException(
                            "Not enough data available and shorten is not " + "allowed");
                } else {
                    toIndex = data.getLength() - 1;
                }
            }

            return data.getData().read(blobGet.getSkip(), toIndex);
        }

        throw new ExpectableException("Incompatible getter for this type");
    }
}
