package sys.model;

import java.io.Serializable;

public class Param implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 365092184810704631L;
	private String id;
	private String type="";
	private String value="";
	private String text="";
	private String flag;
	private int order_id;
	private String parent_sn="0";
	
	//for treeGrid
	private String parentId;
	private String state="closed";
	private String parent_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getParent_sn() {
		return parent_sn;
	}
	public void setParent_sn(String parent_sn) {
		this.parent_sn = parent_sn;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
}
