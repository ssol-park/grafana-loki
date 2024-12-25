package com.psr.promtailloki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping
    public String get() {
        logger.info("[ApiController] GET Request Success !");
        IntStream.rangeClosed(0, 100)
                .forEach(i -> logger.info("########## Print log .. {}", i));
        return "Success";
    }
}
