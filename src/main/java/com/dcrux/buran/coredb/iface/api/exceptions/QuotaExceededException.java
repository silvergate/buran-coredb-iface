package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * Buran.
 *
 * @author: ${USER} Date: 23.01.13 Time: 22:22
 */
public class QuotaExceededException extends Exception {

    /**
     * TODO: Das hier noch genauer definieren.
     */
    public enum DetailInformation {
        cpuExceeded,
        indexSizeExceeded,
        storageExceeded
    }

}

