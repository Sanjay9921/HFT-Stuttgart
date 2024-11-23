package sample.server.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class SampleApp {

	public String getHello(String name) {
		if(name == null || name.isBlank())
			return "Hello World!";
		else
			return "Hello " + name + "!";
	}
	
	public LocalDateTime getTime() {
		return LocalDateTime.now();
	}
}
