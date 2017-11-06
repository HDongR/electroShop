package ryu.park.shop.utils;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

public class ResourcesUtils {

	public static BaseFont getIdentityFont(String path) throws DocumentException, IOException {
		
		String fontPath = ResourcesUtils.class.getResource(path).toExternalForm();
		if (fontPath == null)
			return null;
		BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		return baseFont;
	}
}
