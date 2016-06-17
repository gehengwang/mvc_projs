package tools.excel;

public class ExcelLocation implements Cloneable{
	
	protected String sheetName;
	/** int rowSt 行起始 0->第1列*/
	protected int rowSt;
	/** int rowEd 行结束*/
	protected int rowEd;
	/** int colSt 列起始0->A列*/
	protected int colSt;
	/** int colEd 列结束*/
	protected int colEd;

	protected int rowLen;
	protected int colLen;
	
	private int sheetNum=1;
	public ExcelLocation(String sheetName){
		this.sheetName=sheetName;
		rowSt=-1;
		rowEd=-1;
		colSt=-1;
		colEd=-1;
		rowLen=-1;
		colLen=-1;
	}
	
	public ExcelLocation(String sheetName,int rowLen, int colLen) {
		super();
		this.sheetName = sheetName;
		this.rowSt = -1;
		this.rowEd = -1;
		this.colSt = -1;
		this.colEd = -1;
		this.rowLen = rowLen;
		this.colLen = colLen;
	}

	public ExcelLocation eval(int rowOffset){
		if (rowOffset<0)rowOffset=0;
	
		if(rowSt<0)rowSt=rowOffset;
		
		if(rowLen>=0)rowEd=rowSt+rowLen-1;
		else if(rowEd<0)rowEd=rowSt;
		else rowLen=rowEd-rowSt+1;

		if(colSt<0)colSt=0;
		if(colLen>=0)colEd=colSt+colLen-1;
		else if(colEd<0)colEd=colSt;
		else colLen=colEd-colSt+1;
		
		return this;
	}
	public ExcelLocation merge(ExcelLocation source){
		rowSt=Math.min(rowSt, source.getRowSt());
		rowEd=Math.max(rowEd, source.getRowEd());
		colSt=Math.min(colSt, source.getColSt());
		colEd=Math.max(colEd, source.getColEd());
		rowLen=rowEd-rowSt+1;
		colLen=colEd-colSt+1;
		return this;
	}
	/**
	 * @return the sheetIndex
	 */

	/**
	 * @return the rowSt
	 */
	public int getRowSt() {
		return rowSt;
	}
	/**
	 * @return the rowEd
	 */
	public int getRowEd() {
		return rowEd;
	}
	/**
	 * @return the colSt
	 */
	public int getColSt() {
		return colSt;
	}
	/**
	 * @return the colEd
	 */
	public int getColEd() {
		return colEd;
	}
	/**
	 * @return the rowLen
	 */
	public int getRowLen() {
		return rowLen;
	}
	/**
	 * @return the colLen
	 */
	public int getColLen() {
		return colLen;
	}

	/**
	 * @param rowSt the rowSt to set
	 */
	public void setRowSt(int rowSt) {
		this.rowSt = rowSt;
	}
	/**
	 * @param rowEd the rowEd to set
	 */
	public void setRowEd(int rowEd) {
		this.rowEd = rowEd;
	}
	/**
	 * @param colSt the colSt to set
	 */
	public void setColSt(int colSt) {
		this.colSt = colSt;
	}
	/**
	 * @param colEd the colEd to set
	 */
	public void setColEd(int colEd) {
		this.colEd = colEd;
	}
	/**
	 * @param rowLen the rowLen to set
	 */
	public void setRowLen(int rowLen) {
		this.rowLen = rowLen;
	}
	/**
	 * @param colLen the colLen to set
	 */
	public void setColLen(int colLen) {
		this.colLen = colLen;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	@Override
	protected ExcelLocation clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (ExcelLocation) super.clone();
	}
	
}
