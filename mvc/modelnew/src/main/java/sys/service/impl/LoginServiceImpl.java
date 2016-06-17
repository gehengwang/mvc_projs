package sys.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sys.dao.impl.RoleDaoImpl;
import sys.dao.impl.UserDaoImpl;
import sys.service.LoginService;
import sys.model.Role;
import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.SecurityUtil;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Resource
	UserDaoImpl userDaoImpl;
	@Resource
	RoleDaoImpl roleDaoImpl;

	@Override
	public AjaxMsg login(User user,UserInfo userInfo) {
		// TODO Auto-generated method stub
		AjaxMsg ajaxMsg = new AjaxMsg();
		User userObj =userDaoImpl.queryUserObj(user);
		if(userObj==null){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("用户名不存在或手机号码错误");
		}else if(!SecurityUtil.md5(user.getPassword()).equals(userObj.getPassword())){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("密码错误");
		}else if(0==userObj.getUserStatus()){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("该用户尚未启用！");
		}else{
			//记录用户信息
			userInfo.setUser(userObj);
			
			//获取用户所属角色
			List<Role> roleList = roleDaoImpl.queryRoleByUserId(userObj.getUserId());
			userInfo.setRoles(roleList);
		}
		return ajaxMsg;
	}
}