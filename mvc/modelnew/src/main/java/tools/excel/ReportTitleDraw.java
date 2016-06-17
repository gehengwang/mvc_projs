package tools.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ReportTitleDraw extends BaseExcelDraw {

	private CellStyle style1;
	private CellStyle style2;
	private CellStyle style3;

    private int titleHeight;
	public ReportTitleDraw(String sheetName,String[] title) {
		super(null, null, null);
		List<String[]> indata=new ArrayList<String[]>();
		titleHeight=1;
		this.excelLocation=new ExcelLocation(sheetName,titleHeight,title.length);
		indata.add(title);
		this.data=indata;
	}

	@Override
	public void drawPrepare(Workbook workbook) {
		
	
		style1 = workbook.createCellStyle();
		if(baseCellStyle!=null){
			style1.cloneStyleFrom(baseCellStyle);
		}
		style1.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

		if(titleHeight<=2){
			style2 = workbook.createCellStyle();
			style2.cloneStyleFrom(style1);
			style2.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		}
		if(titleHeight<=3){
			style3 = workbook.createCellStyle();
			style3.cloneStyleFrom(style1);
			style3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		}
	
		

		
		
	}
	@Override
	public void drawRow(Row row, Object rowdata, int rowIndex) {
		

	}

	@Override
	public void drawCell(Cell cell, String val, String excel_fmt,int rowIndex, int colIndex) {
        if(rowIndex==0)cell.setCellStyle(style1);
        if(rowIndex==1)cell.setCellStyle(style2);
        if(rowIndex==2)cell.setCellStyle(style3);
		cell.setCellValue(val);

	}

	public void setTitleHeight(int titleHeight) {
		this.titleHeight = titleHeight;
	}

	public int getTitleHeight() {
		return titleHeight;
	}

	

}
