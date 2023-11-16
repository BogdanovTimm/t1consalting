package org.bogdanovtimm.t1consalting.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bogdanovtimm.t1consalting.exception.ApiException;
import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharCounterServiceImpl implements CharCounterService {

    /**
     * Counts character in in given non-empty text.
     * Opitions:
     * - case-sensitive
     * - trim all whitespaces.
     * 
     * @param form - given text. Must not be blank.
     * @return {@code Map<Character, Integer>} - chars and number of them.
     * @throws {@code IllegalArgumentException} - throws if text is empty.
     */
    @Override
    public Map<Character, Integer> countChars(CharCounterForm form) throws IllegalArgumentException {
        var text = addOpions(form);
        Map<Character, Integer> map = new HashMap<>();
        char[] charArray = text.toCharArray();
        for (char character : charArray) {
            if (map.containsKey(character)) {
                map.put(character, map.get(character) + 1);
            } else {
                map.put(character, 1);
            }
        }
        log.info("Characters were counted");
        map = map.entrySet()
                 .stream()
                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                 .collect(Collectors.toMap(Map.Entry::getKey,
                                           Map.Entry::getValue,
                                           (oldValue, newValue) -> oldValue,
                                           LinkedHashMap::new));
        log.info(map.toString());
        return map;
    }

    private String addOpions(CharCounterForm form) {
        var text = form.getText();
        if (text.isEmpty()) {
            throw new ApiException("Text must not be empty");
        }
        if (form.isCaseSensitive() == false) {
            text = text.toLowerCase();
            log.info("Case sensitivity: Case-insensitive");
        } else {
            log.info("Case sensitivity: Case-sensitive");
        }
        if (form.isTrimSpaces() == true) {
            text = text.replaceAll("\\s", "");
            log.info("Optional: Trim all white spaces");
            if (text.isEmpty()) {
                throw new ApiException("After removing spaces there is nothing to count");
            }
        }
        return text;
    }
}
