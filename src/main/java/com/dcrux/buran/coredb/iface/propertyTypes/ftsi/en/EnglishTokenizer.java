package com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en;

import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.ITokenizer;

import java.util.Set;

/**
 * A simple tokenizer for the english language.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:13
 */
public class EnglishTokenizer implements ITokenizer {
    @Override
    public void tokenize(String input, Set<String> result) {
        String[] resArray = input.split("[^A-Za-z0-9]+");
        for (int x = 0; x < resArray.length; x++) {
            result.add(resArray[x]);
        }
    }
}
