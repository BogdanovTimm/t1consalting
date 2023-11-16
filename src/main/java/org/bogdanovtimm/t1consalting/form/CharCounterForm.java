package org.bogdanovtimm.t1consalting.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Form:
 * given text,
 * whether counting should be case-sensitive,
 * whether it shoudl count white spaces
 */
@Getter
@Setter
@EqualsAndHashCode
public class CharCounterForm {

    @NotEmpty(message = "Text must not be blank")
    @Schema(example = "Lorem Ipsum")
    private String text;

    @Schema(example = "true", description = "Default = false")
    private boolean caseSensitive;

    @Schema(example = "true", description = "Default = false")
    private boolean trimSpaces;
}
