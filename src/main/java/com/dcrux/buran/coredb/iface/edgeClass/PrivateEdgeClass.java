package com.dcrux.buran.coredb.iface.edgeClass;

import com.dcrux.buran.coredb.iface.EdgeLabel;
import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class PrivateEdgeClass extends EdgeClass {

    /**
     *
     */
    private static final long serialVersionUID = 513810653669102016L;

    public PrivateEdgeClass(EdgeLabel label, boolean queryable, Optional<ClassId> inEdgeClass,
            PrivateEdgeConstraints outNodeConstraints) {
        super(label, queryable, inEdgeClass);
        assert (!label.isPublic());
        this.outNodeConstraints = outNodeConstraints;
    }

    public static PrivateEdgeClass cQueryableMany(EdgeLabel label, ClassId inEdgeClass) {
        return new PrivateEdgeClass(label, true, Optional.of(inEdgeClass),
                PrivateEdgeConstraints.many);
    }

    public static PrivateEdgeClass cQueryableMany(EdgeLabel label) {
        return new PrivateEdgeClass(label, true, Optional.<ClassId>absent(),
                PrivateEdgeConstraints.many);
    }

    public static PrivateEdgeClass cQueryableOne(EdgeLabel label, ClassId inEdgeClass) {
        return new PrivateEdgeClass(label, true, Optional.of(inEdgeClass),
                PrivateEdgeConstraints.oneOrNone);
    }

    private final PrivateEdgeConstraints outNodeConstraints;

    public PrivateEdgeConstraints getOutNodeConstraints() {
        return outNodeConstraints;
    }
}
