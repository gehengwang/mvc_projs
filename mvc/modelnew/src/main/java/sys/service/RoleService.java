package sys.service;

import java.util.List;

import sys.model.Role;
import tools.AjaxMsg;

public interface RoleService {

	/**
	 * 角色列表查询
	 * @param role
	 * @return
	 */
	List<Role> queryRoleList();
	
	/**
	 * 查询用户角色
	 * @param userId
	 * @return
	 */
	List<Role> queryRoleByUserId(Long userId);
	
	/**
	 * 增加角色
	 * @param role
	 * @return
	 */
	AjaxMsg addRole(Role role);
	
	/**
	 * 删除角色：提供批量删除
	 * @param roleIds
	 * @return
	 */
	AjaxMsg deleteRoles(String roleIds);
	
	/**
	 * 修改角色
	 * @param roleId
	 * @return
	 */
	AjaxMsg updateRole(Role role);
	
	/**
	 * 给角色赋菜单权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	AjaxMsg saveRoleMenu(Long roleId,String menuIds);
}
