package com.dcrux.buran.coredb.iface.api;

import com.dcrux.buran.coredb.iface.NidVer;
import com.google.common.base.Optional;

import java.util.List;

/**
 * Buran.
 *
 * @author: ${USER} Date: 13.01.13 Time: 13:52
 */
public class QueryResult {

    public QueryResult(List<NidVer> nodes, boolean hasMoreResults,
            Optional<Integer> numberOfResultsWithoutLimit) {
        this.nodes = nodes;
        this.hasMoreResults = hasMoreResults;
        this.numberOfResultsWithoutLimit = numberOfResultsWithoutLimit;
    }

    private final List<NidVer> nodes;
    private final boolean hasMoreResults;
    private final Optional<Integer> numberOfResultsWithoutLimit;

    public List<NidVer> getNodes() {
        return nodes;
    }

    public boolean isHasMoreResults() {
        return hasMoreResults;
    }

    public Optional<Integer> getNumberOfResultsWithoutLimit() {
        return numberOfResultsWithoutLimit;
    }
}
