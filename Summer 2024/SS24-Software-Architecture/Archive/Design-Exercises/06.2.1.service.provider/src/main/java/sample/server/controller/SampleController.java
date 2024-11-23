package sample.server.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import sample.server.application.SampleApp;

@OpenAPIDefinition(
	    info = @Info(
	        title = "Sample-Application",
	        description = "A simple Web Service to demonstrate Rest-Services.",
	        version = "1.0"
	    )
	)

// Call Swagger with:  http://localhost:port/context/swagger-ui.html
// Call Endpoint with: http://localhost:port/context/endpoint

@RestController
public class SampleController {

	private final SampleApp app;

	@Autowired
	public SampleController(SampleApp app) {
		this.app = app;
	}

	//	Call with: http://localhost:port/context
	@Operation(summary = "Status message", description = "Returns a status message.")
	@GetMapping(path="/", produces = "text/plain")
	public String status() {
		return "The server is up an running";
	}

	@Operation(summary = "Hello message", description = "Returns a hello message.")
	@GetMapping(path="hello", produces = "text/plain")
	public String hello() {
		return app.getHello(null);
	}

	@Operation(summary = "Hello message", description = "Returns a hello message with the given name.")
	@GetMapping(path="hello/{name}", produces = "text/plain")
	public String hello(@PathVariable("name") String name) {
		return app.getHello(name);
	}

	@Operation(summary = "Returns the current time", description = "Returns the current server-time")
    @GetMapping(path="time", produces = "application/json")
	public LocalDateTime time() {
		return app.getTime();
	}
}
