package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.node.NidVer;

/**
 * @author caelis
 */
public class VersionedEdTarget implements IIncEdgeTarget, IEdgeTarget {

    private final long nid;
    private final int version;

    @Override
    public EdgeTargetType getEdgeTargetType() {
        return EdgeTargetType.versioned;
    }

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.versioned;
    }

    public static VersionedEdTarget c(NidVer nidVer) {
        return new VersionedEdTarget(nidVer.getNid(), nidVer.getVersion());
    }

    public VersionedEdTarget(long nid, int version) {
        this.nid = nid;
        this.version = version;
    }

    public NidVer getNidVer() {
        return new NidVer(getNid(), getVersion());
    }

    public long getNid() {
        return nid;
    }

    public int getVersion() {
        return version;
    }
}
