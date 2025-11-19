package com.example.ms2;

import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.GetDataSheetRS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
class HelloController {

    @GetMapping
    Mono<GetDataSheetRS> hello() {
        return new DefaultApi().getDataSheet("xxx", "www", "es");
    }

}
