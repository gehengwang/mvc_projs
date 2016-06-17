package sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sys.dao.RoleDao;
import sys.model.Role;
import sys.service.RoleService;
import tools.AjaxMsg;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource
	RoleDao roleDao;
	
	@Override
	public List<Role> queryRoleList() {
		// TODO 自动生成的方法存根
		return roleDao.queryRoleList();
	}

	@Override
	public List<Role> queryRoleByUserId(Long userId) {
		// TODO 自动生成的方法存根
		return roleDao.queryRoleByUserId(userId);
	}

	@Override
	public AjaxMsg addRole(Role role) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = roleDao.addRole(role);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("角色添加失败！");
		}else{
			ajaxMsg.setMsg("角色添加成功！");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg deleteRoles(String roleIds) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = roleDao.deleteRoles(roleIds);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("角色删除失败！");
		}else{
			ajaxMsg.setMsg("角色删除成功！");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg updateRole(Role role) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = roleDao.updateRole(role);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("角色更新失败！");
		}else{
			ajaxMsg.setMsg("角色更新成功！");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg saveRoleMenu(Long roleId, String menuIds) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		roleDao.deleteRoleMenu(roleId);
		int rs = roleDao.addRoleMenu(roleId, menuIds);
		if(rs!=menuIds.length()){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("角色赋权失败！");
		}else{
			ajaxMsg.setMsg("角色赋权成功！");
		}
		return ajaxMsg;
	}
}
