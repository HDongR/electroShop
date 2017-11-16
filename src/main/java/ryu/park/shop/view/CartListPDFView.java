package ryu.park.shop.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ryu.park.shop.utils.ResourcesUtils;

public class CartListPDFView extends AbstractPdfView{
	

	public static final String FONT_PATH = "/fonts/GulimChe.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseFont font = ResourcesUtils.getIdentityFont(FONT_PATH);
		Font f = new Font(font, 12);
		PdfPTable table = new PdfPTable(2);
		table.addCell("Winansi");
		table.addCell(new Phrase("x x테스트", f));
		document.add(new Chunk("Title: "));
		document.add(table);

	}



}
