package com.dcrux.buran.coredb.iface.query;

import com.dcrux.buran.coredb.iface.nodeClass.ClassId;
import com.dcrux.buran.coredb.iface.query.nodeMeta.INodeMetaCondition;
import com.dcrux.buran.coredb.iface.query.propertyCondition.IPropertyCondition;
import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class CondCdNode extends CondNode {
    private final long classId;
    private final Optional<IPropertyCondition> propertyCondition;


    public static CondCdNode c(ClassId classId, IPropertyCondition propertyCondition) {
        return new CondCdNode(Optional.<INodeMetaCondition>absent(), classId.getId(),
                Optional.<IPropertyCondition>of(propertyCondition));
    }

    public static CondCdNode c(ClassId classId, INodeMetaCondition metaCondition,
            IPropertyCondition propertyCondition) {
        return new CondCdNode(Optional.<INodeMetaCondition>of(metaCondition), classId.getId(),
                Optional.<IPropertyCondition>of(propertyCondition));
    }

    public static CondCdNode c(ClassId classId) {
        return new CondCdNode(Optional.<INodeMetaCondition>absent(), classId.getId(),
                Optional.<IPropertyCondition>absent());
    }

    public CondCdNode(Optional<INodeMetaCondition> metaCondition, long classId,
            Optional<IPropertyCondition> propertyCondition) {
        super(metaCondition);
        this.classId = classId;
        this.propertyCondition = propertyCondition;
    }

    public long getClassId() {
        return classId;
    }

    public Optional<IPropertyCondition> getPropertyCondition() {
        return propertyCondition;
    }
}
