package com.dcrux.buran.coredb.iface.propertyTypes;

import com.dcrux.buran.coredb.iface.nodeClass.SorterRef;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 13:15
 */
public class SorterRefs {
    /**
     * Natural sort, missing values come first (missing values are low values).
     */
    public static final SorterRef NATURAL_NL = SorterRef.c(1);
    /**
     * An alias for {@link #NATURAL_NL}.
     */
    public static final SorterRef NATURAL = NATURAL_NL;
    /**
     * Natural sort, missing values come last (missing values are high values).
     */
    public static final SorterRef NATURAL_NH = SorterRef.c(2);
}
