package com.example.hypeadvice.domain.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateDeserializer extends JsonDeserializer<Date> {

	private enum DateFormat {

		DATE_TIME0("dd/MM/yyyy HH:mm:ss"),
		ISO_8601("yyyy-MM-dd'T'HH:mm:ssz"),
		DATE_TIME1("dd/MM/yyyy HH:mm"),
		DATE_TIME2("yyyy-MM-dd HH:mm:ss"),
		DATE_TIME3("dd/MM/yyyy HH:mm:SSS");

		private SimpleDateFormat sdf;

		DateFormat(String pattern) {
			sdf = new SimpleDateFormat(pattern);
		}

		public SimpleDateFormat getSdf() {
			return sdf;
		}
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		String text = parser.getText();
		if (StringUtils.isBlank(text)) {
			return null;
		}
		else {

			Exception exception = null;
			DateFormat[] values = DateFormat.values();
			for (int i = 0; i < values.length; i++) {

				DateFormat df = values[i];
				SimpleDateFormat sdf = df.getSdf();
				try {
					Date parse = sdf.parse(text);
					return parse;
				}
				catch (ParseException e) {
					exception = e;
				}
			}

			if(exception != null) {
				exception.printStackTrace();
			}

			throw new IOException("data em formato inválido: " + text);
		}
	}
}