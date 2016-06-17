package sys.dao;

import java.util.List;

import sys.model.Role;

public interface RoleDao {

	/**
	 * 角色列表查询:角色没有上下级关系，列表展示
	 * @return
	 */
	List<Role> queryRoleList();
	
	/**
	 * 查询用户角色
	 * @param role
	 * @return
	 */
	List<Role> queryRoleByUserId(Long userId);
	
	/**
	 * 增加角色
	 * @param role
	 * @return
	 */
	int addRole(Role role);
	
	/**
	 * 删除角色：提供批量删除
	 * @param roleIds
	 * @return
	 */
	int deleteRoles(String roleIds);
	
	/**
	 * 修改角色
	 * @param roleId
	 * @return
	 */
	int updateRole(Role role);
	
	/**
	 * 给角色赋菜单权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int addRoleMenu(Long roleId,String menuIds);
	
	/**
	 * 删除角色菜单权限
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int deleteRoleMenu(Long roleId);
}