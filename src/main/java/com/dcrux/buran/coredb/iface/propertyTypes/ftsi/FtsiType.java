package com.dcrux.buran.coredb.iface.propertyTypes.ftsi;

import com.dcrux.buran.coredb.iface.nodeClass.*;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * Full-Text-Search-Index.
 *
 * @author caelis
 */
public class FtsiType implements IType {
    public static final TypeRef REF = new TypeRef((short) 54);

    @Override
    public TypeRef getRef() {
        return REF;
    }

    public static FtsiType c() {
        return new FtsiType();
    }

    @Nullable
    @Override
    public ISorter getSorter(SorterRef sorterRef) {
        return null;
    }

    @Override
    public boolean supports(CmpRef comparator) {
        if (comparator.equals(FtsiMatch.REF)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(IDataSetter dataSetter) {
        if (dataSetter.getClass().equals(FtsiAddText.class)) {
            return true;
        }
        if (dataSetter.getClass().equals(FtsiClear.class)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(IDataGetter dataGetter) {
        return false;
    }

    @Nullable
    @Override
    public Object setData(IDataSetter dataSetter, @Nullable Object currentValue) {
        if (dataSetter.getClass().equals(FtsiAddText.class)) {
            final FtsiAddText addText = (FtsiAddText) dataSetter;
            IndexData data = (IndexData) currentValue;
            if (data == null) data = new IndexData();

            final Set<String> tokenized = new HashSet<>();
            Implemenations.getTokenizer(addText.getLocaleHint())
                    .tokenize(addText.getText(), tokenized);
            final Set<String> stemmed = new HashSet<>();
            Implemenations.getStemmer(addText.getLocaleHint()).stemm(tokenized, stemmed);
            Implemenations.getData(addText.getLocaleHint()).addToData(stemmed, data);
            return data;
        }
        if (dataSetter.getClass().equals(FtsiClear.class)) {
            IndexData data = (IndexData) currentValue;
            if (data != null) data.clear();
            return data;
        }

        throw new IllegalArgumentException("Illegal dataSetter");
    }

    @Nullable
    @Override
    public Object getData(IDataGetter dataGetter, @Nullable Object value) {
        throw new IllegalStateException("Operation not supported (Hinweis: Brauchts irgendwann " +
                "doch noch (extrahieren aller daten)");
    }
}
