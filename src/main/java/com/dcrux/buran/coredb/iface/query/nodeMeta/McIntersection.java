package com.dcrux.buran.coredb.iface.query.nodeMeta;

/**
 * @author caelis
 */
public class McIntersection implements INodeMetaCondition {

    private final INodeMetaCondition val1;
    private final INodeMetaCondition val2;

    public McIntersection(INodeMetaCondition val1, INodeMetaCondition val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public static McIntersection c(INodeMetaCondition val1, INodeMetaCondition val2) {
        return new McIntersection(val1, val2);
    }

    public INodeMetaCondition getVal1() {
        return val1;
    }

    public INodeMetaCondition getVal2() {
        return val2;
    }

    @Override
    public boolean matches(IMetaInfoForQuery metaInfoForQuery) {
        final boolean c1 = this.val1.matches(metaInfoForQuery);
        if (!c1) {
            return false;
        }
        final boolean c2 = this.val2.matches(metaInfoForQuery);
        if (!c2) {
            return false;
        }
        return true;
    }
}
