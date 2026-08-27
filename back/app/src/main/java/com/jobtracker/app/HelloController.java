package com.jobtracker.app;

import com.jobtracker.app.common.NotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloController {

    public record HelloResponse(String message) {}

    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello, JobTracker!");
    }

    @GetMapping("/kaboom")
    public String boom() {
        throw new NotFoundException("Application", 42);
    }
}