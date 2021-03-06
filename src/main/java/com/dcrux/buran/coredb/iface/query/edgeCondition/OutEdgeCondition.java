package com.dcrux.buran.coredb.iface.query.edgeCondition;

import com.dcrux.buran.coredb.iface.NidVer;
import com.dcrux.buran.coredb.iface.api.exceptions.ExpectableException;
import com.dcrux.buran.coredb.iface.edge.Edge;
import com.dcrux.buran.coredb.iface.edge.EdgeIndex;
import com.dcrux.buran.coredb.iface.edge.EdgeLabel;
import com.dcrux.buran.coredb.iface.edgeTargets.IEdgeTarget;
import com.dcrux.buran.coredb.iface.edgeTargets.UnversionedEdTarget;
import com.dcrux.buran.coredb.iface.edgeTargets.VersionedEdTarget;
import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.dcrux.buran.coredb.iface.query.ICondNode;
import com.dcrux.buran.coredb.iface.query.nodeMeta.IMetaInfoForQuery;
import com.dcrux.buran.coredb.iface.query.nodeMeta.INodeMetaCondition;
import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caelis
 */
public class OutEdgeCondition implements INodeMetaCondition {

    private final EdgeLabel label;
    private final Optional<EdgeIndex> index;
    private final Optional<ICondNode> target;
    private final boolean matchAll;
    private final Optional<ClassId> sourceClassId;

    public static OutEdgeCondition c(EdgeLabel label) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ICondNode>absent(), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition c(EdgeLabel label, EdgeIndex edgeIndex) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>of(edgeIndex),
                Optional.<ICondNode>absent(), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition c(ClassId sourceClassId, EdgeLabel label, EdgeIndex edgeIndex,
            ICondNode target) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>of(edgeIndex),
                Optional.<ICondNode>of(target), Optional.<ClassId>of(sourceClassId), false);
    }

    public static OutEdgeCondition c(EdgeLabel label, EdgeIndex edgeIndex, ICondNode target) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>of(edgeIndex),
                Optional.<ICondNode>of(target), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition all(EdgeLabel label, ICondNode target) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ICondNode>of(target), Optional.<ClassId>absent(), true);
    }

    private OutEdgeCondition(EdgeLabel label, Optional<EdgeIndex> index, Optional<ICondNode> target,
            Optional<ClassId> sourceClassId, boolean matchAll) {
        if ((!label.isPublic()) && (!sourceClassId.isPresent())) {
            throw new IllegalArgumentException(
                    "Need a source-class-id if a private label is " + "given.");
        }
        this.sourceClassId = sourceClassId;
        this.label = label;
        this.index = index;
        this.target = target;
        this.matchAll = matchAll;
        if (!target.isPresent() && matchAll) {
            throw new ExpectableException("If target is missing you cannot set matchAll to true.");
        }
        if (index.isPresent() && matchAll) {
            throw new ExpectableException("If index is present you cannot set matchAll to true.");
        }
    }

    public static OutEdgeCondition hasAnyEdge(EdgeLabel label) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ICondNode>absent(), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition hasEdge(EdgeLabel label, EdgeIndex index) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>of(index),
                Optional.<ICondNode>absent(), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition hasAnyEdge(EdgeLabel label, ICondNode targetNode) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ICondNode>of(targetNode), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition hasEdge(EdgeLabel label, EdgeIndex index, ICondNode targetNode) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>of(index),
                Optional.<ICondNode>of(targetNode), Optional.<ClassId>absent(), false);
    }

    public static OutEdgeCondition hasEdgeAll(EdgeLabel label, ICondNode targetNode) {
        return new OutEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ICondNode>of(targetNode), Optional.<ClassId>absent(), true);
    }

    public EdgeLabel getLabel() {
        return label;
    }

    public Optional<EdgeIndex> getIndex() {
        return index;
    }

    public Optional<ICondNode> getTarget() {
        return target;
    }

    public boolean isMatchAll() {
        return matchAll;
    }

    @Override
    public boolean matches(IMetaInfoForQuery metaInfoForQuery) {
        final Map<EdgeIndex, Edge> queryableOutEdges =
                metaInfoForQuery.getQueryableOutEdges(this.label);

        /* Label available? */
        if (queryableOutEdges.isEmpty()) {
            return false;
        }

        final Map<EdgeIndex, Edge> outEdgesToQuery;
        if (this.index.isPresent()) {
            outEdgesToQuery = new HashMap<>();
            if (!queryableOutEdges.containsKey(this.index.get())) {
                return false;
            }
            outEdgesToQuery.put(this.index.get(), queryableOutEdges.get(this.index.get()));
        } else {
            outEdgesToQuery = queryableOutEdges;
        }

        if (this.target.isPresent()) {
            for (Map.Entry<EdgeIndex, Edge> elementToCheck : outEdgesToQuery.entrySet()) {
                boolean matches = matches(elementToCheck.getValue(), metaInfoForQuery);
                if (!matches && isMatchAll()) {
                    return false;
                }
                if (matches && (!isMatchAll())) {
                    return true;
                }
            }
            return isMatchAll();
        } else {
            return true;
        }
    }

    private boolean matches(Edge edge, IMetaInfoForQuery metaInfoForQuery) {
        final IEdgeTarget edgeTarget = edge.getTarget();
        boolean found = false;
        final Integer version;
        final Long oid;
        switch (edgeTarget.getEdgeTargetType()) {
            case externalUnversioned:
          /* Externe können nie durchsucht werden */
                found = false;
                oid = null;
                version = null;
                break;
            case externalVersioned:
          /* Externe können nie durchsucht werden */
                found = false;
                oid = null;
                version = null;
                break;
            case unversioned:
                oid = ((UnversionedEdTarget) edgeTarget).getNid();
                version = null;
                break;
            case versioned:
                oid = ((VersionedEdTarget) edgeTarget).getNid();
                version = ((VersionedEdTarget) edgeTarget).getVersion();
                break;
            default:
                throw new ExpectableException("Unknown edge target");
        }
        if (oid != null) {
            if (version == null) {
                found = metaInfoForQuery.getNodeMatcher().matches(oid, this.target.get());
            } else {
                found = metaInfoForQuery.getNodeMatcher()
                        .matchesVersion(new NidVer(oid, version), this.target.get());
            }
        }
        return found;
    }
}
