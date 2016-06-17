package sys.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.dao.RoleDao;
import sys.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao{

	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public List<Role> queryRoleList() {
		// TODO 自动生成的方法存根
		String sql = "select * from tb_role";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(new Role());
		List<Map<String, Object>> roleMap = namedJdbcTemplate.queryForList(sql,sqlParam);
		List<Role> roleList = new ArrayList<Role>();
		for(Map<String, Object> map : roleMap){
			Role roleObj = new Role();
			//该工具类将map类型转为Role对象
			try {
				BeanUtils.populate(roleObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			roleList.add(roleObj);
		}
		return roleList;
	}
	
	@Override
	public List<Role> queryRoleByUserId(Long userId) {
		// TODO 自动生成的方法存根
		String sql = "select role_group roleGroup,a.id roleId,role_name roleName,"
				+ "role_flag roleFlag,order_id orderId,dept_id deptId,class_id classId "
				+ " from tb_role a,tb_user_bind b where a.id=b.role_id and user_id=:userId";
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		sqlParam.addValue("userId", userId);
		List<Map<String, Object>> roleMap = namedJdbcTemplate.queryForList(sql,sqlParam);
		List<Role> roleList = new ArrayList<Role>();
		for(Map<String, Object> map : roleMap){
			Role roleObj = new Role();
			//该工具类将map类型转为Role对象
			try {
				BeanUtils.populate(roleObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			roleList.add(roleObj);
		}
		return roleList;
	}

	@Override
	public int addRole(Role role) {
		// TODO 自动生成的方法存根
		String sql = "insert into tb_role(group_role,role_name,"
				+ "role_flag,order_id) values(?,?,?,?,?)";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(role);
		int rs = namedJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int deleteRoles(String roleIds) {
		// TODO 自动生成的方法存根
		String sql = "update tb_role set role_flag=1 where role_id in(?)";
		SqlParameterSource sqlParam = new MapSqlParameterSource("roleIds",roleIds);
		int rs = namedJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int updateRole(Role role) {
		// TODO 自动生成的方法存根
		String sql = "update tb_role set role_name=? where role_id =?)";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(role);
		int rs = namedJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int addRoleMenu(Long roleId, String menuIds) {
		// TODO 自动生成的方法存根
		//String[] menuIdsArray = menuIds.split(",");
		//String sql = "insert into tb_role_menu(role_id,menu_id) values (?,?)";
		int rs = 0;
		/*for(String str : menuIdsArray){
			rs +=0;
		}*/
		return rs;
	}

	@Override
	public int deleteRoleMenu(Long roleId) {
		// TODO 自动生成的方法存根
		String sql = "delete from tb_role_menu where roleId=?";
		SqlParameterSource sqlParam = new MapSqlParameterSource("roleId",roleId);
		int rs = namedJdbcTemplate.update(sql, sqlParam);
		return rs;
	}
}