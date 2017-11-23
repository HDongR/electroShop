package ryu.park.shop.view.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import ryu.park.shop.utils.BizUtils;
import ryu.park.shop.utils.CurrencyUtils;
import ryu.park.shop.utils.DateUtils;
import ryu.park.shop.vo.CartVO;

public class CartListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String title = "견적서";
		String fmdTitle = new String(title.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fmdTitle + ".xls\"");
		Sheet sheet = workbook.createSheet(title);

		@SuppressWarnings("unchecked")
		List<CartVO> cartList = (ArrayList<CartVO>) model.get("cartList");
		String name = (String) model.get("name");
		String phoneNum = (String) model.get("phoneNum");
		String faxNum = (String) model.get("faxNum");

		//견적일, 이름, 견적금액(2개 셀병합), 전화번호, 팩스번호 순
		List<String> clientInfoValues = new ArrayList<String>();
		clientInfoValues.add(DateUtils.now_short());
		clientInfoValues.add(name);
		clientInfoValues.add(BizUtils.calculCostCartList(2, model) + "원\n(부과세포함, 운송료별도)");
		clientInfoValues.add("");
		clientInfoValues.add(phoneNum);
		clientInfoValues.add(faxNum); 
		
		Map<String, CellStyle> styles = ExcelStyle.createStyles(workbook);

		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
		createTitle(sheet, title, styles);
		createCilentInfo(sheet, clientInfoValues, styles);
		createHostInfo(sheet, styles);
		createCartListInfo(sheet, model, new String[]{"No.", "제품명", "", "" , "상품번호", "단가", "수량", "합계"}, styles); //제품명 3열병합
		
		//finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 10*256); //5 characters wide
        sheet.setColumnWidth(1, 24*256);
        sheet.setColumnWidth(2, 8*256);
        sheet.setColumnWidth(3, 5*256);
        sheet.setColumnWidth(4, 10*256);
        sheet.setColumnWidth(5, 18*256);
        sheet.setColumnWidth(6, 12*256);
        sheet.setColumnWidth(7, 16*256);
	}
	
	private void createTitle(Sheet sheet, String title, Map<String, CellStyle> styles) {
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(45);
		Cell cell = titleRow.createCell(0); 
		cell.setCellValue(title); 
		cell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1")); 
	}
	
	private void createCilentInfo(Sheet sheet, List<String> values, Map<String, CellStyle> styles) {
		CellRangeAddress address1 = new CellRangeAddress(3,4,0,0);
		sheet.addMergedRegion(address1);
		CellRangeAddress address2 = new CellRangeAddress(3,4,1,1);
		sheet.addMergedRegion(address2);
		
		int rowNum = 1;
		String[] titleNames = new String[] {"견적일", "이름", "견적금액", "", "전화번호", "팩스번호"};
		for(int i=0; i<titleNames.length; i++) { 
			Row row = sheet.createRow(rowNum++);
			
			for(int j=0; j<2; j++) {
				Cell cell = row.createCell(j); 
				cell.setCellType(Cell.CELL_TYPE_STRING ); //개행 문자 적용
				cell.setCellValue(j==0 ? titleNames[i] : values.get(i));
				cell.setCellStyle(j==0 ? styles.get("customCellHeader") : styles.get("customCell"));
			}
		} 
		 
		RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, address1, sheet, sheet.getWorkbook());
		RegionUtil.setBorderRight(CellStyle.BORDER_THIN, address1, sheet, sheet.getWorkbook());
		
		RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, address2, sheet, sheet.getWorkbook());
 		RegionUtil.setBorderRight(CellStyle.BORDER_THIN, address2, sheet, sheet.getWorkbook());
 		
 		
	}
	 
	 
	private void createHostInfo(Sheet sheet, Map<String, CellStyle> styles) { 
		sheet.addMergedRegion(new CellRangeAddress(1,5,3,3)); 
		sheet.addMergedRegion(new CellRangeAddress(1,1,5,7)); 
		sheet.addMergedRegion(new CellRangeAddress(3,3,5,7)); 
		
		Row row1 = sheet.getRow(1);
		createCell(row1, 3, "공\n\r급\n\r자", styles.get("header"));
		createCell(row1, 4, "등록번호", styles.get("header"));
		createCell(row1, 5, "101-35-23132", styles.get("cell"));
		
		Row row2 = sheet.getRow(2);
		createCell(row2, 4, "상호", styles.get("header"));
		createCell(row2, 5, "ElectroShop", styles.get("cell"));
		createCell(row2, 6, "대표", styles.get("header"));
		createCell(row2, 7, "유호동", styles.get("cell")); 
		
		Row row3 = sheet.getRow(3);
		createCell(row3, 4, "사업장주소", styles.get("header"));
		createCell(row3, 5, "세종시 연서면 대첩로", styles.get("cell"));
		
		Row row4 = sheet.getRow(4);
		createCell(row4, 4, "업태", styles.get("header"));
		createCell(row4, 5, "도,소매,제조,서비스", styles.get("cell"));
		createCell(row4, 6, "종목", styles.get("header"));
		createCell(row4, 7, "전기,전자부품", styles.get("cell"));
		
		Row row5 = sheet.getRow(5);
		createCell(row5, 4, "전화", styles.get("header"));
		createCell(row5, 5, "010-2967-9441", styles.get("cell"));
		createCell(row5, 6, "팩스", styles.get("header"));
		createCell(row5, 7, "-", styles.get("cell")); 
		
	}
	
	private void createCell(Row row, int columNum, String value, CellStyle style) {
		Cell cell = row.createCell(columNum);  
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}
	
	private void createCartListInfo(Sheet sheet, Map<String,Object> model, String[] column,  Map<String, CellStyle> styles) {
		Row pharas = sheet.createRow(8);
		Cell pharasCell = pharas.createCell(0);
		pharasCell.setCellValue("다음과 같이 견적합니다");
		
		@SuppressWarnings("unchecked")
		List<CartVO> cartList = (List<CartVO>)model.get("cartList");
		 
		sheet.addMergedRegion(new CellRangeAddress(8,8,0,1)); //pharas
		
		for(int i=0; i<cartList.size() + 1; i++) { 
			sheet.addMergedRegion(new CellRangeAddress(9+i,9+i,1,3)); //제품명 3열 병합
		}
		
		sheet.addMergedRegion(new CellRangeAddress(9+cartList.size()+1, 9+cartList.size()+1, 0, 6));  //공급가액
		sheet.addMergedRegion(new CellRangeAddress(9+cartList.size()+2, 9+cartList.size()+2, 0, 6));  //부가세
		sheet.addMergedRegion(new CellRangeAddress(9+cartList.size()+3, 9+cartList.size()+3, 0, 6));  //견적총액
		 
		int rowNum = 9;
		for(int i=0; i<cartList.size() + 4; i++) { //header와 공급가액, 부가세, 견적총액 포함 row 개수 만큼 반복 
			Row row = sheet.createRow(rowNum++);
			for(int j=0; j<column.length; j++) {
				if(i==0) { //header
					Cell cell = row.createCell(j);
					cell.setCellValue(column[j]);
					cell.setCellStyle(styles.get("header"));
				}else if(i > 0 && i < cartList.size() + 1) { //cartList
					Cell cell = row.createCell(j);
					if(j==0) {
						cell.setCellValue(i);
						cell.setCellStyle(styles.get("cell"));
					}else if(j==1) {
						cell.setCellValue(cartList.get(i-1).getGoodsVO().getGoodsSubject());
						cell.setCellStyle(styles.get("cell"));
					}else if(j==4) {
						cell.setCellValue(cartList.get(i-1).getCartGoodsSeq());
						cell.setCellStyle(styles.get("cell"));
					}else if(j==5) {
						cell.setCellValue(CurrencyUtils.toNumFormat(cartList.get(i-1).getGoodsVO().getGoodsCost()) + " 원");
						cell.setCellStyle(styles.get("currencyCell"));
					}else if(j==6) {
						cell.setCellValue(cartList.get(i-1).getCartGoodsCnt());
						cell.setCellStyle(styles.get("cell"));
					}else if(j==7) {
						cell.setCellValue(CurrencyUtils.toNumFormat(cartList.get(i-1).getGoodsVO().getGoodsCost() * cartList.get(i-1).getCartGoodsCnt()) + " 원");
						cell.setCellStyle(styles.get("currencyCell"));
					}
				} else if(i > cartList.size() && i < cartList.size() + 2){ //공급가액
					Cell cell = row.createCell(j);
					if(j==0) 
						cell.setCellValue("공급가액:");
					else if(j==7) 
						cell.setCellValue(BizUtils.calculCostCartList(0, model) + " 원");
					cell.setCellStyle(styles.get("formula"));
				} else if(i > cartList.size() + 1 && i < cartList.size() + 3) { //부가세
					Cell cell = row.createCell(j);
					if(j==0)
						cell.setCellValue("부가세:");
					else if(j==7)
						cell.setCellValue(BizUtils.calculCostCartList(1, model) + " 원");
					cell.setCellStyle(styles.get("formula"));
				} else { //견적총액
					Cell cell = row.createCell(j);
					if(j==0)
						cell.setCellValue("견적총액:");
					else if(j==7)
						cell.setCellValue(BizUtils.calculCostCartList(2, model) + " 원");
					cell.setCellStyle(styles.get("formula_2"));
				}
			}
		}
	}
 

}
