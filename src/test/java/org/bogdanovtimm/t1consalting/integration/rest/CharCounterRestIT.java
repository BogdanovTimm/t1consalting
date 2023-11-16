package org.bogdanovtimm.t1consalting.integration.rest;

import org.bogdanovtimm.t1consalting.util.IntegrationTestBase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import lombok.RequiredArgsConstructor;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CharCounterRestIT extends IntegrationTestBase {
    
    /**
     * Special mock for Resouce
     */
    private final MockMvc mockMvc;

    
}
