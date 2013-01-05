package com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en;

import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.Fuzziness;
import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.IData;
import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.IndexData;

import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:44
 */
public class EnglishData implements IData {
    @Override
    public void addToData(Set<String> tokens, IndexData indexData) {
        for (final String token : tokens) {
            if (token.length() >= 3) {
                final String minToken = token.substring(0, 2);
                indexData.getHighFuziness().add(minToken.hashCode());
            }
            if (token.length() >= 4) {
                final String mediumToken = token.substring(0, 3);
                indexData.getMediumFuziness().add(mediumToken.hashCode());
            }
            indexData.getExact().add(token.hashCode());
        }
    }

    @Override
    public boolean matches(String token, IndexData indexData, Fuzziness fuziness) {
        if ((fuziness == Fuzziness.high) || (token.length() == 3)) {
            if (token.length() >= 3) {
                final String minToken = token.substring(0, 2);
                if (indexData.getHighFuziness().contains(minToken.hashCode())) return true;
            }
        }

        if ((fuziness == Fuzziness.medium) || (token.length() == 4)) {
            if (token.length() >= 4) {
                final String mediumToken = token.substring(0, 3);
                if (indexData.getMediumFuziness().contains(mediumToken.hashCode())) return true;
            }
        }

        return indexData.getExact().contains(token.hashCode());
    }

    public boolean matches(Set<String> tokens, IndexData indexData, Fuzziness fuziness) {
        float percentile;
        switch (fuziness) {
            case low:
                percentile = 1;
                break;
            case medium:
                percentile = 0.7f;
                break;
            case high:
                percentile = 0.5f;
                break;
            default:
                throw new IllegalArgumentException("Unknown fuziness");
        }

        float tokensToMatch = (float) tokens.size() * percentile;
        tokensToMatch = (float) Math.ceil(tokensToMatch);
        int tokensToMatchInt = (int) tokensToMatch;
        int tokensMatched = 0;
        for (final String token : tokens) {
            /* No need to match short tokens */
            if (token.length() < 3) tokensToMatchInt--;
            boolean matched = matches(token, indexData, fuziness);
            if (matched) tokensMatched++;
        }
        /* Need to match at least one token (if only supplied short ones) */
        if (tokensMatched == 0) return false;
        return (tokensMatched >= tokensToMatchInt);
    }
}
