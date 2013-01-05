package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:10
 */
public interface ITokenizer {
    void tokenize(String input, Set<String> result);
}
