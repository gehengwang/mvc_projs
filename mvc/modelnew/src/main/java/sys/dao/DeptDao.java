package sys.dao;

import java.util.List;
import java.util.Map;

import sys.model.Dept;
import sys.model.UserInfo;

public interface DeptDao {

	
	
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
	Map<String,Object> addDept(Dept dept);
	
	/**
	 * 根据机构id更新机构代码
	 * @param deptId
	 * @return
	 */
	int updateDeptCode(Long deptId);
	
	/**
	 * 机构修改
	 * @param dept
	 * @return
	 */
	int updateDept(Dept dept);
	
	/**
	 * 机构删除：提供批量删除
	 * @param deptIds
	 * @return
	 */
	int deleteDepts(String deptIds);
	
	/**
	 * 查询用户所绑定的教育机构
	 * @param userId
	 * @return
	 */
	List<?> deptListMap(UserInfo userInfo);
	
}