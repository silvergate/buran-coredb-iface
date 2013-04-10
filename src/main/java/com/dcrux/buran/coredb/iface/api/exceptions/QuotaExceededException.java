package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * Buran.
 *
 * @author: ${USER} Date: 23.01.13 Time: 22:22
 */
public class QuotaExceededException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2067236354193086763L;

	/**
     * TODO: Das hier noch genauer definieren.
     */
    public enum DetailInformation {
        cpuExceeded,
        indexSizeExceeded,
        storageExceeded
    }

}

