package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.IncNid;

/**
 * @author caelis
 */
public class IncVersionedEdTarget implements IIncEdgeTarget {

    private final long inid;

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.versionedInc;
    }

    public static IncVersionedEdTarget c(IncNid incNid) {
        return new IncVersionedEdTarget(incNid.getId());
    }

    public IncVersionedEdTarget(long inid) {
        this.inid = inid;
    }

    public long getInid() {
        return inid;
    }
}
