package ryu.park.shop.view.pdf;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ryu.park.shop.utils.BizUtils;
import ryu.park.shop.utils.CurrencyUtils;
import ryu.park.shop.utils.DateUtils;
import ryu.park.shop.utils.ResourcesUtils;
import ryu.park.shop.vo.CartVO;

public class CartListPDFView extends AbstractPdfView {

	private static final Logger logger = LoggerFactory.getLogger(CartListPDFView.class);
	public static final String FONT_PATH = "/fonts/GulimChe.ttf";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = (String) model.get("name");
		String phoneNum = (String) model.get("phoneNum");
		String faxNum = (String) model.get("faxNum"); 

		String title = "견적서";
		String fmdTitle = new String(title.getBytes("UTF-8"), "ISO-8859-1");
		// response.setHeader("Content-Disposition", "attachment; filename=\"" + fmdTitle +".pdf\""); //파일 이름표기후 로컬저장

		BaseFont baseFont = ResourcesUtils.getIdentityFont(FONT_PATH);

		document.add(createTitle(baseFont, title));

		PdfPTable basicInfoTable = new PdfPTable(2);
		basicInfoTable.setWidthPercentage(100);
		basicInfoTable.setWidths(new float[] {0.8f, 1.2f});
		basicInfoTable.setSpacingBefore(40f); 
		
		PdfPCell clientInfoCell = new PdfPCell( createClientInfo(model, baseFont, name, phoneNum, faxNum) );
		clientInfoCell.setPaddingRight(5f);
		clientInfoCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
		clientInfoCell.setFixedHeight(160f);
		
