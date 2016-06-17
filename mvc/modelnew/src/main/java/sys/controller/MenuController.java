package sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.Menu;
import sys.model.UserInfo;
import sys.service.MenuService;

@Controller
public class MenuController {

	@Resource
	MenuService menuService;
	
	@ResponseBody
	@RequestMapping("queryMenus")
	List<Menu> queryMenus(HttpServletRequest request,HttpServletResponse response){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<Menu> menuList = menuService.queryMenus(userInfo);
		return menuList;
	}
}
