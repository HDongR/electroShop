package ryu.park.shop.viewResolver;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver; 

import ryu.park.shop.view.CartListPDFView;

public class PdfViewResolver implements ViewResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfViewResolver.class);
	 
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception{ 
    		View view = null;
    		switch(viewName) {
    		case "cart/cart_estimate_page.pdf": 
    			view = new CartListPDFView();
    			break;
    		}
       
        return view;
    }
    
}