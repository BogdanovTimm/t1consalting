package org.bogdanovtimm.t1consalting.unit.rest;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bogdanovtimm.t1consalting.exception.CharCounterExceptionHandler;
import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.bogdanovtimm.t1consalting.rest.CharCounterRest;
import org.bogdanovtimm.t1consalting.service.CharCounterService;
import org.bogdanovtimm.t1consalting.util.UnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lombok.RequiredArgsConstructor;

@WebMvcTest(CharCounterRest.class)
@RequiredArgsConstructor
public class CharCounterRestTest extends UnitTestBase {

    private MockMvc mockMvc;

    @MockBean
    private final CharCounterService service;

    @MockBean
    private final CharCounterExceptionHandler exceptionHandler;

    private final CharCounterRest rest;

    @BeforeEach
    void addExceptionHandler() {
        mockMvc = MockMvcBuilders.standaloneSetup(rest)
                                 .setControllerAdvice(new CharCounterExceptionHandler())
                                 .build();
    }

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
        Mockito.when(service.countChars(form)).thenReturn(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
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
        Mockito.when(service.countChars(form)).thenReturn(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
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
        Mockito.when(service.countChars(form)).thenReturn(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
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
        Mockito.when(service.countChars(form)).thenReturn(sortedMap);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(textBody))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("$.data.*").value(sortedValues));
    }

    private List<Integer> sortedValues(Map<Character, Integer> unsortedMap) {
        return unsortedMap.values()
                          .stream()
                          .sorted(Comparator.reverseOrder())
                          .collect(Collectors.toCollection(LinkedList::new));
    }
}
