package com.dcrux.buran.coredb.iface.query.edgeCondition;

import com.dcrux.buran.coredb.iface.Nid;
import com.dcrux.buran.coredb.iface.NidVer;
import com.dcrux.buran.coredb.iface.api.exceptions.ExpectableException;
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
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: caelis Date: 13.12.12 Time: 22:56 To change this template use
 * File | Settings | File Templates.
 */
public class InEdgeCondition implements INodeMetaCondition {

    private static class NidVerOpt {
        private final Nid nid;
        @Nullable
        private final Integer ver;

        private NidVerOpt(Nid nid, @Nullable Integer ver) {
            this.nid = nid;
            this.ver = ver;
        }
    }

    private final EdgeLabel label;
    private final Optional<EdgeIndex> index;
    private final Optional<ICondNode> source;
    private final Optional<ClassId> sourceClassId;

    private InEdgeCondition(EdgeLabel label, Optional<EdgeIndex> index,
            Optional<ClassId> sourceClassId, Optional<ICondNode> source) {
        this.label = label;
        if ((!this.label.isPublic()) && (!sourceClassId.isPresent())) {
            throw new IllegalArgumentException(
                    "Querying for private edge is only supported if " + "class id is given.");
        }
        this.sourceClassId = sourceClassId;
        this.index = index;
        this.source = source;
    }

    public static InEdgeCondition hasAnyPrivateEdge(ClassId classId, EdgeLabel label) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ClassId>of(classId), Optional.<ICondNode>absent());
    }

    public static InEdgeCondition hasAnyPrivateEdge(ClassId classId, EdgeLabel label,
            ICondNode targetNode) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>absent(),
                Optional.<ClassId>of(classId), Optional.<ICondNode>of(targetNode));
    }

    public static InEdgeCondition hasAnyEdge(EdgeLabel label) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>absent(), Optional.<ClassId>absent(),
                Optional.<ICondNode>absent());
    }

    public static InEdgeCondition hasEdge(EdgeLabel label, EdgeIndex index) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>of(index), Optional.<ClassId>absent(),
                Optional.<ICondNode>absent());
    }

    public static InEdgeCondition hasAnyEdge(EdgeLabel label, ICondNode targetNode) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>absent(), Optional.<ClassId>absent(),
                Optional.<ICondNode>of(targetNode));
    }

    public static InEdgeCondition hasEdge(EdgeLabel label, EdgeIndex index, ICondNode targetNode) {
        return new InEdgeCondition(label, Optional.<EdgeIndex>of(index), Optional.<ClassId>absent(),
                Optional.<ICondNode>of(targetNode));
    }

    public EdgeLabel getLabel() {
        return label;
    }

    public Optional<EdgeIndex> getIndex() {
        return index;
    }

    public Optional<ICondNode> getSource() {
        return this.source;
    }

    @Override
    public boolean matches(IMetaInfoForQuery metaInfoForQuery) {
        final Multimap<EdgeIndex, IEdgeTarget> queryableInEdges =
                metaInfoForQuery.getQueryableInEdges(this.label);

        /* Label available? */
        if (queryableInEdges.isEmpty()) {
            return false;
        }

        final Multimap<EdgeIndex, IEdgeTarget> outEdgesToQuery;
        if (this.index.isPresent()) {
            outEdgesToQuery = HashMultimap.create();
            if (!queryableInEdges.containsKey(this.index.get())) {
                return false;
            }
            outEdgesToQuery.putAll(this.index.get(), queryableInEdges.get(this.index.get()));
        } else {
            outEdgesToQuery = queryableInEdges;
        }

        for (Map.Entry<EdgeIndex, IEdgeTarget> elementToCheck : outEdgesToQuery.entries()) {
            boolean matchesData = true;
            boolean matchesClass = true;

            if (this.source.isPresent()) {
                matchesData = matches(elementToCheck.getValue(), metaInfoForQuery);
            }
            if (this.sourceClassId.isPresent()) {
                matchesClass = matchesClass(elementToCheck.getValue(), this.sourceClassId.get(),
                        metaInfoForQuery);
            }
            if (matchesData && matchesClass) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private NidVerOpt getNidVerOptFromTarget(IEdgeTarget edgeTarget) {
        final Integer version;
        final Long oid;
        switch (edgeTarget.getEdgeTargetType()) {
            case externalUnversioned:
          /* Externe können nie durchsucht werden */
                oid = null;
                version = null;
                break;
            case externalVersioned:
          /* Externe können nie durchsucht werden */
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
        if (oid == null) return null;
        return new NidVerOpt(new Nid(oid), version);
    }

    private boolean matchesClass(IEdgeTarget edgeTarget, ClassId classId,
            IMetaInfoForQuery metaInfoForQuery) {
        NidVerOpt nidVerOpt = getNidVerOptFromTarget(edgeTarget);
        if (nidVerOpt == null) return false;
        metaInfoForQuery.getNodeMatcher().matches(nidVerOpt.nid.getNid(), classId);
        return true;
    }


    private boolean matches(IEdgeTarget edgeTarget, IMetaInfoForQuery metaInfoForQuery) {
        boolean found;
        final Integer version;
        final Long oid;

        NidVerOpt nidVerOpt = getNidVerOptFromTarget(edgeTarget);
        if (nidVerOpt == null) {
            found = false;
            oid = null;
            version = null;
        } else {
            found = true;
            oid = nidVerOpt.nid.getNid();
            version = nidVerOpt.ver;
        }

        if (oid != null) {
            if (version == null) {
                found = metaInfoForQuery.getNodeMatcher().matches(oid, this.source.get());
            } else {
                found = metaInfoForQuery.getNodeMatcher()
                        .matchesVersion(new NidVer(oid, version), this.source.get());
            }
        }
        return found;
    }
}
