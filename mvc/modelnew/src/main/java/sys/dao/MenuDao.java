package sys.dao;

import java.util.List;

import sys.model.Menu;
import sys.model.UserInfo;

public interface MenuDao {

	/**
	 * 菜单有上下级关系，以树的形式展示。根据父菜单id查询子菜单
	 * @param menuParent
	 * @param userInfo
	 * @return
	 */
	List<Menu> queryMenuByParentId(Long menuParent,UserInfo userInfo);
	
	/**
	 * 查询角色所对应的菜单
	 * @param roleIds
	 * @return
	 */
	List<Menu> queryMenusByRoles(String roleIds);

}
