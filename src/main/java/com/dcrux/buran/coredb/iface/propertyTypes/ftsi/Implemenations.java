package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en.EnglishData;
import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en.EnglishStemmer;
import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en.EnglishTokenizer;
import com.google.common.base.Optional;

import java.util.Locale;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:56
 */
public class Implemenations {

    private static final IData EN_DATA = new EnglishData();
    private static final IStemmer EN_STEMMER = new EnglishStemmer();
    private static final ITokenizer EN_TOKENIZER = new EnglishTokenizer();

    public static IData getData(Optional<Locale> localeHint) {
        return EN_DATA;
    }

    public static IStemmer getStemmer(Optional<Locale> localeHint) {
        return EN_STEMMER;
    }

    public static ITokenizer getTokenizer(Optional<Locale> localeHint) {
        return EN_TOKENIZER;
    }
}
