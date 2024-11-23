package sample.client.assembly;


import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;

import sample.client.infrastructure.base.WebClient;
import sample.client.infrastructure.base.WebClient.HttpMethod;
import sample.client.infrastructure.properties.ApplicationProperties;

public class JDKSampleClientRun {
	
	public static void main(String[] args) {
		ApplicationProperties properties = ApplicationProperties.instance();
			
		WebClient client = new WebClient();
		String url = properties.get("service.url");
		
		HttpResponse<String> response;
		
		response = client.request(url, HttpMethod.GET, Map.of("Content-Type", "text/plain"));
		System.out.println(response + (response.statusCode() == 200 ? " : " + response.body() : ""));
		
		response = client.request(url + "hello", HttpMethod.GET, Map.of("Content-Type", "text/plain"));
		System.out.println(response + (response.statusCode() == 200 ? " : " + response.body() : ""));

		response = client.request(url + "hello/John-Doe", HttpMethod.GET, Map.of("Content-Type", "text/plain"));
		System.out.println(response + (response.statusCode() == 200 ? " : " + response.body() : ""));

		HttpResponse<LocalDateTime> httpResponse;
		httpResponse = client.request(url + "time", HttpMethod.GET, Map.of("Content-Type", "application/json"), null, LocalDateTime.class);
		System.out.println(httpResponse + (httpResponse.statusCode() == 200 ? " : " + httpResponse.body() : ""));
	}
        
 }
