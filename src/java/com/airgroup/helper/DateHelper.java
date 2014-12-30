package com.airgroup.helper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHelper {
	public static final DateTimeFormatter FORMATTER = DateTimeFormat
			.forPattern("yyyy-MM-dd");

	public static class Deserializer extends JsonDeserializer<DateTime> {
		public DateTime deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return DateHelper.FORMATTER.parseDateTime(jp.getText());
		}
	}

	public static class Serializer extends JsonSerializer<DateTime> {
		public void serialize(DateTime value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException,
				JsonProcessingException {
			if (value != null)
				jgen.writeString(DateHelper.FORMATTER.print(value));
		}
	}
}