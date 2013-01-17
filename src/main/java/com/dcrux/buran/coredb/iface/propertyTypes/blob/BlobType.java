package com.dcrux.buran.coredb.iface.propertyTypes.blob;

import com.dcrux.buran.coredb.iface.api.exceptions.ExpectableException;
import com.dcrux.buran.coredb.iface.nodeClass.*;
import org.apache.commons.lang.NotImplementedException;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;

/**
 * Buran.
 *
 * @author: ${USER} Date: 15.01.13 Time: 21:09
 */
public class BlobType implements IType {

    public static final TypeRef REF = new TypeRef((short) 233);

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
        if (comparator.getId() == LengthEq.REF.getId()) return true;
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
        final BlobSet setter = (BlobSet) dataSetter;
        final BlobData data;
        if (currentValue != null) data = (BlobData) currentValue;
        else data = new BlobData(new ByteArrayOutputStream());

        /* Set data */
        final int pos = setter.getPos();
        if (pos > data.getLength()) throw new ExpectableException(
                "Position in data setter is higher than latest byte in " +
                        "blob. A blob does not support gaps.");
        if (!setter.isAllowOverwrite()) {
            if ((pos < data.getLength()) && (setter.getData().getNumOfBytes() > 0)) {
                throw new ExpectableException("The given position lies inside existing data. The " +
                        "command would overwrite existing data. Aborting.");
            }
        }

        if (true) throw new NotImplementedException("TODO: Kommt noch");

        //data.getData().write(setter.getData().getData(), );

        return data;
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
