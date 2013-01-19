package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.Nid;

/**
 * @author caelis
 */
public class UnversionedEdTarget implements IIncEdgeTarget, IEdgeTarget {

    private final long nid;

    @Override
    public EdgeTargetType getEdgeTargetType() {
        return EdgeTargetType.unversioned;
    }

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.unversioned;
    }

    public static UnversionedEdTarget c(Nid nid) {
        return new UnversionedEdTarget(nid.getNid());
    }

    public UnversionedEdTarget(long nid) {
        this.nid = nid;
    }

    public long getNid() {
        return nid;
    }
}
