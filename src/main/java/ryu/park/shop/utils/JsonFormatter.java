package ryu.park.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum JsonFormatter {
	  INSTANCE;
	  private final ObjectMapper mapper = new ObjectMapper();

	  private JsonFormatter(){
	    // Perform any configuration on the ObjectMapper here.
	  }

	  public ObjectMapper getObjectMapper() {
	    return mapper;
	  }
	}