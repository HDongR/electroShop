package ryu.park.shop.viewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import ryu.park.shop.view.excel.CartListExcelView;

public class ExcelViewResolver implements ViewResolver {

	private static final Logger logger = LoggerFactory.getLogger(ExcelViewResolver.class);
	private Map<String, View> excelViewMap;
	
	public ExcelViewResolver() {
		super();
		excelViewMap = new HashMap<String, View>();
		excelViewMap.put("doc/cart_estimate_download.xls", new CartListExcelView());
	}
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return excelViewMap.get(viewName);
	}

}
