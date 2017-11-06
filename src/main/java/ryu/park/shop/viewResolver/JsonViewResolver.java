package ryu.park.shop.viewResolver;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * View resolver for returning JSON in a view-based system. Always returns a
 * {@link MappingJacksonJsonView}.
 */
public class JsonViewResolver implements ViewResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonViewResolver.class);

	/**
	 * Get the view to use.
	 * 
	 * @return Always returns an instance of {@link MappingJacksonJsonView}.
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		view.setPrettyPrint(true);
		return view;
	}

}