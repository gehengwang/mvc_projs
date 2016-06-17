package sys.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6269395374384214260L;
	private Long menuId;	//菜单id
	private String menuName;	//菜单名称
	private String menuUrl;	//菜单链接
	private String menuFlag;	//菜单标志位 0-删除 1-正常	
	private Long menuParent;	//上级菜单id
	private Long orderId;	//排序号
	private List<Menu> childMenu;	//子菜单
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	public Long getMenuParent() {
		return menuParent;
	}
	public void setMenuParent(Long menuParent) {
		this.menuParent = menuParent;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<Menu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}
}