package tools.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import tools.ReflectionUtil;


public abstract class BaseExcelDraw {

	private static final int    MAX_ROW[] = {65500,1048500};
	
	protected ExcelLocation excelLocation;
	
	protected List<?> data;
	protected String[] key;

	protected int edition;
	protected CellStyle baseCellStyle;

	public BaseExcelDraw(ExcelLocation excelLocation,List<?> data,String[] key){
		this.excelLocation=excelLocation;
		this.data=data;
		this.key=key;

		if (this.data!=null)this.excelLocation.setRowLen(data.size());
		if (this.key!=null)this.excelLocation.setColLen(key.length);
	}
    
	public final ExcelLocation drawData(Workbook workbook,ExcelLocation globalExcelLocation ) throws Exception{
		drawPrepare(workbook);
		Sheet sheet = workbook.getSheet(excelLocation.getSheetName());
		if(sheet==null)sheet=workbook.createSheet(excelLocation.getSheetName());
		if(globalExcelLocation==null){
			excelLocation.eval(0);
			globalExcelLocation=excelLocation.clone();
		}else{
			excelLocation.eval(globalExcelLocation.getRowEd()+1);
			globalExcelLocation.merge(excelLocation);
		}
		
		if(data!=null){
			for(int i=0;i<excelLocation.getRowLen();i++){
				if(i+excelLocation.getRowSt()>MAX_ROW[edition])return globalExcelLocation;
				Row row = sheet.getRow(i+excelLocation.getRowSt());
				if(row==null)row=sheet.createRow(i+excelLocation.getRowSt());
				Object rowdata = data.get(i);			
				
				if(rowdata!=null){
					for(int j=0;j<excelLocation.getColLen();j++){
						Cell cell=row.getCell(j+excelLocation.getColSt());
						if(cell==null)cell=row.createCell(j+excelLocation.getColSt());
						Object celldata = null;
						String convert_fmt = null;
						String excel_fmt="@";
						if(rowdata instanceof String[]){
							String[] array=(String[])rowdata;
							if(j>=array.length)continue;
							celldata=((String[])rowdata)[j];
						}else if(rowdata instanceof Map){
							celldata=((Map<?, ?>)rowdata).get(key[j]);
						}else{
							celldata=ReflectionUtil.invokeGetMethod(rowdata, key[j]);
							ExcelCell excelCell=(ExcelCell) ReflectionUtil.getFieldAnnotation(rowdata.getClass(),key[j],ExcelCell.class.getName());
							if(excelCell!=null){
								convert_fmt = excelCell.convert_fmt();
								excel_fmt =  excelCell.excel_fmt();	
							}
						}
						String val=ReflectionUtil.covert2Str(celldata, convert_fmt);
						drawCell(cell, val, excel_fmt, i,j);
					}
				}
				drawRow(row,rowdata,i);
			}
		}
		return globalExcelLocation;
		
	}
	protected abstract void drawPrepare(Workbook workbook);
	protected abstract void drawRow(Row row,Object rowdata,int rowIndex);
	protected abstract void drawCell(Cell cell,String val,String excel_fmt,int rowIndex,int colIndex);

	/**
	 * @return the baseCellStyle
	 */
	public CellStyle getBaseCellStyle() {
		return baseCellStyle;
	}

	/**
	 * @param baseCellStyle the baseCellStyle to set
	 */
	public void setBaseCellStyle(CellStyle baseCellStyle) {
		this.baseCellStyle = baseCellStyle;
	}
}