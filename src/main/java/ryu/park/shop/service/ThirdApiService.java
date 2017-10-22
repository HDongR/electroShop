package ryu.park.shop.service;

import java.util.HashMap;

public interface ThirdApiService {
	public HashMap<String, Object> getPostAndAddress(String name, int currentPage);
}
