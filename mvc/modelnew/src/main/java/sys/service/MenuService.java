package sys.service;

import java.util.List;

import sys.model.Menu;
import sys.model.UserInfo;

public interface MenuService {

	/**
	 * 取角色所对应的菜单
	 * @param userInfo
	 * @return
	 */
	List<Menu> queryMenus(UserInfo userInfo);
}
