package sys.dao;


import java.util.List;
import java.util.Map;

import sys.model.User;
import sys.model.UserInfo;
import tools.PageUI;
import tools.Pagination;

public interface UserDao {

	/**
	 * 获取指定用户对象
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
	 * 新增用户
	 * @param user
	 * @return
	 */
	Map<String,Object> addUser(User user);
	
	/**
	 * 增加用户绑定，一个用户可能有多个角色,以','分隔
	 * @param user
	 * @param role
	 * @return
	 */
	int addUserBind(User user);
	
	
	/**
	 * 删除用户绑定
	 * @param user
	 * @return
	 */
	int deleteUserBind(User user);
	
	/**
	 * 用户修改
	 * @param userId
	 * @return
	 */
	int updateUser(User user);
	

	/**
	 * 更新用户状态
	 * @param userId
	 * @param value
	 * @return
	 */
	int updateUserStatus(Long[] userIds,String userStatus);
	
	/**
	 * 批量用户密码修改
	 * @param userId
	 * @return
	 */
	int updatePassword(Long[] userIds,String password);
	
	
	/**
	 * 机构所注册的老师
	 * @param deptId
	 * @return
	 */
	List<?> queryTechList(UserInfo userInfo,Long classId);
}
