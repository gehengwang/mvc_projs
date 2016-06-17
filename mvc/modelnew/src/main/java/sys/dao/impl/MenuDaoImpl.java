package sys.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.dao.MenuDao;
import sys.model.Menu;
import sys.model.UserInfo;

@Repository
public class MenuDaoImpl implements MenuDao{

	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<Menu> queryMenuByParentId(Long menuParent,UserInfo userInfo) {
		// TODO Auto-generated method stub
		String sql = "select a.id menuId,a.menu_name menuName,a.menu_url menuUrl, "
				+ "a.menu_flag menuFlag,a.menu_parent menuParent,a.order_id orderId "
				+ "from tb_menu a, tb_role_menu b where a.id=b.menu_id and "
				+ "menu_parent=:menuParent and b.role_id=:roleId "
				+ "order by menu_id";
		
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		sqlParam.addValue("menuParent", menuParent);
		sqlParam.addValue("roleId", userInfo.getRoles().get(0).getRoleId());
		List<Map<String,Object>> menuMap = namedJdbcTemplate.queryForList(sql, sqlParam);
		List<Menu> menuList = new ArrayList<Menu>();
		for(Map<String,Object> map : menuMap){
			Menu menu = new Menu();
			try {
				BeanUtils.populate(menu, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menuList.add(menu);
		}
		return menuList;
	}


	@Override
	public List<Menu> queryMenusByRoles(String roleIds) {
		// TODO 自动生成的方法存根
		String sql = "select * from tb_menu where menu_id in"
				+ "(select menu_id from tb_role_menu where role_id in(:roleIds))";
		SqlParameterSource sqlParam = new MapSqlParameterSource("roleIds",roleIds.replace(",", "','"));
		List<Map<String,Object>> menuMap = namedJdbcTemplate.queryForList(sql, sqlParam);
		List<Menu> menuList = new ArrayList<>();
		for(Map<String, Object> map : menuMap){
			Menu menu = new Menu();
			try {
				BeanUtils.populate(menu, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menuList.add(menu);
		}
		return menuList;
	}
}
