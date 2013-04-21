package com.dcrux.buran.coredb.iface.api;

import com.dcrux.buran.coredb.iface.*;
import com.dcrux.buran.coredb.iface.api.exceptions.*;
import com.dcrux.buran.coredb.iface.domains.DomainHash;
import com.dcrux.buran.coredb.iface.domains.DomainId;
import com.dcrux.buran.coredb.iface.edge.EdgeIndex;
import com.dcrux.buran.coredb.iface.edge.EdgeLabel;
import com.dcrux.buran.coredb.iface.edge.EdgeType;
import com.dcrux.buran.coredb.iface.edgeTargets.IEdgeTarget;
import com.dcrux.buran.coredb.iface.edgeTargets.IIncEdgeTarget;
import com.dcrux.buran.coredb.iface.nodeClass.*;
import com.dcrux.buran.coredb.iface.query.IQuery;
import com.dcrux.buran.coredb.iface.subscription.Subscription;
import com.dcrux.buran.coredb.iface.subscription.SubscriptionId;
import com.google.common.base.Optional;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Buran.
 *
 * @author: ${USER} Date: 02.01.13 Time: 15:26
 */
//FIXME Optional ersetzen mit weiteren methoden
public interface IApi {

    /*********************************************************************************************
     * REGION: Classes
     ********************************************************************************************/

    /**
     * Declares a class and returns its hash. The hash can the be converted to a class-id. The
     * class-hash is globally valid.
     *
     * @param nodeClass
     * @return
     * @throws PermissionDeniedException
     */
    NodeClassHash declareClass(NodeClass nodeClass)
            throws PermissionDeniedException, QuotaExceededException;

    /**
     * Gets the class-id given a class-hash. The class-id is specific to a buran server instance.
     *
     * @param hash
     *         Globally valid class-hash.
     * @return Returns the class-id or <code>null</code> if the class has not yet been defined. Call
     *         {@link #declareClass(com.dcrux.buran.coredb.iface.nodeClass.NodeClass)} first.
     */
    @Nullable
    ClassId getClassIdByHash(NodeClassHash hash) throws QuotaExceededException;

    /*********************************************************************************************
     * REGION: Create and commit
     ********************************************************************************************/

    /**
     * Create a new node in incubation without updating.
     *
     * @param receiver
     * @param sender
     * @param classId
     * @param keepAliveHint
     * @return
     */
    CreateInfo createNew(UserId receiver, UserId sender, ClassId classId,
            Optional<KeepAliveHint> keepAliveHint)
            throws PermissionDeniedException, QuotaExceededException;

    /**
     * Creates a new node in incubation. The created node will update the existing node given by
     *
     * @param nodeToUpdate
     *         after calling commit.
     * @param receiver
     * @param sender
     * @param keepAliveHint
     * @param nodeToUpdate
     * @param historyHint
     * @return
     * @throws NodeNotUpdatable
     *         Is thrown if the node to update is not currently the current version or if the node
     *         has been marked as deleted.
     * @throws PermissionDeniedException
     * @throws HistoryHintNotFulfillable
     */
    CreateInfoUpdate createNewUpdate(UserId receiver, UserId sender,
            Optional<KeepAliveHint> keepAliveHint, NidVer nodeToUpdate,
            Optional<HistoryHint> historyHint)
            throws NodeNotUpdatable, PermissionDeniedException, HistoryHintNotFulfillable,
            QuotaExceededException;

    /**
     * Commits one or more nodes from incubation. This method works atomic.
     *
     * @param receiver
     * @param sender
     * @param incNid
     *         One or more nodes from incubation.
     * @return
     * @throws OptimisticLockingException
     * @throws PermissionDeniedException
     * @throws IncubationNodeNotFound
     */
    CommitResult commit(UserId receiver, UserId sender, IncNid... incNid)
            throws OptimisticLockingException, PermissionDeniedException, IncubationNodeNotFound,
            QuotaExceededException;

