package sample.client.infrastructure.base;

import javax.net.ssl.SSLSession;

import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

// see: https://stackoverflow.com/questions/63397838/create-a-dummy-java-net-http-httpresponse-without-making-an-http-request
public class HttpErrorResponse<T> implements HttpResponse<T> {

	private final int statusCode;
	private final String message;
	private final HttpRequest request;
	
	public HttpErrorResponse(int statusCode, HttpRequest request) {
		this(statusCode, (String)null, request);
	}

	public HttpErrorResponse(int statusCode, Throwable throwable, HttpRequest request) {
		this(statusCode, (throwable.getMessage() != null ? throwable.getMessage() : throwable.getClass().getCanonicalName()), request);
	}
	
	public HttpErrorResponse(int statusCode, String message, HttpRequest request) {
		if(statusCode == 200)
			throw new IllegalArgumentException("Response only for error codes");
		
		this.statusCode = statusCode;
		this.message = message;
		this.request = request;
	}
	
	@Override
	public int statusCode() {
		return statusCode;
	}
	
	@Override
	public HttpRequest request() {
		return request;
	}
	
	@Override
	public Optional<HttpResponse<T>> previousResponse() {
		return Optional.empty();
	}
	
	@Override
	public HttpHeaders headers() {
		return request.headers();
	}
	
	@Override
	public T body() {
		return null;
	}
	
	@Override
	public Optional<SSLSession> sslSession() {
		return Optional.empty();
	}
	@Override
	public URI uri() {
		return request.uri();
	}
	
	@Override
	public Version version() {
		return request.version().get();
	}
	
    @Override
    public String toString() {
        String method	= request().method();
        URI uri			= request().uri();
        String uriStr	= uri == null ? "" : uri.toString();
        String msgStr	= message == null || message.isBlank() ? "" : " : " + message;
 
        String str = String.format("(%s %s) %d%s", method, uriStr, statusCode, msgStr);
        return str;
    }
}