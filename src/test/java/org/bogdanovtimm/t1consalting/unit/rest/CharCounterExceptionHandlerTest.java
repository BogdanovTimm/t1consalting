package org.bogdanovtimm.t1consalting.unit.rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.bogdanovtimm.t1consalting.exception.ApiException;
import org.bogdanovtimm.t1consalting.exception.CharCounterExceptionHandler;
import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.bogdanovtimm.t1consalting.rest.CharCounterRest;
import org.bogdanovtimm.t1consalting.service.CharCounterService;
import org.bogdanovtimm.t1consalting.util.UnitTestBase;
import org.hamcrest.Matchers;
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
public class CharCounterExceptionHandlerTest extends UnitTestBase {

    private MockMvc mockMvc;

    @MockBean
    private final CharCounterService service;

    private final CharCounterRest rest;

    @BeforeEach
    void addExceptionHandler() {
        mockMvc = MockMvcBuilders.standaloneSetup(rest)
                                 .setControllerAdvice(new CharCounterExceptionHandler())
                                 .build();
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
        Mockito.when(service.countChars(form)).thenThrow(new ApiException("After removing spaces there is nothing to count"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/countchars")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(blankBody))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").value(Matchers.startsWith(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("400"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionReason").value("After removing spaces there is nothing to count"));
        ;
    }
}
