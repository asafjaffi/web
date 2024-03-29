package com.jaffi.app.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
public class GsonJsonProvider implements MessageBodyReader<Object>,
		MessageBodyWriter<Object> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
	    try {
	      Type jsonType;
	      if (type.equals(genericType)) {
	        jsonType = type;
	      } else {
	        jsonType = genericType;
	      }
	      getGson().toJson(t, jsonType, writer);
	    } finally {
	      writer.close();
	    }
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
	    try {
	      Type jsonType;
	      if (type.equals(genericType)) {
	        jsonType = type;
	      } else {
	        jsonType = genericType;
	      }
	      return getGson().fromJson(streamReader, jsonType);
	    } finally {
	      streamReader.close();
	    }
	}
	
	private static final String UTF_8 = "UTF-8";
	 
	  private Gson gson;
	 
	  private Gson getGson() {
	    if (gson == null) {
	      final GsonBuilder gsonBuilder = new GsonBuilder();
	      gson = gsonBuilder.create();
	    }
	    return gson;
	  }
}
