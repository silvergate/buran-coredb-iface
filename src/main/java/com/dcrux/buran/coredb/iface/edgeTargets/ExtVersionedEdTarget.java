package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.UserId;
import com.dcrux.buran.coredb.iface.node.NidVer;

/**
 * @author caelis
 */
public class ExtVersionedEdTarget implements IIncEdgeTarget, IEdgeTarget {

    private final long nid;
    private final int version;
    private final long userId;

    @Override
    public EdgeTargetType getEdgeTargetType() {
        return EdgeTargetType.externalVersioned;
    }

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.externalUnversioned;
    }

    public ExtVersionedEdTarget(long userId, long nid, int version) {
        this.nid = nid;
        this.version = version;
        this.userId = userId;
    }

    public long getNid() {
        return nid;
    }

    public NidVer getNidVer() {
        return new NidVer(getNid(), getVersion());
    }

    public UserId getUserIdx() {
        // TODO: Rename to userId
        return UserId.c(this.userId);
    }

    public int getVersion() {
        return version;
    }

    public long getUserId() {
        return userId;
    }
}
