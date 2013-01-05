package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import com.dcrux.buran.coredb.iface.nodeClass.CmpRef;
import com.dcrux.buran.coredb.iface.nodeClass.ICmp;
import com.google.common.base.Optional;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:06
 */
public class FtsiMatch implements ICmp {

    public static final CmpRef REF = new CmpRef((short) 763);

    private final Optional<Locale> localeHint;
    private final Fuzziness fuziness;
    private final String rhs;

    public FtsiMatch(Optional<Locale> localeHint, Fuzziness fuziness, String rhs) {
        this.localeHint = localeHint;
        this.fuziness = fuziness;
        this.rhs = rhs;
    }

    public static FtsiMatch c(Fuzziness fuziness, String rhs) {
        return new FtsiMatch(Optional.<Locale>absent(), fuziness, rhs);
    }

    @Override
    public CmpRef getRef() {
        return REF;
    }

    @Override
    public boolean matches(@Nullable Object lhs) {
        if (lhs == null) return false;
        final IndexData lhsIndexData = (IndexData) lhs;
        if (lhsIndexData.getExact().isEmpty()) return false;

        final Set<String> tokens = new HashSet<>();
        Implemenations.getTokenizer(this.localeHint).tokenize(this.rhs, tokens);
        final Set<String> stemmed = new HashSet<>();
        Implemenations.getStemmer(this.localeHint).stemmDontBase(tokens, stemmed);
        return Implemenations.getData(this.localeHint)
                .matches(stemmed, lhsIndexData, this.fuziness);
    }
}
