package tools;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class Page {

	private int startIndex;	//起始行数
	private int lastIndex;	//结束行数
	private int page;	//当前页数
	private int totalPage;	//总页数
	private int totalRows;  //记录总数        
	private int rows;  //一页显示的记录数        
	private List<Map<String,Object>> resultList;	//结果集存放list

	
	public Page(String sql,int page,int rows,NamedParameterJdbcTemplate nameJdbcTemplate,SqlParameterSource sqlParameterSource){
		
		if(nameJdbcTemplate == null){
			throw new IllegalArgumentException("Page.jTemplate is null");
		}else if(sql == null || sql.equals("")){
			throw new IllegalArgumentException("Page.sql is empty");
		}
		setRows(rows);//设置每页显示的记录数
		setPage(page);//设置要显示的页数
		StringBuffer sqlTotal = new StringBuffer();
		sqlTotal.append(" select count(*) from ( ")
				.append(sql)
				.append(" ) totalTable");
		setTotalRows(nameJdbcTemplate.queryForObject(sqlTotal.toString(), sqlParameterSource, Integer.class));//总记录数
		setTotalPage();//计算总页数
		setStartIndex();//计算起始行数
		setLastIndex();//计算结束行数
		StringBuffer pageSql = new StringBuffer();//MySQL分页limit写法注意需last-start
		pageSql.append(sql).append(" limit " +startIndex+ "," +(lastIndex-startIndex));
		setResultList(nameJdbcTemplate.queryForList(pageSql.toString(), sqlParameterSource));
	}

	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex() {
		this.startIndex = (page - 1) * rows;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	//计算结束时候的索引
	public void setLastIndex() {
		if( totalRows < rows){
			this.lastIndex = totalRows;
		}else if((totalRows % rows == 0) || (totalRows % rows != 0 && page < totalPage)){
			this.lastIndex = page * rows;
		}else if(totalRows % rows != 0 && page == totalPage){//最后一页
			this.lastIndex = totalRows;
		}
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage() {
		/*if(totalRows % rows == 0){
			this.totalPage = totalRows / rows;
		}else{
			this.totalPage = (totalRows / rows) + 1;
		}*/
		this.totalPage = (int) Math.ceil((double)totalRows/rows);
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}
}