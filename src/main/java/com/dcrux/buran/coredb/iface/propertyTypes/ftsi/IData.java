package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:53
 */
public interface IData {
    void addToData(Set<String> tokens, IndexData indexData);

    boolean matches(String token, IndexData indexData, Fuzziness fuziness);

    boolean matches(Set<String> tokens, IndexData indexData, Fuzziness fuziness);
}
