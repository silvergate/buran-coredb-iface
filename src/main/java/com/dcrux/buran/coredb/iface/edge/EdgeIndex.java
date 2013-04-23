package com.dcrux.buran.coredb.iface.edge;

import java.io.Serializable;

/**
 * @author caelis
 */
public class EdgeIndex implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4131706503596091899L;

    public static final EdgeIndex BASE = new EdgeIndex(0);

    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 65535;

    private final int id;

    public EdgeIndex(int id) {
        assert (id >= MIN_INDEX);
        assert (id <= MAX_INDEX);
        this.id = id;
    }

    public static EdgeIndex c(int id) {
        return new EdgeIndex(id);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EdgeIndex edgeIndex = (EdgeIndex) o;

        if (id != edgeIndex.id) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
