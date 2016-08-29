/**
 * 
 */
package com.tesco.service;

import java.io.IOException;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author 
 *
 * @param <T>
 */
public class JsonConverter<T> {

	private ObjectMapper mapper;

	/**
	 * 
	 * @param docId
	 * @param t
	 * @return
	 * @throws IOException
	 */
	public JsonDocument serialize(String docId, T t) throws IOException {
		String json = getObjectMapper().writeValueAsString(t);
		JsonNode jsonNode = getObjectMapper().readTree(json);
		((ObjectNode) jsonNode).put("_type", t.getClass().getName());
		json = mapper.writeValueAsString(jsonNode);
		JsonObject object = JsonObject.fromJson(json);
		
		return JsonDocument.create(docId, object);
	}

	/**
	 * 
	 * @param jsonData
	 * @param entity
	 * @return 
	 * @return
	 * @throws IOException
	 */
	public T deSerialize(String jsonData, Class<T> entity) throws IOException {
		return getObjectMapper().readValue(jsonData, entity);
	}
	
	/**
	 * 
	 * @return
	 */
	private ObjectMapper getObjectMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		return mapper;
	}

}
