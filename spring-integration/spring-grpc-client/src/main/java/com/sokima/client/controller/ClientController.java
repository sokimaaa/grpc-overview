package com.sokima.client.controller;

import com.sokima.client.service.AggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final AggregatorService service;

    @GetMapping("/client/grpc-call")
    public List<?> get() {
        return service.doJob();
    }
}
