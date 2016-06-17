package tools.excel;

import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ReportDataDraw extends BaseExcelDraw {
   
	private CellStyle odd;
	private CellStyle even;

	public ReportDataDraw(ExcelLocation excelLocation, List<?> data,
			String[] key) {
		super(excelLocation, data, key);
	
	}

	@Override
	public void drawPrepare(Workbook workbook) {
		
		odd = workbook.createCellStyle();
		even = workbook.createCellStyle();
		if(baseCellStyle!=null){
			odd.cloneStyleFrom(baseCellStyle);
			even.cloneStyleFrom(baseCellStyle);
		}
		
		odd.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		odd.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		
	}
	@Override
	public void drawRow(Row row, Object rowdata, int rowIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawCell(Cell cell, String val, String excel_fmt,int rowIndex, int colIndex) {
		DataFormat format = cell.getSheet().getWorkbook().createDataFormat();
		short idx = format.getFormat(excel_fmt);
		if(rowIndex%2==0){
			odd.setDataFormat(idx);
			cell.setCellStyle(odd);
		}else{
			even.setDataFormat(idx);
			cell.setCellStyle(even);
		}
		cell.setCellValue(val);

	}
}