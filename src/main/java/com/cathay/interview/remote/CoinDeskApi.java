package com.cathay.interview.remote;

import com.cathay.interview.dto.CurrencyInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("api")
public class CoinDeskApi {

    public static final String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/download")
    public ResponseEntity<String> downloadData() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CurrencyInfoDto> request = new HttpEntity<>(null, headers);

        return restTemplate.exchange(
                URL,
                HttpMethod.GET,
                request,
                String.class
        );
    }
}
