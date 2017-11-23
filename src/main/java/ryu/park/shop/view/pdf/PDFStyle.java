package ryu.park.shop.view.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class PDFStyle {

	protected static PdfPCell createCell(String name, Font font, int horizontalAlign, int verticalAlign, BaseColor backColor, int row, int col, boolean isBorder) {
		PdfPCell cell = new PdfPCell(new Phrase(name, font));
		cell.setHorizontalAlignment(horizontalAlign);
		cell.setVerticalAlignment(verticalAlign); 
		cell.setBackgroundColor(backColor);
		cell.setRowspan(row);
		cell.setColspan(col);
		cell.setBorder(isBorder ? PdfPCell.BOX : PdfPCell.NO_BORDER);
		cell.setFixedHeight(32f);
		
		return cell;
	} 

	protected static Paragraph createPara(String name, Font font, float leading, float beforeSpacing, int horizontalAlign) {
		Paragraph para = new Paragraph(name, font);
		para.setSpacingBefore(beforeSpacing);
		para.setLeading(leading);
		para.setAlignment(horizontalAlign);
		
		return para;
	} 
}
