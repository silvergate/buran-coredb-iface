package com.dcrux.buran.coredb.iface.query.nodeMeta;

import com.dcrux.buran.coredb.iface.edge.Edge;
import com.dcrux.buran.coredb.iface.edge.EdgeIndex;
import com.dcrux.buran.coredb.iface.edge.EdgeLabel;
import com.dcrux.buran.coredb.iface.edgeTargets.IEdgeTarget;
import com.dcrux.buran.coredb.iface.nodeClass.NodeClass;
import com.dcrux.buran.coredb.iface.permissions.UserNodePermission;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.Set;

/**
 * @author caelis
 */
public interface IMetaInfoForQuery {

    public enum EdgeType {
        out,
        in
    }

    long getClassId();

    int getVersion();

    long getValidFrom();

    long getValidTo();

    long getReceiver();

    long getSender();

    UserNodePermission getOwnPermissions();

    Map<Long, UserNodePermission> getOtherPermissions();

    Set<Long> getDomainIds();

    NodeClass getNodeClass();

    /* Edges */
    Map<EdgeIndex, Edge> getQueryableOutEdges(EdgeLabel label);

    Multimap<EdgeIndex, IEdgeTarget> getQueryableInEdges(EdgeLabel label);

    INodeMatcher getNodeMatcher();
}
