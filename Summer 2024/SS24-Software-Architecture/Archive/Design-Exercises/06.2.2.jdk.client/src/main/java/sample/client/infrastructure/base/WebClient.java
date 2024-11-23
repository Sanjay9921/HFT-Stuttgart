package sample.client.infrastructure.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

public class WebClient {
	
	@SuppressWarnings("serial")
	public static class WebClientException extends RuntimeException {

		public WebClientException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	public static enum HttpMethod {		
		DELETE, GET, PUT, POST
	}
	
	private final HttpClient client;	
	private final JsonConverter converter = new JsonConverter();
	
	public WebClient() {
		this.client = HttpClient.newHttpClient();
	}
	
	public WebClient(String keyStoreLocation, String keyStoreType, char[] keyStorePassword) {
		this.client = HttpClient.newBuilder()
                .sslContext(sslContext(keyStoreLocation, keyStoreType, keyStorePassword))
                .build();
	}
	
	// see. https://stackoverflow.com/questions/21794117/java-security-cert-certificateparsingexception-signed-fields-invalid
	// see: https://stackoverflow.com/questions/2893819/accept-servers-self-signed-ssl-certificate-in-java-client
	private SSLContext sslContext(String keyStoreLocation, String keyStoreType, char[] keyStorePassword) {
		try(InputStream is = this.getClass().getResourceAsStream(keyStoreLocation)){
			
	    	KeyStore keystore = KeyStore.getInstance(keyStoreType);
	    	keystore.load(is, keyStorePassword);

			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keystore);

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
			
			return sslContext;
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
			throw new WebClientException("Failed to initalize SSLContext", e);
		}
	}

	private Map<String, String> jsonHeader(Map<String, String> headers){
		Map<String, String> jsonHeaders = new HashMap<>(headers);
		for(String key : headers.keySet())
			if("content-type".equals(key.toLowerCase())) {
				jsonHeaders.put(key, "application/json"); // overwrite in any case
				return jsonHeaders;
			}
		
		jsonHeaders.put("content-type", "application/json"); // add if missing
		return jsonHeaders;
	}
	
    private BodyPublisher bodyPublisher(Object object) {
        String json = converter.toJson(object);
        return BodyPublishers.ofString(json);
    }

	private HttpRequest buildRequest(String uri, HttpMethod method, Map<String, String> headers, Object body) {
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(uri));
		
		requestBuilder = switch(method) {
			case DELETE	-> requestBuilder.DELETE();
			case GET	-> requestBuilder.GET();
			case POST	-> requestBuilder.POST(bodyPublisher(body));
			case PUT	-> requestBuilder.PUT(bodyPublisher(body));
			};
	    
		for(String key : headers.keySet())
			requestBuilder = requestBuilder.header(key, headers.get(key));
	
	    HttpRequest request = requestBuilder.build();
		return request;
	}

	public HttpResponse<String> request(String uri, HttpMethod method, Map<String, String> headers) {
	    HttpRequest request = buildRequest(uri, method, headers, null);		
        try {
			return client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// see: https://stackoverflow.com/questions/63397838/create-a-dummy-java-net-http-httpresponse-without-making-an-http-request
			return new HttpErrorResponse<>(500, e, request);
		}
	}
	
	public HttpResponse<String> request(String uri, HttpMethod method, Map<String, String> headers, Object body) {
	    HttpRequest request = buildRequest(uri, method, headers, body);		
        try {
			return client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// see: https://stackoverflow.com/questions/63397838/create-a-dummy-java-net-http-httpresponse-without-making-an-http-request
			return new HttpErrorResponse<>(500, e, request);
		}
	}
	
	public <T> HttpResponse<T> request(String uri, HttpMethod method, Map<String, String> headers, Object body, Class<T> returnType) {
	    HttpRequest request = buildRequest(uri, method, jsonHeader(headers), body);		
        try {
        	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        	T entity = converter.toEntity(response.body(), returnType);
        	return new HttpJsonResponse<T>(response, entity);
		} catch (IOException | InterruptedException e) {
			// see: https://stackoverflow.com/questions/63397838/create-a-dummy-java-net-http-httpresponse-without-making-an-http-request
			return new HttpErrorResponse<>(500, e, request);
		}
	}
	
	public <T> HttpResponse<T> request(String uri, HttpMethod method, Map<String, String> headers, Object body, TypeReference<T> returnType) {
	    HttpRequest request = buildRequest(uri, method, jsonHeader(headers), body);		
        try {
        	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        	T entity = converter.toEntity(response.body(), returnType);
        	return new HttpJsonResponse<T>(response, entity);
		} catch (IOException | InterruptedException e) {
			// see: https://stackoverflow.com/questions/63397838/create-a-dummy-java-net-http-httpresponse-without-making-an-http-request
			return new HttpErrorResponse<>(500, e, request);
		}
	}
}
