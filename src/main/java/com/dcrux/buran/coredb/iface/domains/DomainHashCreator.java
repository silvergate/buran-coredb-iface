package com.dcrux.buran.coredb.iface.domains;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 01:28
 */
public class DomainHashCreator {
    private final UUID uuid;
    private final String creatorName;
    private final String creatorEmail;
    private final String description;

    public DomainHashCreator(UUID uuid, String creatorName, String creatorEmail,
            String description) {
        this.uuid = uuid;
        if (!((creatorName.length() >= 6) && (creatorName.length() <= 64))) {
            throw new IllegalArgumentException(
                    "creatorName: (creatorName.length()>=6) && (creatorName.length()<=48)");
        }
        this.creatorName = creatorName;
        if (!((creatorEmail.length() >= 6) && (creatorEmail.length() <= 64))) {
            throw new IllegalArgumentException(
                    "creatorEmail: (creatorEmail.length()>=6) && (creatorEmail.length()<=48)");
        }
        this.creatorEmail = creatorEmail;
        if (!((description.length() >= 6) && (description.length() <= 64))) {
            throw new IllegalArgumentException(
                    "description: (description.length()>=6) && (description.length()<=48)");
        }
        this.description = description;
    }

    public DomainHash createHash() {
        final byte result[] = new byte[48];
        ByteBuffer bb = ByteBuffer.wrap(result);

        bb.putLong(this.uuid.getLeastSignificantBits());
        bb.putLong(this.uuid.getMostSignificantBits());

        final String combination = MessageFormat
                .format("{0}:{1}:{2}", this.creatorName, this.creatorEmail, this.description);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algo not found");
        }
        byte[] hash = md.digest(combination.getBytes());
        bb.put(hash);

        return DomainHash.create(result);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public String getDescription() {
        return description;
    }
}
