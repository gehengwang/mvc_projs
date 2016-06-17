package tools;

import java.io.Serializable;

public class Pagination implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2744898130099503825L;
	private int page;
	private int rows;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
