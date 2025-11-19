package com.example.ms2;


import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.*;
import org.openapitools.client.auth.*;
import org.openapitools.client.model.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

public class ApiClientTest {


    @Test
    void callIsDone() {

        ApiClient defaultClient = new ApiClient();
        defaultClient.setBasePath("http://localhost:8090/ets");

        var result = new DefaultApi(defaultClient).getDataSheet("xxx", "www", "es").block();

        System.out.printf("result: %s\n", result);

    }

}
