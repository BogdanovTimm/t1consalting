package org.bogdanovtimm.t1consalting.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bogdanovtimm.t1consalting.form.CharCounterForm;
import org.bogdanovtimm.t1consalting.response.HttpResponse;
import org.bogdanovtimm.t1consalting.service.CharCounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Provides REST API for counting characters in a given text.
 */
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
@Tag(name = "Char Counter", description = "REST API for counting chars in given text")
public class CharCounterRest {

    private final CharCounterService charCounterService;

    /**
     * Counts characters in given non-blank text.
     * Opitions:
     * - case-sensitive
     * - trim all whitespaces
     *
     * @param form - given text, whether chars should be counted case-sensitive,
     * whether is should trim all whitespaces. Must not be non-empty.
     * @return {@code HttpResponse} with counted characters;
     */
    @PostMapping("/countchars")
    @Operation(summary = "Counts chars in given non-blank text. Options: case-sensitive, trim spaces")
    public ResponseEntity<HttpResponse> countChars(
                                                                 @RequestBody
                                                                 @Valid
                                                                 @Parameter(description = "Given non-empty text")
                                                                 CharCounterForm form) {
        var mapOfChars = charCounterService.countChars(form);
        return ResponseEntity.ok()
                             .body(HttpResponse.builder()
                                               .timeStamp(LocalDateTime.now().toString())
                                               .statusCode(HttpStatus.OK.value())
                                               .status(HttpStatus.OK)
                                               .data(mapOfChars)
                                               .build());
    }
}
