package tools.excel;

import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

public abstract class BaseExcelView extends AbstractView{
	private static final Logger LOG = Logger.getLogger(BaseExcelView.class); 
	private static final String CONTENT_TYPE = "application/vnd.ms-excel";
	
	private static final String EXTENSIONS[] = {".xls",".xlsx"};
	
	
	private String url;
	private int edition;
	private String excelName;
	protected ExcelLocation globalExcelLocation;
	public BaseExcelView(String excelName,int edition) {
		this.excelName=excelName;
		this.edition=edition;
		setContentType(CONTENT_TYPE);
	}
	/**
	 * Set the URL of the Excel workbook source, without localization part nor extension.
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/**
	 * Renders the Excel view, given the specified model.
	 */
	@Override
	protected final void renderMergedOutputModel(
			Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Workbook workbook;
		if (this.url != null) {
			workbook = getTemplateSource(this.url, request);
		}
		else {
			if(edition==0){
				workbook = new HSSFWorkbook();
			}else{
				workbook =new XSSFWorkbook();
			}
			logger.debug("Created Excel Workbook from scratch");
		}

		buildExcelDocument(model, workbook, request, response);

		// Set the content type.
		response.setContentType(getContentType());
		
        // 解决中文文件名乱码问题  
        if (request.getHeader("User-Agent").toLowerCase()  
                .indexOf("firefox") > 0) {  
        	excelName = new String(excelName.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器  
        } else if (request.getHeader("User-Agent").toUpperCase()  
                .indexOf("MSIE") > 0) {  
        	excelName = URLEncoder.encode(excelName, "UTF-8");// IE浏览器  
        }else if (request.getHeader("User-Agent").toUpperCase()  
                .indexOf("CHROME") > 0) {  
        	excelName = new String(excelName.getBytes("UTF-8"), "ISO8859-1");// 谷歌  
        } 
		response.setHeader("Content-Disposition", "attachment; filename=\""+excelName+ EXTENSIONS[edition]+"\"");  
		// Should we set the content length here?
		// response.setContentLength(workbook.getBytes().length);

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}
	protected void modify(Sheet sheet) {
		
		/**
		 * -To calculate column width Sheet.autoSizeColumn uses Java2D classes that throw exception 
		 *  if graphical environment is not available. In case if graphical environment is not available, 
		 *  you must tell Java that you are running in headless mode and set the following 
		 *  system property: <b>java.awt.headless=true</b> . 
        */
		try{
			Row row=sheet.getRow(sheet.getFirstRowNum());
			int col=0;
			if(row!=null)col=row.getLastCellNum();
			for(int i=0;i<col;i++){
				sheet.autoSizeColumn(i); //调整列宽度
			}
		}catch(Exception e){
			LOG.error("无法调用系统图形接口,可以设置参数java.awt.headless=true 解决该问题", e);
		}

	    /**-设置打印区域------------------------------**/
		PrintSetup ps = sheet.getPrintSetup();
	    ps.setFitWidth((short)1);
	    ps.setFitHeight((short)0);
//	    /**-设置打印标题------------------------------**/
//	    int lastr=0;if(titlex!=null)lastr=titlex.getRowed();
//		wb.setRepeatingRowsAndColumns(0, -1, -1, 0, lastr);



	}
	protected Workbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
		LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
		Locale userLocale = RequestContextUtils.getLocale(request);
		Resource inputFile = helper.findLocalizedResource(url, EXTENSIONS[edition], userLocale);

		// Create the Excel document from the source.
		if (logger.isDebugEnabled()) {
			logger.debug("Loading Excel workbook from " + inputFile);
		}
		
		//POIFSFileSystem fs = new POIFSFileSystem(inputFile.getInputStream());
	
		return ExcelFun.getWorkBook(inputFile.getInputStream(),EXTENSIONS[edition]);
	}

	/**
	 * Subclasses must implement this method to create an Excel HSSFWorkbook document,
	 * given the model.
	 * @param model the model Map
	 * @param workbook the Excel workbook to complete
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 */
	protected abstract void buildExcelDocument(
			Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public String getExcelName() {
		return excelName;
	}
	public void setGlobalExcelLocation(ExcelLocation globalExcelLocation) {
		this.globalExcelLocation = globalExcelLocation;
	}
	public ExcelLocation getGlobalExcelLocation() {
		return globalExcelLocation;
	}



}
