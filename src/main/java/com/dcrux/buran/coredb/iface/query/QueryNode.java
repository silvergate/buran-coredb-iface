package com.dcrux.buran.coredb.iface.query;

import com.google.common.base.Optional;

/**
 * @author caelis
 */
public class QueryNode implements IQuery {
    private final CondNode condition;
    private final Optional<MetaSort> sorting;
    private final SkipLimit skipLimit;

    public static final SkipLimit DEFAULT_SKIP_LIMIT = SkipLimit.limit(2048);

    public QueryNode(CondNode condition, Optional<MetaSort> sorting, SkipLimit skipLimit) {
        this.condition = condition;
        this.sorting = sorting;
        this.skipLimit = skipLimit;
    }

    public static QueryNode c(CondNode condition) {
        return new QueryNode(condition, Optional.<MetaSort>absent(), DEFAULT_SKIP_LIMIT);
    }

    public static QueryNode cSorted(CondNode condition, MetaSort sort) {
        return new QueryNode(condition, Optional.<MetaSort>of(sort), DEFAULT_SKIP_LIMIT);
    }

    public CondNode getCondition() {
        return condition;
    }

    public Optional<MetaSort> getSorting() {
        return sorting;
    }

    public SkipLimit getSkipLimit() {
        return skipLimit;
    }
}
