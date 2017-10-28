package ryu.park.shop.utils;

import org.springframework.core.convert.converter.Converter;

import ryu.park.shop.type.OrderType;

public class EnumConverter implements Converter<String, OrderType>{

	@Override
	public OrderType convert(String source) {
		 try {
	          return OrderType.valueOf(source);
	       } catch(Exception e) {
	          return OrderType.SEQ;
	       }
	}

}
