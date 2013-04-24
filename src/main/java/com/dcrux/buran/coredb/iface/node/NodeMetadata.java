package com.dcrux.buran.coredb.iface.node;

import com.dcrux.buran.coredb.iface.UserId;
import com.google.common.base.Optional;

import java.io.Serializable;
import java.util.Date;

/**
 * Buran.
 *
 * @author: ${USER} Date: 24.04.13 Time: 01:36
 */
public class NodeMetadata implements Serializable {
    private final UserId sender;
    private final Date commitTime;
    private final Optional<Date> validToTime;
    private final boolean markedAsDeleted;

    private NodeMetadata(UserId sender, Date commitTime, Optional<Date> validToTime,
            boolean markedAsDeleted) {
        this.sender = sender;
        this.commitTime = commitTime;
        this.validToTime = validToTime;
        this.markedAsDeleted = markedAsDeleted;
    }

    /**
     * It's the latest version, not marked as deleted.
     *
     * @param sender
     * @param commitTime
     * @return
     */
    public static NodeMetadata currentVersion(UserId sender, Date commitTime) {
        return new NodeMetadata(sender, commitTime, Optional.<Date>absent(), false);
    }

    /**
     * It's the latest version, but has been marked as deleted.
     *
     * @param sender
     * @param commitTime
     * @param validToTime
     * @return
     */
    public static NodeMetadata markedAsDeleted(UserId sender, Date commitTime, Date validToTime) {
        return new NodeMetadata(sender, commitTime, Optional.<Date>of(validToTime), true);
    }

    /**
     * Not latest version.
     *
     * @param sender
     * @param commitTime
     * @param validToTime
     * @return
     */
    public static NodeMetadata historizedVersion(UserId sender, Date commitTime, Date validToTime) {
        return new NodeMetadata(sender, commitTime, Optional.<Date>of(validToTime), false);
    }

    public UserId getSender() {
        return sender;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public Optional<Date> getValidToTime() {
        return validToTime;
    }

    public boolean isMarkedAsDeleted() {
        return markedAsDeleted;
    }
}
