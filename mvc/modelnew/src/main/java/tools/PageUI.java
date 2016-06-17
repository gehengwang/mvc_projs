package tools;

import java.io.Serializable;
import java.util.List;

public class PageUI implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863217069466685947L;
	private List<?> rows;	//主要数据
	private List<?> footer;	//底部数据  对应EasyUI datagrid footer
	private int total;	//总数据量
	
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public List<?> getFooter() {
		return footer;
	}
	public void setFooter(List<?> footer) {
		this.footer = footer;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}