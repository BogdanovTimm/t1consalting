package org.bogdanovtimm.t1consalting.response;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {

    @Schema(example = "2023-11-14T22:46:08.312986500")
    protected String timeStamp;

    @Schema(example = "200")
    protected int statusCode;

    @Schema(example = "OK")
    protected HttpStatus status;

    @Schema(example = "Text must not be blank")
    protected String exceptionReason;
    
    @Hidden
    protected String developerMessage;

    @Schema(example = "\"a\": 1")
    protected Map<?, ?> data;
}
