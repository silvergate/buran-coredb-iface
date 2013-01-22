package com.dcrux.buran.coredb.iface.api.exceptions;

/**
 * @author caelis
 */
public class InformationUnavailableException extends UnexpectableException {
    /**
     *
     */
    private static final long serialVersionUID = -2612356278550707055L;
    private final UnavailabilityReason tempUnavailable;

    public InformationUnavailableException(UnavailabilityReason tempUnavailable) {
        this.tempUnavailable = tempUnavailable;
    }

    public UnavailabilityReason isTempUnavailable() {
        return tempUnavailable;
    }
}
