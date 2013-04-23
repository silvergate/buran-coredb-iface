package com.dcrux.buran.coredb.iface.api;

import com.dcrux.buran.coredb.iface.edge.EdgeIndex;

import java.io.Serializable;

/**
 * Buran.
 *
 * @author: ${USER} Date: 18.04.13 Time: 00:23
 */
public final class EdgeIndexRange implements Serializable {

    private final EdgeIndex first;
    private final EdgeIndex last;

    public EdgeIndexRange(EdgeIndex first, EdgeIndex last) {
        if (first.getId() > last.getId()) {
            throw new IllegalArgumentException("first.getId()>last.getId()");
        }
        this.first = first;
        this.last = last;
    }

    public static EdgeIndexRange c(EdgeIndex first, EdgeIndex last) {
        return new EdgeIndexRange(first, last);
    }

    public static EdgeIndexRange c(int first, int last) {
        return new EdgeIndexRange(EdgeIndex.c(first), EdgeIndex.c(last));
    }

    public static EdgeIndexRange single(EdgeIndex index) {
        return new EdgeIndexRange(index, index);
    }

    public EdgeIndex getFirst() {
        return first;
    }

    public EdgeIndex getLast() {
        return last;
    }

    public boolean contains(EdgeIndex index) {
        return (index.getId() >= this.first.getId()) && (index.getId() <= this.last.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeIndexRange that = (EdgeIndexRange) o;

        if (!first.equals(that.first)) return false;
        if (!last.equals(that.last)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + last.hashCode();
        return result;
    }
}
