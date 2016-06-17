package tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 函数      <br>
 */
public class ExcelFun {
	 private final static DataFormatter formatter = new DataFormatter();
	 public static Workbook getWorkBook(File excelFile) throws IOException{
		 return getWorkBook(new FileInputStream(excelFile),excelFile.getName());
	 }
    /**
     * 根据文件和文件名生成Workbook对象
     * @param excelFile
     * @param excelFileFileName
     * @return
     * @throws IOException
     */

	public static Workbook getWorkBook(InputStream is,String excelFileFileName) throws IOException{  
    	
        if(excelFileFileName.toLowerCase().endsWith("xls")){  
            return new HSSFWorkbook(is);  
        }  
        if(excelFileFileName.toLowerCase().endsWith("xlsx")){  
            return new XSSFWorkbook(is);  
        }  
        return null;  
    } 
    public static String getStrValue(Cell cell){
      if(cell==null)return "";
      FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();  
      String rs = formatter.formatCellValue(cell, evaluator);  
      return rs;
    }
    /**
     * 获取Cell中内容
     * @param cell
     * @return String
     */
    public static String getValue(Cell cell){
    	if(cell==null)return "";
    	String rs="";
    	if ((cell.getCellType()==Cell.CELL_TYPE_NUMERIC||cell.getCellType()==Cell.CELL_TYPE_FORMULA)&&DateUtil.isCellDateFormatted(cell)){
    		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    		rs=getValue(cell,sf);
    	}else{
    		DecimalFormat df=new DecimalFormat("0");
    		rs=getValue(cell,df);
    	}
    	return rs;
    }
    /**
     * 获取Cell中内容
     * @param cell
     * @return String
     */
    public static String getValue(Cell cell,Format myformat){
    	String cellvalue = "";
        if (cell != null){
        	switch (cell.getCellType()) 
        	{
        	  case Cell.CELL_TYPE_NUMERIC:
        	  case Cell.CELL_TYPE_FORMULA:{
        		// 判断当前的cell是否为Date
                  if (DateUtil.isCellDateFormatted(cell)) 
                  {
                     // 如果是Date类型则，取得该Cell的Date值
                     Date date = cell.getDateCellValue();
                     cellvalue = myformat.format(date);
                     
                  }else{
//                	 cellvalue=cell.toString();
                   double num = new Double((double)cell.getNumericCellValue());
                   cellvalue = String.valueOf(myformat.format(num));
                  }
                  break;
        	  }
        	  case Cell.CELL_TYPE_STRING:
                  cellvalue = cell.getStringCellValue().replaceAll("'", "''");
                  cellvalue = cellvalue.trim();
                  break;
               default:
                  cellvalue = "";
            }

        		  
        	
        }

		return cellvalue;	
    }
    /**
     * 创建CELLS
     * @param r
     * @param num
     */
    public static void createCells(Row r,int num){
    	for(int i = 0; i < num; i ++) {
 		   r.createCell(i);
 	   }

    }
    /**
     * 创建ROWS
     * @param sheet
     * @param st
     * @param ed
     */
    public static void createRows(Sheet sheet,int st,int ed){
    	for(int i = st; i < ed; i ++) {
    		sheet.createRow(i);
 	   }

    }
    
    public static void copyCell(Cell srcCell,Cell distCell, boolean copyValueFlag){
    	
    	//Workbook workbook = distCell.getSheet().getWorkbook();
    	//CellStyle distStyle = workbook.createCellStyle();
    	CellStyle srcStyle  = srcCell.getCellStyle();
    	//distStyle.cloneStyleFrom(srcStyle);
    	distCell.setCellStyle(srcStyle);
    	if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
    	 int srcCellType = srcCell.getCellType();  
         distCell.setCellType(srcCellType);  
         if (copyValueFlag) {  
             if (srcCellType == Cell.CELL_TYPE_NUMERIC) {  
                 if (DateUtil.isCellDateFormatted(srcCell)) {  
                     distCell.setCellValue(srcCell.getDateCellValue());  
                 } else {  
                     distCell.setCellValue(srcCell.getNumericCellValue());  
                 }  
             } else if (srcCellType == Cell.CELL_TYPE_STRING) {  
                 distCell.setCellValue(srcCell.getRichStringCellValue());  
             } else if (srcCellType == Cell.CELL_TYPE_BLANK) {  
                 // nothing21  
             } else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {  
                 distCell.setCellValue(srcCell.getBooleanCellValue());  
             } else if (srcCellType == Cell.CELL_TYPE_ERROR) {  
                 distCell.setCellErrorValue(srcCell.getErrorCellValue());  
             } else if (srcCellType == Cell.CELL_TYPE_FORMULA) {  
                 distCell.setCellFormula(srcCell.getCellFormula());  
             } else { // nothing29  
             }  
         }  
    	
    }
    public static void copyRow(Row fromRow,Row toRow,boolean copyValueFlag){ 
    	toRow.setHeight(fromRow.getHeight());
        for (Iterator<?> cellIt = fromRow.cellIterator(); cellIt.hasNext();) {  
        	Cell tmpCell = (Cell) cellIt.next();  
        	Cell newCell = toRow.createCell(tmpCell.getColumnIndex());  
            copyCell(tmpCell, newCell, copyValueFlag);  
        }  
    }  

}
