package tools.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ReportHeadDraw extends BaseExcelDraw {
   


	private CellStyle style;

	public ReportHeadDraw(ExcelLocation excelLocation,
			String head) {
		super(excelLocation, null, null);
		List<String[]> indata=new ArrayList<String[]>();
		indata.add(new String[]{head});
		this.data=indata;
		
	
	}

	@Override
	public void drawPrepare(Workbook workbook) {
		
		style = workbook.createCellStyle();

		if(baseCellStyle!=null){
			style.cloneStyleFrom(baseCellStyle);

		}

		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font ff=workbook.createFont();
		ff.setFontName("黑体");
		ff.setBoldweight(Font.BOLDWEIGHT_BOLD);
		ff.setFontHeightInPoints((short) 14);
		style.setFont(ff);
		

		
		
	}
	@Override
	public void drawRow(Row row, Object rowdata, int rowIndex) {
		Sheet sheet = row.getSheet();
		sheet.addMergedRegion(new CellRangeAddress(excelLocation.getRowSt(),excelLocation.getRowEd(),excelLocation.getColSt(),excelLocation.getColEd()));

	}

	@Override
	public void drawCell(Cell cell, String val, String excel_fmt,int rowIndex, int colIndex) {
		DataFormat format = cell.getSheet().getWorkbook().createDataFormat();
		short idx = format.getFormat(excel_fmt);
		style.setDataFormat(idx);
		cell.setCellStyle(style);
		cell.setCellValue(val);

	}

	

}
