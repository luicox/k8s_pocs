package com.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

@Path("/greeting")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> greeting() {
        return Map.of("message", "Hello from Quarkus Greeting Service! XXX");
    }
}