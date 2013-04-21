package com.dcrux.buran.coredb.iface.propertyTypes;

import com.dcrux.buran.coredb.iface.ByteContainer;
import com.dcrux.buran.coredb.iface.nodeClass.IDataGetter;

import java.util.Set;

/**
 * @author caelis
 */
public class PrimGet<TData> implements IDataGetter<TData> {
    public static final IDataGetter<Object> ANY = new PrimGet<>();
    public static final IDataGetter<byte[]> BINARY = new PrimGet<>();
    public static final IDataGetter<Boolean> BOOL = new PrimGet<>();
    public static final IDataGetter<Integer> INTEGER = new PrimGet<>();
    public static final IDataGetter<Double> LONG_FLOAT = new PrimGet<>();
    public static final IDataGetter<Long> LONG_INT = new PrimGet<>();
    public static final IDataGetter<Set<ByteContainer>> SET = new PrimGet<>();
    public static final IDataGetter<String> STRING = new PrimGet<>();
}
