package sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.dao.DeptDao;
import sys.dao.UserDao;
import sys.model.Dept;
import sys.model.Role;
import sys.model.User;
import sys.model.UserInfo;
import sys.service.DeptService;
import tools.AjaxMsg;
import tools.SecurityUtil;

@Service
@Transactional
public class DeptServiceImpl implements DeptService{

	@Resource
	DeptDao deptDao;
	@Resource
	UserDao userDao;
		
	
	@Override
	public Dept queryDeptObj(Dept dept) {
		// TODO 自动生成的方法存根
		return deptDao.queryDeptObj(dept);
	}

	@Override
	public List<Dept> queryDeptList(Dept dept) {
		// TODO 自动生成的方法存根
		return deptDao.queryDeptList(dept);
	}

	@Override
	public AjaxMsg addDept(Dept dept) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		Map<String,Object> mapDept = deptDao.addDept(dept);
		//根据返回的dept_id来更新dept_code
		Long deptId = Long.parseLong(mapDept.get("deptId")+"");
		int rs0 = deptDao.updateDeptCode(deptId);
		//注册机构的同时注册一个管理员
		User user = new User();
		user.setUserName(dept.getDeptName());
		user.setMobile(dept.getDeptMobile());
		user.setEmail(dept.getDeptEmail());
		user.setPassword(SecurityUtil.md5(dept.getPassword()));
		user.setRoleId(Role.ROLE_dept_admin);
		user.setDeptId(deptId);
		Map<String,Object> mapUser = userDao.addUser(user);
		Long[] userIds = {Long.parseLong(mapUser.get("userId")+"")};
		user.setUserIds(userIds);
		int rs1 = userDao.addUserBind(user);
		
		if(rs0>0&&rs1>0){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("您已经注册成功，系统管理员会审核您的资料，并给您反馈！");
		}else{
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("机构注册失败");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg updateDept(Dept dept) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = deptDao.updateDept(dept);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("机构修改失败");
		}else{
			ajaxMsg.setMsg("机构修改成功");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg deleteDepts(String deptIds) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = deptDao.deleteDepts(deptIds);
		if(rs!=deptIds.length()){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("机构删除失败");
		}else{
			ajaxMsg.setMsg("成功删除"+rs+"条记录");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg checkDeptName(String deptName) {
		// TODO Auto-generated method stub
		AjaxMsg ajaxMsg = new AjaxMsg();
		Dept dept = new Dept();
		dept.setDeptName(deptName);
		Dept deptObj = deptDao.queryDeptObj(dept);
		if(null!=deptObj){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("对不起,该机构名已被占用，请重试！");
			ajaxMsg.setBackParam(deptObj.getDeptId());
		}else{
			ajaxMsg.setMsg("该机构名可以使用");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg checkDeptMobile(String deptMobile) {
		// TODO Auto-generated method stub
		AjaxMsg ajaxMsg = new AjaxMsg();
		Dept dept = new Dept();
		dept.setDeptMobile(deptMobile);
		Dept deptObj = deptDao.queryDeptObj(dept);
		if(null!=deptObj){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("您输入的手机号码已注册,请重新输入！");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg checkDeptEmail(String deptEmail) {
		// TODO Auto-generated method stub
		AjaxMsg ajaxMsg = new AjaxMsg();
		Dept dept = new Dept();
		dept.setDeptEmail(deptEmail);
		Dept deptObj = deptDao.queryDeptObj(dept);
		if(null!=deptObj){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("您输入的邮箱地址已注册,请重新输入！");
		}
		return ajaxMsg;
	}

	@Override
	public List<?> deptCombo(UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return deptDao.deptListMap(userInfo);
	}
	
}