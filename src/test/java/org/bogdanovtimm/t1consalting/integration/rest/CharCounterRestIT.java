package org.bogdanovtimm.t1consalting.integration.rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.bogdanovtimm.t1consalting.util.IntegrationTestBase;
import org.hamcrest.Matchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.Test;
import lombok.RequiredArgsConstructor;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CharCounterRestIT extends IntegrationTestBase {

    /**
     * Special mock for Resouce
     */
    private final MockMvc mockMvc;

    @Test
    void countCharsForText() throws Exception {
        var form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        String textBody = "{\"text\": \"HeLlo wOrlD\"}";
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        sortedMap.put('l', 3);
        sortedMap.put('o', 2);
        sortedMap.put('h', 1);
        sortedMap.put('e', 1);
        sortedMap.put(' ', 1);
        sortedMap.put('w', 1);
        sortedMap.put('r', 1);
        sortedMap.put('d', 1);
        List<Integer> sortedValues = sortedValues(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data.*").value(sortedValues));

    }

    @Test
    void countCharsForTextSensitive() throws Exception {
        var form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setCaseSensitive(true);
        String textBody = "{\"text\": \"HeLlo wOrlD\", \"caseSensitive\": true}";
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        sortedMap.put('l', 2);
        sortedMap.put('H', 1);
        sortedMap.put('e', 1);
        sortedMap.put('L', 1);
        sortedMap.put('o', 1);
        sortedMap.put(' ', 1);
        sortedMap.put('w', 1);
        sortedMap.put('O', 1);
        sortedMap.put('r', 1);
        sortedMap.put('D', 1);
        List<Integer> sortedValues = sortedValues(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data.*").value(sortedValues));
    }

    @Test
    void countCharsForTextTrimSpaces() throws Exception {
        var form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setTrimSpaces(true);
        String textBody = "{\"text\": \"HeLlo wOrlD\", \"trimSpaces\": true}";
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        sortedMap.put('l', 3);
        sortedMap.put('o', 2);
        sortedMap.put('h', 1);
        sortedMap.put('e', 1);
        sortedMap.put('w', 1);
        sortedMap.put('r', 1);
        sortedMap.put('d', 1);
        List<Integer> sortedValues = sortedValues(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data.*").value(sortedValues));
    }

    @Test
    void countCharsForTextSensitiveTrimSpaces() throws Exception {
        var form = new CharCounterForm();
        form.setText("HeLlo wOrlD");
        form.setCaseSensitive(true);
        form.setTrimSpaces(true);
        String textBody = "{\"text\": \"HeLlo wOrlD\", \"caseSensitive\": true, \"trimSpaces\": true}";
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        sortedMap.put('l', 2);
        sortedMap.put('H', 1);
        sortedMap.put('e', 1);
        sortedMap.put('L', 1);
        sortedMap.put('o', 1);
        sortedMap.put('w', 1);
        sortedMap.put('O', 1);
        sortedMap.put('r', 1);
        sortedMap.put('D', 1);
        List<Integer> sortedValues = sortedValues(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("200"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data.*").value(sortedValues));
    }

    @Test
    void countCharsForEmptyText() throws Exception {
        String textBody = "{\"text\": \"\"}";
        var form = new CharCounterForm();
        form.setText("");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("400"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionReason").value("Text must not be blank"));
    }

    @Test
    void countCharsForBlankTextWithRemovingOfSpaces() throws Exception {
        String blankBody = "{\"text\": \" \", \"trimSpaces\": true}";
        var form = new CharCounterForm();
        form.setText(" ");
        form.setTrimSpaces(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(blankBody))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("400"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionReason").value("After removing spaces there is nothing to count"));
    }

    private List<Integer> sortedValues(Map<Character, Integer> unsortedMap) {
        return unsortedMap.values()
                          .stream()
                          .sorted(Comparator.reverseOrder())
                          .collect(Collectors.toCollection(LinkedList::new));
    }

}
