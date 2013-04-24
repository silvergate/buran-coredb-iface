package com.dcrux.buran.coredb.iface.node;

/**
 * Buran.
 *
 * @author: ${USER} Date: 02.01.13 Time: 14:45
 */
public enum NodeState {
    available(0),
    historizedAvailable(10),
    historizedPropertiesMissing(11),
    /**
     * Properties and edges are missing. Node metadata (like valid-from and valid-to is available).
     */
    historizedAllMissing(12),
    unavailablePropertiesMissing(20),
    unavailableAllMissing(21);

    NodeState(int state) {
        this.state = (byte) state;
    }

    public static boolean isHistorized(NodeState nodeState) {
        switch (nodeState) {
            case historizedAllMissing:
                return true;
            case historizedAvailable:
                return true;
            case historizedPropertiesMissing:
                return true;
        }
        return false;
    }

    final byte state;

    public byte getState() {
        return state;
    }
}
