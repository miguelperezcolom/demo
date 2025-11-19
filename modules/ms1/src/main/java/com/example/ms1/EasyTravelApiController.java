package com.example.ms1;

import org.openapitools.api.DefaultApi;
import org.openapitools.model.GetDataSheetRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ets")
class EasyTravelApiController implements DefaultApi {


    @Override
    public Mono<ResponseEntity<GetDataSheetRS>> getDataSheet(String authtoken, String resourceid, String language, ServerWebExchange exchange) {
        return Mono.just(new ResponseEntity<>(new GetDataSheetRS(), HttpStatus.OK));
    }
}
