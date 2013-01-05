package com.dcrux.buran.coredb.iface.propertyTypes.ftsi.en;

import com.dcrux.buran.coredb.iface.propertyTypes.ftsi.IStemmer;
import com.google.common.collect.HashMultimap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A very basic english stemmer.
 *
 * @author: ${USER} Date: 04.01.13 Time: 23:16
 */
public class EnglishStemmer implements IStemmer {

    private static HashMultimap<String, String> baseToWords = HashMultimap.create();
    private static Map<String, String> wordToBase = new HashMap<>();

    private static void add(String base, String... words) {
        final String baseLc = base.toLowerCase();
        for (final String singleWord : words) {
            final String singelWordLc = singleWord.toLowerCase();
            baseToWords.put(baseLc, singelWordLc);
            wordToBase.put(singelWordLc, baseLc);
        }
    }

    static {
        add("have", "has", "had", "were", "was");
        add("is", "are", "should");
        add("can", "could");
    }

    @Override
    public void stemm(Set<String> input, Set<String> output) {
        for (String inputStr : input) {
            final String isLc = inputStr.toLowerCase();
            final String base = wordToBase.get(isLc);
            if (base != null) output.add(base);
            else {
                /* Remove past tense: "waited" -> "wait" */
                if (isLc.endsWith("ed") && (isLc.length() >= 4))
                    output.add(isLc.substring(0, isLc.length() - 1 - 2));
                /* Remove plural: "cars" -> "car" */
                if (isLc.endsWith("s") && (isLc.length() >= 4))
                    output.add(isLc.substring(0, isLc.length() - 1 - 1));
            }
            output.add(isLc);
        }
    }

    @Override
    public void stemmDontBase(Set<String> input, Set<String> output) {
        for (String inputStr : input) {
            final String isLc = inputStr.toLowerCase();
            output.add(isLc);
        }
    }
}
