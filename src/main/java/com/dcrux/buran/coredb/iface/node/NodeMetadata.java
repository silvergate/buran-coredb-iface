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
    private final Optional<Integer> version;

    private NodeMetadata(UserId sender, Date commitTime, Optional<Date> validToTime,
            boolean markedAsDeleted, Optional<Integer> version) {
        this.sender = sender;
        this.commitTime = commitTime;
        this.validToTime = validToTime;
        this.markedAsDeleted = markedAsDeleted;
        this.version = version;
    }

    /**
     * It's the latest version, not marked as deleted, not historized.
     *
     * @param sender
     * @param commitTime
     * @return
     */
    public static NodeMetadata currentVersion(UserId sender, Date commitTime) {
        return new NodeMetadata(sender, commitTime, Optional.<Date>absent(), false,
                Optional.<Integer>absent());
    }

    /**
     * Not latest version.
     *
     * @param sender
     * @param commitTime
     * @param validToTime
     * @param markedAsDeleted
     *         The node series has been marked as deleted.
     * @param latestVersion
     *         If deleted this is the latest version before deletion. If not deleted, this is the
     *         latest version (current version).
     * @return
     */
    public static NodeMetadata historizedVersion(UserId sender, Date commitTime, Date validToTime,
            boolean markedAsDeleted, int latestVersion) {
        return new NodeMetadata(sender, commitTime, Optional.<Date>of(validToTime), markedAsDeleted,
                Optional.of(latestVersion));
    }

    public int getLatestVersion() {
        if (isMarkedAsDeleted()) {
            throw new IllegalStateException("This node has no latest version since it's been " +
                    "deleted. Check isMarkedAsDeleted() before calling this method.");
        }
        if (isCurrent()) {
            throw new IllegalStateException("This is already the latest version. Call isCurrent()" +
                    " before calling this method.");
        }
        return this.version.get();
    }

    public int getLatestVersionBeforeDeletion() {
        if (!isMarkedAsDeleted()) {
            throw new IllegalStateException("This node has not been deleted.");
        }
        if (isCurrent()) {
            throw new IllegalStateException("This is already the latest version. Call isCurrent()" +
                    " before calling this method.");
        }
        return this.version.get();
    }

    public boolean isCurrent() {
        return !this.getValidToTime().isPresent();
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

    /**
     * Returns <code>true</code> if this node series has been deleted.
     *
     * @return
     */
    public boolean isMarkedAsDeleted() {
        return markedAsDeleted;
    }
}
