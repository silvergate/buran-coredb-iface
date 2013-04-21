package com.dcrux.buran.coredb.iface.nodeClass;

import com.dcrux.buran.coredb.iface.edgeClass.EdgeClass;

/**
 * @author caelis
 */
public interface IBuilder {
    IBuilder add(String name, boolean required, IType type);

    IBuilder addEdgeClass(EdgeClass edgeClass);

    NodeClass get();
}
