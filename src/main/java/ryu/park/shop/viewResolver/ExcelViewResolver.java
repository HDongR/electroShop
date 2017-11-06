package ryu.park.shop.viewResolver;
 
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import ryu.park.shop.view.CartListExcelView;

public class ExcelViewResolver implements ViewResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelViewResolver.class);
	 
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	View view = null;
		switch(viewName) {
		case "cart/cart_estimate_page.xls": 
			view = new CartListExcelView();
			break;
		}
        return view;
    }
    
    
}
