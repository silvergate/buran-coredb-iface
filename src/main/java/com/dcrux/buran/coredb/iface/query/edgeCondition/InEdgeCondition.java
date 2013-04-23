package com.dcrux.buran.coredb.iface.query.edgeCondition;

import com.dcrux.buran.coredb.iface.NidVer;
import com.dcrux.buran.coredb.iface.edge.EdgeIndex;
import com.dcrux.buran.coredb.iface.edge.EdgeLabel;
import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.dcrux.buran.coredb.iface.query.ICondNode;
import com.dcrux.buran.coredb.iface.query.nodeMeta.IMetaInfoForQuery;
import com.dcrux.buran.coredb.iface.query.nodeMeta.INodeMetaCondition;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: caelis Date: 13.12.12 Time: 22:56 To change this template use
 * File | Settings | File Templates.
 */
public class InEdgeCondition implements INodeMetaCondition {

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
        final Multimap<EdgeIndex, NidVer> queryableInEdges =
                metaInfoForQuery.getQueryableInEdges(this.label);

        /* Label available? */
        if (queryableInEdges.isEmpty()) {
            return false;
        }

        final Multimap<EdgeIndex, NidVer> outEdgesToQuery;
        if (this.index.isPresent()) {
            outEdgesToQuery = HashMultimap.create();
            if (!queryableInEdges.containsKey(this.index.get())) {
                return false;
            }
            outEdgesToQuery.putAll(this.index.get(), queryableInEdges.get(this.index.get()));
        } else {
            outEdgesToQuery = queryableInEdges;
        }

        for (Map.Entry<EdgeIndex, NidVer> elementToCheck : outEdgesToQuery.entries()) {
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

    private boolean matchesClass(NidVer edgeSource, ClassId classId,
            IMetaInfoForQuery metaInfoForQuery) {
        return metaInfoForQuery.getNodeMatcher().matches(edgeSource.getNid(), classId);
    }


    private boolean matches(NidVer edgeSource, IMetaInfoForQuery metaInfoForQuery) {
        boolean found;
        final Integer version;
        final Long oid;

        found = true;
        oid = edgeSource.getNid();
        version = edgeSource.getVersion();

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
