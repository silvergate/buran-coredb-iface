package com.dcrux.buran.coredb.iface;

import java.io.Serializable;

/**
 * Buran.
 *
 * @author: ${USER} Date: 19.01.13 Time: 11:34
 */
public class Nid implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6068118424266616489L;
    private final long nid;

    public Nid(long nid) {
        this.nid = nid;
    }

    public long getNid() {
        return nid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nid nid1 = (Nid) o;

        if (nid != nid1.nid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (nid ^ (nid >>> 32));
    }
}
