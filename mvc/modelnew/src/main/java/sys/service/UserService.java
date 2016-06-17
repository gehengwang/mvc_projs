package sys.service;


import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;

public interface UserService {

	/**
	 * 根据条件获取指定用户对象
	 * @param user
	 * @return
	 */
	User queryUserObj(User user);
	
	/**
	 * 用户信息查询：
	 * 学生查自己信息、老师和机构管理员查询所有
	 * @param pagination
	 * @param user
	 * @param userInfo
	 * @return
	 */
	PageUI queryUserPage(Pagination pagination, User user,UserInfo userInfo);
	
	/**
	 * 用户信息列表
	 * @param user
	 * @param userInfo
	 * @return
	 */
	List<User> queryUserList(User user,UserInfo userInfo);
	
	/**
	 * 增加用户和用户角色，一个用户可能有多个角色,以','分隔
	 * 前台复选框选择角色，则后台先删除原用户角色，再重新添加
	 * @param user
	 * @return
	 */
	
	AjaxMsg saveUser(User user);
	
	/**
	 * 增加用户绑定，一个用户可能有多个角色,以','分隔
	 * @param user
	 * @param role
	 * @return
	 */
	AjaxMsg saveUserBind(User user);
	
	
	/**
	 * 用户修改
	 * @param userId
	 * @return
	 */
	AjaxMsg updateUser(User user);
	
	
	/**
	 * 更新用户状态
	 * @param userId
	 * @param value
	 * @return
	 */
	AjaxMsg updateUserStatus(Long[] userIds,String userStatus);
	
	/**
	 * 用户密码修改
	 * @param userId
	 * @return
	 */
	AjaxMsg updatePassword(Long[] userIds,String password);
	
	/**
	 * 检查用户名和手机号码是否已注册
	 * @param userName
	 * @param mobile
	 * @return
	 */
	AjaxMsg checkUser(User user);
	
	/**
	 * 校验家长所选的学生是否存在
	 * @param userStr
	 * @return
	 */
	AjaxMsg validateStudent(User user);
	
	/**
	 * 机构所注册的老师
	 * @param deptId
	 * @return
	 */
	 List<?> techCombo(UserInfo userInfo,Long classId);
	 
	 /**
	  * 批量导入用户
	  * @param workbook
	  * @param userInfo
	  * @return
	  */
	 AjaxMsg importUsers(Workbook workbook,UserInfo userInfo,User user);
	
}