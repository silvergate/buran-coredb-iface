package com.dcrux.buran.coredb.iface.nodeClass;

import javax.annotation.Nullable;

/**
 * @author caelis
 */
public interface ICmp {
    CmpRef getRef();

    boolean matches(@Nullable Object lhs);
}
