package com.dcrux.buran.coredb.iface;

/**
 * Buran.
 *
 * @author: ${USER} Date: 02.01.13 Time: 14:45
 */
public enum NodeState {
    available(0),
    historizedAvailable(10),
    historizedPropertiesMissing(11),
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
