package com.dcrux.buran.coredb.iface.query;

import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class QueryCdNode implements IQuery {
    private final CondCdNode condition;
    private final Optional<ISorting> sorting;
    private final SkipLimit skipLimit;

    public QueryCdNode(CondCdNode condition, Optional<ISorting> sorting, SkipLimit skipLimit) {
        this.condition = condition;
        this.sorting = sorting;
        this.skipLimit = skipLimit;
    }

    public static QueryCdNode c(CondCdNode condition) {
        return new QueryCdNode(condition, Optional.<ISorting>absent(),
                QueryNode.DEFAULT_SKIP_LIMIT);
    }

    public static QueryCdNode c(CondCdNode condition, SkipLimit skipLimit) {
        return new QueryCdNode(condition, Optional.<ISorting>absent(), skipLimit);
    }

    public static QueryCdNode cSorted(CondCdNode condition, ISorting sort) {
        return new QueryCdNode(condition, Optional.<ISorting>of(sort),
                QueryNode.DEFAULT_SKIP_LIMIT);
    }

    public CondCdNode getCondition() {
        return condition;
    }

    public Optional<ISorting> getSorting() {
        return sorting;
    }

    public SkipLimit getSkipLimit() {
        return skipLimit;
    }
}
