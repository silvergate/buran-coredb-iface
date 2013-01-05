package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import com.dcrux.buran.coredb.iface.nodeClass.IDataSetter;
import com.google.common.base.Optional;

import java.util.Locale;

/**
 * Buran.
 *
 * @author: ${USER} Date: 04.01.13 Time: 21:27
 */
public class FtsiAddText implements IDataSetter {
    private final String text;
    private final Optional<Locale> localeHint;

    public static FtsiAddText c(final String text) {
        return new FtsiAddText(text, Optional.<Locale>absent());
    }

    public FtsiAddText(String text, Optional<Locale> localeHint) {
        this.text = text;
        this.localeHint = localeHint;
    }

    public String getText() {
        return text;
    }

    public Optional<Locale> getLocaleHint() {
        return localeHint;
    }
}
