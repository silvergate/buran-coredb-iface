package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:09
 */
public interface IStemmer {
    void stemm(Set<String> input, Set<String> output);

    void stemmDontBase(Set<String> input, Set<String> output);
}
