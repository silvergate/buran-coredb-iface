package com.dcrux.buran.coredb.iface.query;

import com.dcrux.buran.coredb.iface.query.nodeMeta.INodeMetaCondition;
import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class CondNode implements ICondNode {
    /**
     *
     */
    private static final long serialVersionUID = -6018472210420101383L;
    private final Optional<INodeMetaCondition> metaCondition;

    public static CondNode c(INodeMetaCondition metaCondition) {
        return new CondNode(Optional.of(metaCondition));
    }

    public CondNode(Optional<INodeMetaCondition> metaCondition) {
        this.metaCondition = metaCondition;
    }

    public Optional<INodeMetaCondition> getMetaCondition() {
        return metaCondition;
    }
}
