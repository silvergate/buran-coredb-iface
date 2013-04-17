package com.dcrux.buran.coredb.iface.edge;

/**
 * Buran.
 *
 * @author: ${USER} Date: 17.04.13 Time: 23:24
 */
public class EdgeLabelIndex {
    private final short labelIndex;

    public EdgeLabelIndex(short labelIndex) {
        this.labelIndex = labelIndex;
    }

    public static EdgeLabelIndex fromString(final String name) {
        return new EdgeLabelIndex(EdgeLabel.toHash(name));
    }

    public short getLabelIndex() {
        return labelIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeLabelIndex that = (EdgeLabelIndex) o;

        if (labelIndex != that.labelIndex) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) labelIndex;
    }
}
