package ryu.park.shop.viewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import ryu.park.shop.view.CartListPDFView;

public class PdfViewResolver implements ViewResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfViewResolver.class);
	private Map<String, View> pdfViewMap;
	 
	public PdfViewResolver() {
		super();
		pdfViewMap = new HashMap<>();
		pdfViewMap.put("doc/cart_estimate_download.pdf", new CartListPDFView());
	}
	
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception{
        return pdfViewMap.get(viewName);
    }
    
}