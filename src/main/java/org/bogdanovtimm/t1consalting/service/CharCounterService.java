package org.bogdanovtimm.t1consalting.service;

import java.util.Map;

import org.bogdanovtimm.t1consalting.form.CharCounterForm;

public interface CharCounterService {

    /**
     * Counts character in in given non-empty text.
     * Opitions:
     * case-sensitive,
     * trim all whitespaces.
     * 
     * @param form - given text. Must not be empty.
     * @return {@code Map<Character, Integer>} - map of characters and number of their occurances.
     * @throws {@code IllegalArgumentException} - throws if text is empty.
     */
    public Map<Character, Integer> countChars(CharCounterForm form) throws IllegalArgumentException;
}