		PdfPCell hostInfoCell = new PdfPCell( createHostInfo(baseFont));
		hostInfoCell.setBorder(PdfPCell.NO_BORDER);
		hostInfoCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); 
		hostInfoCell.setPaddingLeft(20f); 
		
		basicInfoTable.addCell(clientInfoCell);
		basicInfoTable.addCell(hostInfoCell);
		
		Paragraph paras = PDFStyle.createPara("다음과 같이 견적합니다", new Font(baseFont, 12), 0f, 30f, Paragraph.ALIGN_LEFT);
		
		PdfPTable cartTable = createCartTable(model, baseFont);
		cartTable.setSpacingBefore(7);
		 
		Element footerInfo = createFooterInfo(baseFont); 
		
		document.add(basicInfoTable); 
		document.add(paras);
		document.add(cartTable);
		document.add(footerInfo);
	}

	private Element createTitle(BaseFont baseFont, String titleName) {  
		Paragraph p = new Paragraph(titleName, new Font(baseFont, 35));
		p.setAlignment(Paragraph.ALIGN_CENTER); 
		return p;
	}

	private PdfPTable createClientInfo(Map<String, Object> model, BaseFont baseFont, String name, String phoneNum,
			String faxNum) throws DocumentException {
		Font font = new Font(baseFont, 12);

		PdfPTable table = new PdfPTable(2);    
		table.setWidths(new float[]{0.6f, 1.4f});

		table.addCell(PDFStyle.createCell("견적일 :", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
		table.addCell(PDFStyle.createCell(DateUtils.getInstance().now_short(), font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
 
		table.addCell(PDFStyle.createCell("이름 :", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
		table.addCell(PDFStyle.createCell(name, font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
 
		table.addCell(PDFStyle.createCell("견적금액 :", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, null, 1, 1, false));
		table.addCell(PDFStyle.createCell(BizUtils.getInstance().calculCostCartList(2, model) + "원\n(부과세포함, 운송료별도)", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_TOP, null, 1, 1, false));
  
		table.addCell(PDFStyle.createCell("전화번호 :", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
		table.addCell(PDFStyle.createCell(phoneNum, font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
 
		table.addCell(PDFStyle.createCell("팩스번호 :", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));
		table.addCell(PDFStyle.createCell(faxNum, font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, false));

		return table;
	}

	private PdfPTable createHostInfo(BaseFont baseFont) throws DocumentException {
		Font font = new Font(baseFont, 12);

		PdfPTable table = new PdfPTable(5);  
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.setWidths(new float[] {0.3f, 0.9f, 1.5f, 0.4f, 1.2f});
		
		BaseColor grayColor = new BaseColor(180, 180, 180);
		
		table.addCell(PDFStyle.createCell("공\n\r급\n\r자", font, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, grayColor, 5, 1, true)); 
		table.addCell(PDFStyle.createCell("등록번호", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true)); 
		table.addCell(PDFStyle.createCell("101-35-23132", new Font(baseFont, 12, Font.BOLD), PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, null, 1, 3, true));
		table.addCell(PDFStyle.createCell("상호", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("ElectroShop", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		table.addCell(PDFStyle.createCell("대표", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("유호동", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		table.addCell(PDFStyle.createCell("사업장주소", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("세종시 연서면 대첩로", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 3, true));
		table.addCell(PDFStyle.createCell("업태", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("도,소매,제조,서비스", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		table.addCell(PDFStyle.createCell("종목", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("전기,전자부품", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		table.addCell(PDFStyle.createCell("전화", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("010-2967-9441", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		table.addCell(PDFStyle.createCell("팩스", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, grayColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("-", font, PdfPCell.ALIGN_LEFT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
		
		return table;
	}


	private PdfPTable createCartTable(Map<String, Object> model, BaseFont baseFont) throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100); 
		table.setWidths(new float[] {0.3f, 1.75f, 0.65f, 1.4f, 0.5f, 1.4f});
		
		Font hdFont = new Font(baseFont, 13, Font.BOLD);
		Font font = new Font(baseFont, 12);
		
		BaseColor hdColor = new BaseColor(209, 232, 244);
		
		table.addCell(PDFStyle.createCell("No.", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("제품명", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("상품번호", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("단가", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("수량", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true));
		table.addCell(PDFStyle.createCell("합계", hdFont, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true)); 
		
		@SuppressWarnings("unchecked")
		List<CartVO> cartList = (List<CartVO>)model.get("cartList");
		for(int i=0; i<cartList.size(); i++) {
			CartVO c = cartList.get(i);
			table.addCell(PDFStyle.createCell(String.valueOf(i+1), font, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
			table.addCell(PDFStyle.createCell(c.getGoodsVO().getGoodsSubject(), font, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
			table.addCell(PDFStyle.createCell(String.valueOf(c.getCartGoodsSeq()), font, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
			
			PdfPCell costCell = PDFStyle.createCell(CurrencyUtils.toNumFormat(c.getGoodsVO().getGoodsCost()) + " 원", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true);
			costCell.setPaddingRight(10f);
			table.addCell(costCell); 
			
			table.addCell(PDFStyle.createCell(String.valueOf(c.getCartGoodsCnt()), font, PdfPCell.ALIGN_CENTER, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true));
			
			PdfPCell allCostCell = PDFStyle.createCell(CurrencyUtils.toNumFormat(c.getCartGoodsCnt() * c.getGoodsVO().getGoodsCost()) + " 원", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true);
			allCostCell.setPaddingRight(10f);
			table.addCell(allCostCell);
		}
		
		PdfPCell allCostLabelCell = PDFStyle.createCell("공급가액:", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 5, true);
		allCostLabelCell.setPaddingRight(10f);
		table.addCell(allCostLabelCell);
		
		PdfPCell allCostCell = PDFStyle.createCell(BizUtils.getInstance().calculCostCartList(0, model) + " 원", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true);
		allCostCell.setPaddingRight(10f); 
		table.addCell(allCostCell);
		
		PdfPCell taxLabelCell = PDFStyle.createCell("부가세:", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 5, true);
		taxLabelCell.setPaddingRight(10f);
		table.addCell(taxLabelCell);
		
		PdfPCell taxCell = PDFStyle.createCell(BizUtils.getInstance().calculCostCartList(1, model) + " 원", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, null, 1, 1, true);
		taxCell.setPaddingRight(10f);
		table.addCell(taxCell); 
		
		PdfPCell allSumLabelCell = PDFStyle.createCell("견적총액:", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 5, true);
		allSumLabelCell.setPaddingRight(10f);
		table.addCell(allSumLabelCell);
		
		PdfPCell allSumCell = PDFStyle.createCell(BizUtils.getInstance().calculCostCartList(2, model) + " 원", font, PdfPCell.ALIGN_RIGHT, PdfPCell.ALIGN_MIDDLE, hdColor, 1, 1, true);
		allSumCell.setPaddingRight(10f);
		table.addCell(allSumCell); 
		
		return table;
	}
	
	private Element createFooterInfo(BaseFont baseFont) {
		PdfPTable table = new PdfPTable(1); 
		table.setWidthPercentage(95f);
		table.setSpacingBefore(20f);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		
		Font font = new Font(baseFont, 12);
		
		table.addCell(PDFStyle.createPara("※	가격정보가 수시로 변하므로 제품구매시에는 최종 단가를 다시 확인하시기 바랍니다.", font, 0f, 20f, Paragraph.ALIGN_LEFT));
		table.addCell(PDFStyle.createPara("※	부가세 포함가격이며, 배송비용은 별도입니다.", font, 0f, 20f, Paragraph.ALIGN_LEFT));
		table.addCell(PDFStyle.createPara("※	담당자 : 온라인출력", font, 0f, 20f, Paragraph.ALIGN_LEFT));
		table.addCell(PDFStyle.createPara("※	ES Shop 고객센터 : 010-2967-9441", font, 0f, 20f, Paragraph.ALIGN_LEFT));
		table.addCell(PDFStyle.createPara("※	A4 용지에 인쇄시 좌, 우 여백을 10mm로 설정후 인쇄하시기 바랍니다.", font, 0f, 20f, Paragraph.ALIGN_LEFT));
		
		return table;
	} 

}
