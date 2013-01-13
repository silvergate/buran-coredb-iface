package com.dcrux.buran.coredb.iface.query;

/**
 * @author caelis
 */
public class SkipLimit {
    private final int skip;
    private final int limit;

    public SkipLimit(int skip, int limit) {
        this.skip = skip;
        this.limit = limit;
    }

    public static SkipLimit c(int skip, int limit) {
        return new SkipLimit(skip, limit);
    }

    public static SkipLimit limit(int limit) {
        return c(0, limit);
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkipLimit skipLimit = (SkipLimit) o;

        if (limit != skipLimit.limit) return false;
        if (skip != skipLimit.skip) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = skip;
        result = 31 * result + limit;
        return result;
    }
}
