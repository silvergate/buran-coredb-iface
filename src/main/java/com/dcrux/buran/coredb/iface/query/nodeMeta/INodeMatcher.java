package com.dcrux.buran.coredb.iface.query.nodeMeta;

import com.dcrux.buran.coredb.iface.node.NidVer;
import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.dcrux.buran.coredb.iface.query.ICondNode;

/**
 * @author caelis
 */
public interface INodeMatcher {
    boolean matchesVersion(NidVer nidVer, ICondNode qNode);

    boolean matches(long oid, ICondNode qNode);

    boolean matches(long oid, ClassId classId);
}
