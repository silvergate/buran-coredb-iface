package com.dcrux.buran.coredb.iface.propertyTypes.set;

import com.dcrux.buran.coredb.iface.nodeClass.IDataSetter;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 18:45
 */
public class SetRemove implements IDataSetter {
    private final byte[] data;

    public byte[] getData() {
        return data;
    }

    public SetRemove(byte[] data) {
        if ((data == null) || (data.length > SetType.MAX_LEN_BYTES)) {
            throw new IllegalArgumentException("Invalid length or null");
        }
        this.data = data;
    }
}
