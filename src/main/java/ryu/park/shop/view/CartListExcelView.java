package ryu.park.shop.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import ryu.park.shop.vo.CartVO;

public class CartListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	 
	//	response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
		Sheet sheet = workbook.createSheet("sheet 1");

		@SuppressWarnings("unchecked")
		Map<Integer, CartVO> cartList = (HashMap<Integer, CartVO>) model.get("cartList");

		Row row = null;
		Cell cell = null;
		int r = 0;
		int c = 0;

		// Style for header cell
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		// Create header cells
		row = sheet.createRow(r++);

		cell = row.createCell(c++);
		cell.setCellStyle(style);
		cell.setCellValue("상품명");

		cell = row.createCell(c++);
		cell.setCellStyle(style);
		cell.setCellValue("사진URL");

		cell = row.createCell(c++);
		cell.setCellStyle(style);
		cell.setCellValue("가격");

		cell = row.createCell(c++);
		cell.setCellStyle(style);
		cell.setCellValue("수량");

		// Create data cell
		for (Integer key : cartList.keySet()) {
			row = sheet.createRow(r++);
			c = 0;
			row.createCell(c++).setCellValue(cartList.get(key).getGoodsVO().getGoodsSubject());
			row.createCell(c++).setCellValue(cartList.get(key).getGoodsVO().getGoodsMainPicUrl());
			row.createCell(c++).setCellValue(cartList.get(key).getGoodsVO().getGoodsCost());
			row.createCell(c++).setCellValue(cartList.get(key).getCartGoodsCnt());

		}
		for (int i = 0; i < 4; i++)
			sheet.autoSizeColumn(i, true);

	}

}
