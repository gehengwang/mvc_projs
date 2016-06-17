package sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sys.dao.MenuDao;
import sys.model.Menu;
import sys.model.UserInfo;
import sys.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

	@Resource
	MenuDao menuDao;
	
	@Override
	public List<Menu> queryMenus(UserInfo userInfo) {
		// TODO Auto-generated method stub
		List<Menu> menuList = menuDao.queryMenuByParentId(Long.parseLong("0"), userInfo);
		for(Menu menu : menuList){
			List<Menu> menuListChild = menuDao.queryMenuByParentId(menu.getMenuId(), userInfo);
			menu.setChildMenu(menuListChild);
		}
		return menuList;
	}

}
