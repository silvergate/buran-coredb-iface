package com.dcrux.buran.coredb.iface.edgeClass;

import com.dcrux.buran.coredb.iface.edge.EdgeLabelIndex;
import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.google.common.base.Optional;

import java.io.Serializable;

/**
 * @author caelis
 */
public class EdgeClass implements Serializable {
    /**
     *
     */


    private static final long serialVersionUID = -1150929005988155511L;
    private final short labelIndex;
    private final boolean queryable;
    private final Optional<ClassId> inEdgeClass;
    private final PrivateEdgeConstraints outNodeConstraints;

    protected EdgeClass(short labelIndex, boolean queryable, Optional<ClassId> inEdgeClass,
            PrivateEdgeConstraints outNodeConstraints) {
        this.labelIndex = labelIndex;
        this.queryable = queryable;
        this.inEdgeClass = inEdgeClass;
        this.outNodeConstraints = outNodeConstraints;
    }

    public static EdgeClass cQueryableMany(EdgeLabelIndex labelIndex, ClassId inEdgeClass) {
        return new EdgeClass(labelIndex.getLabelIndex(), true, Optional.of(inEdgeClass),
                PrivateEdgeConstraints.many);
    }

    public static EdgeClass cQueryableMany(EdgeLabelIndex labelIndex) {
        return new EdgeClass(labelIndex.getLabelIndex(), true, Optional.<ClassId>absent(),
                PrivateEdgeConstraints.many);
    }

    public static EdgeClass cQueryableOne(EdgeLabelIndex labelIndex, ClassId inEdgeClass) {
        return new EdgeClass(labelIndex.getLabelIndex(), true, Optional.of(inEdgeClass),
                PrivateEdgeConstraints.oneOrNone);
    }

    public PrivateEdgeConstraints getOutNodeConstraints() {
        return outNodeConstraints;
    }

    public boolean isQueryable() {
        return queryable;
    }

    public short getLabelIndex() {
        return labelIndex;
    }

    public Optional<ClassId> getInEdgeClass() {
        return inEdgeClass;
    }
}
