package sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sys.model.Role;
import sys.service.RoleService;
import tools.AjaxMsg;

/**
 * 角色控制
 * @author xy
 *
 */
@Controller
@RequestMapping("role")
public class RoleController {

	@Resource
	RoleService roleService;
	
	@RequestMapping("queryRoleObj")
	List<Role> queryRoleObj(){
		List<Role> roleList = roleService.queryRoleList();
		return roleList;
	}
	
	@RequestMapping("queryRoleByUserId")
	List<Role> queryRoleByUserId(Long userId){
		List<Role> roleList = roleService.queryRoleByUserId(userId);
		return roleList;
	}
	
	@RequestMapping("addRole")
	AjaxMsg addRole(Role role){
		AjaxMsg msg = roleService.addRole(role);
		return msg;
	}
	
	@RequestMapping("updateRole")
	AjaxMsg updateRole(Role role){
		AjaxMsg msg = roleService.updateRole(role);
		return msg;
	}
	
	@RequestMapping("deleteRoles")
	AjaxMsg deleteRoles(String roleIds){
		AjaxMsg msg = roleService.deleteRoles(roleIds);
		return msg;
	}
	
	@RequestMapping("saveRoleMenu")
	AjaxMsg saveRoleMenu(Long roleId,String menuIds){
		AjaxMsg msg = roleService.saveRoleMenu(roleId, menuIds);
		return msg;
	}
}
