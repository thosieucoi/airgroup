package com.airgroup.helper;

import java.io.IOException;

import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TimeZoneHelper
{
	 public static class Deserializer extends JsonDeserializer<DateTimeZone>
	  {
	    public DateTimeZone deserialize(JsonParser jp, DeserializationContext ctxt)
	      throws IOException, JsonProcessingException
	    {
	      return DateTimeZone.forID(jp.getText());
	    }
	  }

	  public static class Serializer extends JsonSerializer<DateTimeZone>
	  {
	    public void serialize(DateTimeZone value, JsonGenerator jgen, SerializerProvider provider)
	      throws IOException, JsonProcessingException
	    {
	      if (value == null)
	        jgen.writeNull();
	      else
	        jgen.writeString(value.toString());
	    }
	  }
}
