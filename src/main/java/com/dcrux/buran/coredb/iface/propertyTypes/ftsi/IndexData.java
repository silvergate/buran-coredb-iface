package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:41
 */
public class IndexData implements Serializable {
    private final Set<Integer> highFuziness = new HashSet<>();
    private final Set<Integer> mediumFuziness = new HashSet<>();
    private final Set<Integer> exact = new HashSet<>();

    public Set<Integer> getHighFuziness() {
        return highFuziness;
    }

    public Set<Integer> getMediumFuziness() {
        return mediumFuziness;
    }

    public Set<Integer> getExact() {
        return exact;
    }

    public void clear() {
        this.highFuziness.clear();
        this.mediumFuziness.clear();
        this.exact.clear();
    }
}
