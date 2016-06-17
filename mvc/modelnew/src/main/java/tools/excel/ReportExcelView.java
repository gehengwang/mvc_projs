package tools.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;


public class ReportExcelView extends BaseExcelView {

	private BaseExcelDraw headDraw;
	private BaseExcelDraw titleDraw;
	private BaseExcelDraw dataDraw;
	
	public ReportExcelView(String excelName,int edition) {
		super(excelName, edition);
	}

	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		    CellStyle cellStyle=createStyle(workbook);
		    if(headDraw!=null){
		    	if(headDraw.getBaseCellStyle()==null)headDraw.setBaseCellStyle(cellStyle);
		    	globalExcelLocation=headDraw.drawData(workbook,globalExcelLocation);
		    }
		    if(titleDraw!=null){
		    	if(titleDraw.getBaseCellStyle()==null)titleDraw.setBaseCellStyle(cellStyle);
		    	globalExcelLocation=titleDraw.drawData(workbook,globalExcelLocation);
		    }
		    if(dataDraw!=null){
		    	if(dataDraw.getBaseCellStyle()==null)dataDraw.setBaseCellStyle(cellStyle);
		    	globalExcelLocation=dataDraw.drawData(workbook,globalExcelLocation);
		    }
		    
		    
		    for (int i=0;i<workbook.getNumberOfSheets();i++){
		    	 modify(workbook.getSheetAt(0));
		    }
		   
	}
	private CellStyle createStyle(Workbook workbook){
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN );
		cellStyle.setBorderTop(CellStyle.BORDER_THIN );
		cellStyle.setBorderRight(CellStyle.BORDER_THIN );
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN );
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFont(font);
		return cellStyle;
	};

	public BaseExcelDraw getDataDraw() {
		return dataDraw;
	}


	public void setDataDraw(BaseExcelDraw dataDraw) {
		this.dataDraw = dataDraw;
	}


	public void setTitleDraw(BaseExcelDraw titleDraw) {
		this.titleDraw = titleDraw;
	}


	public BaseExcelDraw getTitleDraw() {
		return titleDraw;
	}


	public void setHeadDraw(BaseExcelDraw headDraw) {
		this.headDraw = headDraw;
	}


	public BaseExcelDraw getHeadDraw() {
		return headDraw;
	}

}