    /**
     * Extends the keep alive time of a node in incubation.
     *
     * @param receiver
     * @param sender
     * @param keepAliveHint
     * @param incNid
     *         One or more nodes in incubation.
     * @return Keep-alive-information.
     * @throws IncubationNodeNotFound
     *         Is thrown if at least one node cannot be found.
     */
    KeepAliveInfo keepAlive(UserId receiver, UserId sender, KeepAliveHint keepAliveHint,
            IncNid... incNid) throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Removed one or more nodes from incubation without committing. Continues with execution if
     * removal of a node fails.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @throws IncubationNodeNotFound
     *         Is thrown if at least one given node is not found (continues with removal of other
     *         nodes).
     */
    void cancelIncubationNode(UserId receiver, UserId sender, IncNid... incNid)
            throws IncubationNodeNotFound, QuotaExceededException;

    /*********************************************************************************************
     * REGION: Data manipulation
     ********************************************************************************************/

    /**
     * Sets data to the property specified by typeIndex.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param typeIndex
     *         Type index. Has to be defined in the class.
     * @param dataSetter
     *         An implementation of {@link IDataSetter}. Has to be supported by the type defined in
     *         the class.
     * @throws IncubationNodeNotFound
     */
    void setData(UserId receiver, UserId sender, IncNid incNid, short typeIndex,
            IDataSetter dataSetter) throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Transfers information from one node to another. The source node has the following
     * requirement: <ul> <li>Must have the same receiver as the target node.</li> <li>If
     * transferring of properties and/or edge is not excluded the target node must be of the same
     * class as the source node. </li> </ul>
     * <p/>
     * TODO: Das Transferieren der public properties sollte eigentlich ok sein, zwischen zwei
     * unterschiedlichen klassen (jedenfalls meistens, je nach constraint).
     *
     * @param receiver
     * @param sender
     * @param target
     *         Target node in incubation.
     * @param src
     *         Source node (Note: historized nodes are valid too - if readable, see {@link
     *         NodeState#historizedAvailable} and {@link NodeState#historizedPropertiesMissing}).
     * @param transferExclusion
     *         Exclude information from transferring.
     * @throws IncubationNodeNotFound
     *         Target node was not found in incubation.
     * @throws InformationUnavailableException
     *         Information from the source node is not available because the node has been
     *         historized and the information is missing.
     * @throws PermissionDeniedException
     *         Reading from the source node is denied.
     * @throws NodeNotFoundException
     *         The source node was not found.
     * @throws IncompatibleClassException
     *         Transferring of properties and/or edge is activated and source and target node are
     *         not of the same class.
     */
    void transferData(UserId receiver, UserId sender, IncNid target, NidVer src,
            TransferExclusion transferExclusion)
            throws IncubationNodeNotFound, InformationUnavailableException,
            PermissionDeniedException, NodeNotFoundException, IncompatibleClassException,
            QuotaExceededException;

    /**
     * Sets an edge specified by the given label and index to the given target. Fails if an edge
     * specified by label and index already exists.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param index
     * @param label
     * @param target
     * @throws EdgeIndexAlreadySet
     *         The specified (by label and index) edge already exists.
     * @throws IncubationNodeNotFound
     * @see #setEdgeReplace(com.dcrux.buran.coredb.iface.UserId, com.dcrux.buran.coredb.iface
     *      .UserId, com.dcrux.buran.coredb.iface.IncNid, com.dcrux.buran.coredb.iface.edge
     *      .EdgeIndex, com.dcrux.buran.coredb.iface.edge.EdgeLabel, com.dcrux.buran.coredb.iface
     *      .edgeTargets .IIncEdgeTarget)
     */
    void setEdge(UserId receiver, UserId sender, IncNid incNid, EdgeIndex index, EdgeLabel label,
            IIncEdgeTarget target)
            throws EdgeIndexAlreadySet, IncubationNodeNotFound, QuotaExceededException;

    /**
     * Sets an edge specified by the given label and index to the given target. Will replace an
     * existing edge (if any).
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param index
     * @param label
     * @param target
     * @throws IncubationNodeNotFound
     * @see #setEdge(com.dcrux.buran.coredb.iface.UserId, com.dcrux.buran.coredb.iface.UserId,
     *      com.dcrux.buran.coredb.iface.IncNid, com.dcrux.buran.coredb.iface.edge.EdgeIndex,
     *      com.dcrux.buran.coredb.iface.edge.EdgeLabel, com.dcrux.buran.coredb.iface.edgeTargets
     *      .IIncEdgeTarget)
     */
    void setEdgeReplace(UserId receiver, UserId sender, IncNid incNid, EdgeIndex index,
            EdgeLabel label, IIncEdgeTarget target)
            throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Removes a single edge by given label an index. Throws {@link EdgeIndexNotSet} if the
     * specified edge does not exist.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param label
     * @param index
     * @throws EdgeIndexNotSet
     *         Is thrown if the edge specified by label and index does not exists.
     * @throws IncubationNodeNotFound
     * @see #removeEdge(com.dcrux.buran.coredb.iface.UserId, com.dcrux.buran.coredb.iface.UserId,
     *      com.dcrux.buran.coredb.iface.IncNid, com.dcrux.buran.coredb.iface.edge.EdgeLabel,
     *      com.dcrux.buran.coredb.iface.edge.EdgeIndex)
     */
    void removeEdgeStrict(UserId receiver, UserId sender, IncNid incNid, EdgeLabel label,
            EdgeIndex index) throws EdgeIndexNotSet, IncubationNodeNotFound, QuotaExceededException;

    /**
     * Removes a single edge by given label and index. Does nothing if the specified edge does not
     * exist.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param label
     * @param index
     * @throws IncubationNodeNotFound
     * @see #removeEdgeStrict(com.dcrux.buran.coredb.iface.UserId, com.dcrux.buran.coredb.iface
     *      .UserId, com.dcrux.buran.coredb.iface.IncNid, com.dcrux.buran.coredb.iface.edge
     *      .EdgeLabel, com.dcrux.buran.coredb.iface.edge.EdgeIndex)
     */
    void removeEdge(UserId receiver, UserId sender, IncNid incNid, EdgeLabel label, EdgeIndex index)
            throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Removes all out edge with the specified label (if a label is given) or all edge (if no label
     * is given).
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param label
     * @throws IncubationNodeNotFound
     */
    void removeEdges(UserId receiver, UserId sender, IncNid incNid, Optional<EdgeLabel> label)
            throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Marks a node as deleted. All other manipulations on this node in incubation have no effect.
     * This method only works if updating a node. After commit, the updated node will be historized.
     * This node in incubation will be lost and there won't be a current version of the updated
     * node.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @throws IncubationNodeNotFound
     * @throws NotUpdatingException
     *         If called on a node not updating another node.
     */
    void markNodeAsDeleted(UserId receiver, UserId sender, IncNid incNid)
            throws IncubationNodeNotFound, NotUpdatingException, QuotaExceededException;

    /*********************************************************************************************
     * REGION: Data read api
     ********************************************************************************************/

    /**
     * Gets data from a property of a node.
     *
     * @param receiver
     * @param sender
     * @param nidVersion
     * @param typeIndex
     *         Type index, is defined in the node class.
     * @param dataGetter
     *         Implementation of {@link IDataGetter}. Has to be supported by the type defined in the
     *         class.
     * @return Data. Type depends on the implementation of the chosen {@link IDataGetter}.
     * @throws InformationUnavailableException
     *
     * @throws PermissionDeniedException
     * @throws NodeNotFoundException
     */
    @Nullable
    Object getData(UserId receiver, UserId sender, NidVer nidVersion, short typeIndex,
            IDataGetter dataGetter)
            throws InformationUnavailableException, PermissionDeniedException,
            NodeNotFoundException, QuotaExceededException;

    /**
     * Gets out-edge from a node. Can optionally be filtered by label and public and private edge.
     * <p/>
     * TODO: Filtern nach einem range von edge-indexes.
     *
     * @param receiver
     * @param sender
     * @param nid
     * @param types
     *         Optional: Filter by modifier (public or private). Must not be empty.
     * @param label
     *         Optional: Filter by label<strong>Important</strong>: <ul><li>Illegal (not declared in
     *         the class) private labels are not allowed (will throw an exception).</li><li>Illegal
     *         public labels result in an empty result set. (reason: they might not be declared in
     *         the system, so buran is not able to reason whether they're legal or
     *         illegal).</li><li>If a label is given, the correct modifier must be in the
     *         types-enum-set.</li></ul>
     * @return
     * @throws NodeNotFoundException
     * @throws InformationUnavailableException
     *
     * @throws PermissionDeniedException
     */
    Map<EdgeLabel, Map<EdgeIndex, IEdgeTarget>> getOutEdges(UserId receiver, UserId sender,
            NidVer nid, EnumSet<EdgeType> types, Optional<EdgeLabel> label)
            throws NodeNotFoundException, InformationUnavailableException,
            PermissionDeniedException, QuotaExceededException;

    /**
     * Gets in-edge from a node. Can optionally be filtered by various parameters.
     *
     * @param receiver
     * @param sender
     * @param nid
     * @param sourceHistoryStates
     * @param sourceClassId
     * @param types
     *         Filter by modifier (public or private). Must not be empty.
     * @param indexRange
     * @param label
     *         Optional. If a private label is given, the sourceClassId must either be absent or
     *         must be the same classId as in the private label.
     * @return
     * @throws NodeNotFoundException
     * @throws InformationUnavailableException
     *
     * @throws PermissionDeniedException
     * @throws QuotaExceededException
     */
    Map<EdgeLabel, Multimap<EdgeIndex, IEdgeTarget>> getInEdges(UserId receiver, UserId sender,
            NidVer nid, EnumSet<HistoryState> sourceHistoryStates, Optional<ClassId> sourceClassId,
            EnumSet<EdgeType> types, Optional<EdgeIndexRange> indexRange, Optional<EdgeLabel> label)
            throws NodeNotFoundException, InformationUnavailableException,
            PermissionDeniedException, QuotaExceededException;

    /*********************************************************************************************
     * REGION: Meta-Data read api
     ********************************************************************************************/

    /**
     * Returns the state of the given node.
     *
     * @param receiver
     * @param sender
     * @param nid
     * @return
     * @throws NodeNotFoundException
     *         Is thrown if a node with the given node-id does not exists (now and in the past).
     * @throws PermissionDeniedException
     */
    NodeState getNodeState(UserId receiver, UserId sender, NidVer nid)
            throws NodeNotFoundException, PermissionDeniedException, QuotaExceededException;

    /**
     * Returns the current version of the node specified by its id.
     *
     * @param receiver
     * @param sender
     * @param nid
     * @return The versioned node-id or <code>null</code> if there's no current version. There's no
     *         current version if the node has been marked as deleted.
     * @throws NodeNotFoundException
     *         The node given by its id was not found.
     */
    @Nullable
    NidVer getCurrentNodeVersion(UserId receiver, UserId sender, Nid nid)
            throws NodeNotFoundException, QuotaExceededException;

    /**
     * Returns the latest version of a deleted node.
     * <p/>
     * TODO: Return Version, not NidVer
     *
     * @param receiver
     * @param sender
     * @param nid
     * @return Latest version of a node or <code>null</code> if this is a valid node but not
     *         deleted.
     * @throws NodeNotFoundException
     *         The node given by its id was not found.
     */
    @Nullable
    NidVer getLatestVersionBeforeDeletion(UserId receiver, UserId sender, Nid nid)
            throws NodeNotFoundException, QuotaExceededException;

    /**
     * Returns the class-id of the given node.
     *
     * @param receiver
     * @param sender
     * @param nid
     * @return
     * @throws NodeNotFoundException
     * @throws PermissionDeniedException
     * @throws QuotaExceededException
     */
    ClassId getClassId(UserId receiver, UserId sender, NidVer nid)
            throws NodeNotFoundException, PermissionDeniedException, QuotaExceededException;

    /*********************************************************************************************
     * REGION: Domain API
     ********************************************************************************************/

    /**
     * Creates a new domain and returns its ID.
     *
     * @param receiver
     * @param sender
     * @return
     * @throws PermissionDeniedException
     */
    DomainId addAnonymousDomain(UserId receiver, UserId sender)
            throws PermissionDeniedException, QuotaExceededException;

    /**
     * Creates a new domain identified by a hash (if non-existent) - or does nothing (if existent).
     * Returns the domain id.
     *
     * @param receiver
     * @param sender
     * @param hash
     * @return
     * @throws PermissionDeniedException
     */
    DomainId addOrGetIdentifiedDomain(UserId receiver, UserId sender, DomainHash hash)
            throws PermissionDeniedException, QuotaExceededException;

    /**
     * Assigns a domain the the given node. Does nothing if node is already in domain.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param domainId
     * @throws IncubationNodeNotFound
     * @throws DomainNotFoundException
     */
    void addDomainToNode(UserId receiver, UserId sender, IncNid incNid, DomainId domainId)
            throws IncubationNodeNotFound, DomainNotFoundException, QuotaExceededException;

    /**
     * Removes the domain from the given node. Returns <code>true</code> if the domain was assigned
     * to the node and is now removed. Returns <code>false</code> if the domain was not assigned to
     * the node.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @param domainId
     * @return
     * @throws IncubationNodeNotFound
     * @throws DomainNotFoundException
     */
    boolean removeDomainFromNode(UserId receiver, UserId sender, IncNid incNid, DomainId domainId)
            throws IncubationNodeNotFound, DomainNotFoundException, QuotaExceededException;

    /**
     * Removes all domains from the node.
     *
     * @param receiver
     * @param sender
     * @param incNid
     * @return
     * @throws IncubationNodeNotFound
     */
    int clearDomainsFromNode(UserId receiver, UserId sender, IncNid incNid)
            throws IncubationNodeNotFound, QuotaExceededException;

    /**
     * Returns the domains that are assigned to the node.
     *
     * @param receiver
     * @param sender
     * @param nidVer
     * @return
     * @throws InformationUnavailableException
     *
     * @throws PermissionDeniedException
     * @throws NodeNotFoundException
     */
    Set<DomainId> getDomains(UserId receiver, UserId sender, NidVer nidVer)
            throws InformationUnavailableException, PermissionDeniedException,
            NodeNotFoundException, QuotaExceededException;

    /**
     * Creates a domain hash.
     *
     * @param uuid
     * @param creatorName
     * @param creatorEmail
     * @param description
     * @return
     * @throws QuotaExceededException
     */
    DomainHash createDomainHash(UUID uuid, String creatorName, String creatorEmail,
            String description) throws QuotaExceededException;

    /*********************************************************************************************
     * REGION: Subscription API
     ********************************************************************************************/

    /**
     * Adds a new subscription to the system and returns a generated id.
     *
     * @param subscription
     * @return
     */
    SubscriptionId addSubscription(final Subscription subscription)
            throws PermissionDeniedException, QuotaExceededException;

    /**
     * Removes a subscription.
     *
     * @param subscriptionId
     * @return Returns <code>true</code> if the subscription was found and could be removed. Returns
     *         <code>false</code> if the subscription was not found.
     */
    boolean removeSubscription(UserId receiver, UserId sender, final SubscriptionId subscriptionId)
            throws PermissionDeniedException, QuotaExceededException;

    /*********************************************************************************************
     * REGION: Query API
     ********************************************************************************************/

    /**
     * Performs a query against the database.
     *
     * @param receiverId
     * @param senderId
     * @param query
     * @param countNumberOfResultsWithoutLimit
     *         If <code>true</code> the number of results without limit is returned in the result.
     * @return
     * @throws PermissionDeniedException
     *         Is only thrown if querying the completely denied. Is not thrown if only some nodes
     *         cannot be queried (those nodes are filtered from the result-set).
     */
    QueryResult query(UserId receiverId, UserId senderId, IQuery query,
            boolean countNumberOfResultsWithoutLimit)
            throws PermissionDeniedException, QuotaExceededException;
}