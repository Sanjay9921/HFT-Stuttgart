package sample.client.infrastructure.base;

import javax.net.ssl.SSLSession;

import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpJsonResponse<T> implements HttpResponse<T> {

	private final HttpResponse<String> originalResponse;
	private final T newBody;
	
	public HttpJsonResponse(HttpResponse<String> originalResponse, T newBody) {
		this.originalResponse = originalResponse;
		this.newBody = newBody;
	}
		
	@Override
	public int statusCode() {
		return originalResponse.statusCode();
	}
	
	@Override
	public HttpRequest request() {
		return originalResponse.request();
	}
	
	@Override
	public Optional<HttpResponse<T>> previousResponse() {
		return Optional.empty();
	}
	
	@Override
	public HttpHeaders headers() {
		return originalResponse.headers();
	}
	
	@Override
	public T body() {
		return newBody;
	}
	
	@Override
	public Optional<SSLSession> sslSession() {
		return originalResponse.sslSession();
	}
	@Override
	public URI uri() {
		return originalResponse.uri();
	}
	
	@Override
	public Version version() {
		return originalResponse.version();
	}
	
    @Override
    public String toString() {
        return originalResponse.toString();
    }
}