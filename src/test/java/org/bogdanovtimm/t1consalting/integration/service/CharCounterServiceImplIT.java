package org.bogdanovtimm.t1consalting.integration.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.bogdanovtimm.t1consalting.exception.ApiException;
import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.bogdanovtimm.t1consalting.service.CharCounterServiceImpl;
import org.bogdanovtimm.t1consalting.util.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CharCounterServiceImplIT extends IntegrationTestBase {

    private final CharCounterServiceImpl charCouter;

    @Test
    void countCharsInsensitive() {
        CharCounterForm form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        Map<Character, Integer> map = Map.of('h', 1, 'e', 1, 'l', 3, 'o', 2, ' ', 1, 'w', 1, 'r', 1, 'd', 1);
        checkLowercase(map);
        List<Integer> sortedValues = sortedValues(map);
        var output = charCouter.countChars(form);
        checkLowercase(output);
        Assertions.assertThat(output).isEqualTo(map);
        var LinkedListOfValues = new LinkedList<>(output.values());
        Assertions.assertThat(LinkedListOfValues).isEqualTo(sortedValues);
    }

    @Test
    void countCharsSensitive() {
        CharCounterForm form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setCaseSensitive(true);
        Map<Character, Integer> map = Map.of('H', 1, 'e', 1, 'L', 1, 'l', 2, 'o', 1, ' ', 1, 'w', 1, 'O', 1, 'r', 1, 'D', 1);
        List<Integer> sortedValues = sortedValues(map);
        var output = charCouter.countChars(form);
        Assertions.assertThat(output).isEqualTo(map);
        var LinkedListOfValues = new LinkedList<>(output.values());
        Assertions.assertThat(LinkedListOfValues).isEqualTo(sortedValues);
    }

    @Test
    void countCharsInsensitiveTrimmingSpaces() {
        CharCounterForm form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setTrimSpaces(true);
        Map<Character, Integer> map = Map.of('h', 1, 'e', 1, 'l', 3, 'o', 2, 'w', 1, 'r', 1, 'd', 1);
        map.keySet().forEach((key) -> Assertions.assertThat(Character.isLowerCase(key)).isTrue());
        List<Integer> sortedValues = sortedValues(map);
        var output = charCouter.countChars(form);
        Assertions.assertThat(output).isEqualTo(map);
        Assertions.assertThat(output).doesNotContainKeys(' ');
        var LinkedListOfValues = new LinkedList<>(output.values());
        Assertions.assertThat(LinkedListOfValues).isEqualTo(sortedValues);
    }

    @Test
    void countCharsSensitiveTrimmingSpaces() {
        CharCounterForm form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setCaseSensitive(true);
        form.setTrimSpaces(true);
        Map<Character, Integer> map = Map.of('H', 1, 'e', 1, 'L', 1, 'l', 2, 'o', 1, 'w', 1, 'O', 1, 'r', 1, 'D', 1);
        List<Integer> sortedValues = sortedValues(map);
        var output = charCouter.countChars(form);
        Assertions.assertThat(output).isEqualTo(map);
        Assertions.assertThat(output).doesNotContainKeys(' ');
        var LinkedListOfValues = new LinkedList<>(output.values());
        Assertions.assertThat(LinkedListOfValues).isEqualTo(sortedValues);
    }

    @Test
    void apiExceptionIfEmpty() {
        CharCounterForm form = new CharCounterForm();
        form.setText("");
        Assertions.assertThatExceptionOfType(ApiException.class)
                  .isThrownBy(() -> {
                      charCouter.countChars(form);
                  })
                  .withMessage("Text must not be empty");
    }

    @Test
    void apiExceptionIfAfterRemovingSpacesNothingLeft() {
        CharCounterForm form = new CharCounterForm();
        form.setText(" ");
        form.setTrimSpaces(true);
        Assertions.assertThatExceptionOfType(ApiException.class)
                  .isThrownBy(() -> {
                      charCouter.countChars(form);
                  })
                  .withMessage("After removing spaces there is nothing to count");
    }

    private List<Integer> sortedValues(Map<Character, Integer> unsortedMap) {
        return unsortedMap.values()
                          .stream()
                          .sorted(Comparator.reverseOrder())
                          .collect(Collectors.toCollection(LinkedList::new));
    }

    private void checkLowercase(Map<Character, Integer> map) {
        map.keySet()
           .forEach((key) -> Assertions.assertThat(!Character.isLetter(key) ||
                                                   Character.isLowerCase(key) && Character.isAlphabetic(key)).isTrue());
    }
}
