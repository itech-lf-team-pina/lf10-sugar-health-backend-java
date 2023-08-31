package com.health.sugar.lf10sugarhealth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @GetMapping(
            path = "/health"
    )
    public String getHealth() {
        System.out.println("Ist gut");
        return "OK";
    }
}
