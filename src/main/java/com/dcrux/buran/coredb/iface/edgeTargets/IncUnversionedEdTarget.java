package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.node.IncNid;

/**
 * @author caelis
 */
public class IncUnversionedEdTarget implements IIncEdgeTarget {

    //TODO: Die Incs-targets kommen vermutlich in die implementierung

    private final long inid;

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.unversionedInc;
    }

    IncUnversionedEdTarget(long inid) {
        this.inid = inid;
    }

    public static IncUnversionedEdTarget c(IncNid incNid) {
        return new IncUnversionedEdTarget(incNid.getId());
    }

    public long getInid() {
        return inid;
    }
}
