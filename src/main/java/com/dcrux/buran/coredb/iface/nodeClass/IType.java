package com.dcrux.buran.coredb.iface.nodeClass;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @author caelis
 */
public interface IType extends Serializable {
    TypeRef getRef();

    /**
     * Returns the sorter implementation if the given sorter-ref is supported. Returns
     * <code>null</code> if the sorter-ref is not supported.
     *
     * @param sorterRef
     * @return Sorter if supported or <code>null</code> if not supported.
     */
    @Nullable
    ISorter getSorter(SorterRef sorterRef);

    boolean supports(CmpRef comparator);

    boolean supports(IDataSetter dataSetter);

    boolean supports(IDataGetter dataGetter);

    /**
     * Sets data.  The implementation is allowed to throw an exception if not data setter is not
     * supported (see {@link #supports(IDataSetter)}.
     *
     * @param dataSetter
     * @param currentValue
     *         The current value. The implementation is allowed to modify the reference target.
     * @return The new value (might be the same reference as currentValue).
     */
    @Nullable
    Object setData(IDataSetter dataSetter, @Nullable Object currentValue);

    /**
     * Gets data. The implementation is allowed to throw an exception if not data getter is not
     * supported (see {@link #supports(IDataGetter)}.
     *
     * @param dataGetter
     * @param value
     *         The implementation MUST NOT modify the value.
     * @return The data to return (might be the same reference as value).
     */
    @Nullable
    Object getData(IDataGetter dataGetter, @Nullable Object value);
}
