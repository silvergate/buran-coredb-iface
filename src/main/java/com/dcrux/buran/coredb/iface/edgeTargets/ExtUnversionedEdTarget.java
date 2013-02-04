package com.dcrux.buran.coredb.iface.edgeTargets;

import com.dcrux.buran.coredb.iface.Nid;
import com.dcrux.buran.coredb.iface.UserId;

/**
 * @author caelis
 */
public class ExtUnversionedEdTarget implements IIncEdgeTarget, IEdgeTarget {

    private final long nid;
    private final long userId;

    @Override
    public EdgeTargetType getEdgeTargetType() {
        return EdgeTargetType.externalVersioned;
    }

    @Override
    public IncEdgeTargetType getIncType() {
        return IncEdgeTargetType.externalUnversioned;
    }

    public ExtUnversionedEdTarget(long userId, long nid) {
        this.nid = nid;
        this.userId = userId;
    }

    public long getNid() {
        return nid;
    }

    public Nid getNidx() {
        // TODO: rename to nid
        return new Nid(this.nid);
    }

    public UserId getUserIdx() {
        //TODO: rename to userID
        return UserId.c(this.userId);
    }

    public long getUserId() {
        return userId;
    }
}
