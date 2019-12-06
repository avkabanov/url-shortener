package com.kabanov.shortner.controller;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabanov.shortner.controller.request.ShortenUrlRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Kabanov Alexey
 */
@RunWith(SpringRunner.class)
@ImportResource("classpath:spring-config.xml")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ShortenerControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void shouldCreateShortenUrlAndRedirectByShorten() throws Exception {
        String fullUrl = "google.com";
        String shortUrl = createShortUrl(fullUrl);

        MockHttpServletRequestBuilder request = get("/" + shortUrl);
        mockMvc
                .perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, fullUrl));
    }

    private String createShortUrl(String fullUrl) throws Exception {
        ShortenUrlRequest shortenUrlRequest = new ShortenUrlRequest(fullUrl);
        String content = objectMapper.writeValueAsString(shortenUrlRequest);

        MockHttpServletRequestBuilder request = post("/url/short")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        AtomicReference<String> shortUrl = new AtomicReference<>();
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andDo(result -> {
                    String response = result.getResponse().getContentAsString();
                    shortUrl.set(response);
                });
        return shortUrl.get();
    }
}