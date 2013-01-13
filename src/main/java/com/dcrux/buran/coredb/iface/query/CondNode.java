package com.dcrux.buran.coredb.iface.query;

import com.dcrux.buran.coredb.iface.query.nodeMeta.INodeMetaCondition;
import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class CondNode implements ICondNode {
    private final Optional<INodeMetaCondition> metaCondition;

    public CondNode(Optional<INodeMetaCondition> metaCondition) {
        this.metaCondition = metaCondition;
    }

    public Optional<INodeMetaCondition> getMetaCondition() {
        return metaCondition;
    }
}
