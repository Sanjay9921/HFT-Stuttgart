package sample.client.infrastructure.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConverter {
	
	@SuppressWarnings("serial")
	public static class JsonProcessingRuntimeException extends RuntimeException {

		public JsonProcessingRuntimeException(Throwable cause) {
			super(cause);
		}		
	}
	
	private final ObjectMapper mapper;
	
	public JsonConverter() {
		this.mapper = new ObjectMapper();
		this.mapper.registerModule(new JavaTimeModule());		
	}

	public String toJson(Object object) {
		try {
			String json = mapper.writeValueAsString(object);
			return json;
		} catch (JsonProcessingException e) {
			throw new JsonProcessingRuntimeException(e);
		}
	}

	public <T> T toEntity(String json, Class<T> cls) {
		try {
			T entity = mapper.readValue(json, cls);
			return entity;
		} catch (JsonProcessingException e) {
			throw new JsonProcessingRuntimeException(e);
		}
	}

	public <T> T toEntity(String json, TypeReference<T> typeRef) {
		try {
			T entity = mapper.readValue(json, typeRef);
			return entity;
		} catch (JsonProcessingException e) {
			throw new JsonProcessingRuntimeException(e);
		}
	}
}
