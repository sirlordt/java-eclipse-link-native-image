package com.jpa.example.converter;

import java.util.HashMap;
//import java.util.Map;

//import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//import jakarta.json.Json;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//
@Converter //(autoApply = true)
public class JpaConverterJson implements AttributeConverter<HashMap<String, Object>, String> {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(HashMap<String, Object> meta) {
    try {
      if ( meta==null ) return null; // meta = new HashMap<String,Object>();
      return objectMapper.writeValueAsString(meta);
    } 
    catch (JsonProcessingException ex) {
      return null;
      // or throw an error
    }
  }

  @Override
  public HashMap<String, Object> convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, new TypeReference<HashMap<String, Object>>() {});
    }
    catch (Exception ex) {
      // logger.error("Unexpected IOEx decoding json from database: " + dbData);
      return null;
    }
  }

}