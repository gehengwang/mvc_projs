package sys.service;

import java.util.List;

import sys.model.Dept;
import sys.model.UserInfo;
import tools.AjaxMsg;

public interface DeptService {

	
	/**
	 * 查询机构对象
	 * @param dept
	 * @return
	 */
	Dept queryDeptObj(Dept dept);
	
	
	/**
	 * 机构列表
	 * @param dept
	 * @return
	 */
	List<Dept> queryDeptList(Dept dept);
	
	/**
	 * 机构注册
	 * @param dept
	 * @return
	 */
	AjaxMsg addDept(Dept dept);
	
	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	AjaxMsg updateDept(Dept dept);
	
	/**
	 * 删除部门：提供批量删除
	 * @param deptIds
	 * @return
	 */
	AjaxMsg deleteDepts(String deptIds);
	
	/**
	 * 机构注册验证
	 * @param deptName
	 * @return
	 */
	AjaxMsg checkDeptName(String deptName);
	
	AjaxMsg checkDeptMobile(String deptMobile);
	
	AjaxMsg checkDeptEmail(String deptEmail);
	
	
	/**
	 * 查询用户所绑定的教育机构
	 * @param userId
	 * @return
	 */
	List<?> deptCombo(UserInfo userInfo);
}