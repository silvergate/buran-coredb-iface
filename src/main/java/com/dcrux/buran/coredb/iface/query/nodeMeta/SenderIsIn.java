package com.dcrux.buran.coredb.iface.query.nodeMeta;

import com.dcrux.buran.coredb.iface.UserId;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author caelis
 */
public class SenderIsIn implements INodeMetaCondition {
    private final Set<Long> userIds;

    public static SenderIsIn c(UserId... userIds) {
        final Set<Long> userIdsSet = new HashSet<>();
        for (UserId userId : userIds) {
            userIdsSet.add(userId.getId());
        }
        return new SenderIsIn(userIdsSet);
    }

    private SenderIsIn(Set<Long> userIdSet) {
        this.userIds = userIdSet;
    }

    public SenderIsIn(Long... userId) {
        this.userIds = new HashSet<>();
        this.userIds.addAll(Arrays.asList(userId));
    }

    public Set<Long> getUserIds() {
        return Collections.unmodifiableSet(this.userIds);
    }

    @Override
    public boolean matches(IMetaInfoForQuery metaInfoForQuery) {
        final long sender = metaInfoForQuery.getSender();
        return this.userIds.contains(sender);
    }
}
