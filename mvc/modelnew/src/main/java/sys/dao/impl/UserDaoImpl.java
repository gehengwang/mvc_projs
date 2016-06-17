package sys.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sys.dao.UserDao;
import sys.model.Role;
import sys.model.User;
import sys.model.UserInfo;
import tools.Page;
import tools.PageUI;
import tools.Pagination;

@Repository
public class UserDaoImpl implements UserDao{

	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate nameJdbcTemplate;
	
	@Override
	public User queryUserObj(User user) {
		// TODO 自动生成的方法存根
		//防止sql注入
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(user.getUserName())&&user.getUserName()!=null){
			whereSql.append(" and user_name=:userName");
		}
		if(!"".equals(user.getMobile())&&user.getMobile()!=null){
			whereSql.append(" and mobile=:mobile");
		}
		
		String sql = "select id userId,user_name userName,mobile,password,sex,qq,email,"
				+ "weixin,school_name schoolName,user_status userStatus,create_date createDate,"
				+ "login_times loginTimes,answer_times answerTimes,stu_id stuId "
				+ "from tb_user where 1=1 "+whereSql;
		
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(user);
		List<User> listUser = nameJdbcTemplate.query(sql,sqlParam, new BeanPropertyRowMapper<User>(User.class));
		User userObj = null;
		if(listUser.size()>0){
			userObj=listUser.get(0);
		}
		//spring空对象时不返回null,而是抛异常
		/*try{
			userObj = nameJdbcTemplate.queryForObject(sql, sqlParam, new BeanPropertyRowMapper<User>(User.class));
		}catch(EmptyResultDataAccessException  e){
			return null;
		}*/
		return userObj;
	}

	@Override
	public PageUI queryUserPage(Pagination pagination,User user,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		MapSqlParameterSource sqlMap = new MapSqlParameterSource();

		if(!"".equals(user.getUserName())&&user.getUserName()!=null){
			whereSql.append(" and userName like concat('%',:userName,'%') ");
			sqlMap.addValue("userName", user.getUserName());
		}
		
		if(!"".equals(user.getClassId())&&user.getClassId()!=null){
			whereSql.append(" and class_id = :classId ");
			sqlMap.addValue("classId", user.getClassId());
		}
		
		sqlMap.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		
		//学生信息
		if("0".equals(user.getFlag())){
			//学生只能看到自己信息
			if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_student+" and userId=:userId ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//老师看到自己所带班学生信息
			else if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_student+" and userId in("
						+ "select user_id from tb_user_bind where class_id in( "
						+ "select class_id from tb_user_bind where user_id=:userId)) ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//管理员看到所有学生信息
			else if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
				if(!"".equals(user.getTechId())&&user.getTechId()!=null){
					whereSql.append(" and role_id="+Role.ROLE_student+" and userId in("
							+ "select user_id from tb_user_bind where class_id in( "
							+ "select class_id from tb_user_bind where user_id=:userId)) ");
					sqlMap.addValue("userId", user.getTechId());
				}else{
					whereSql.append(" and role_id="+Role.ROLE_student+" ");
				}
			}
		}else if("1".equals(user.getFlag())){//老师信息
			//老师看到自己信息
			if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_tech+" and userId=:userId ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//管理员看到所有老师信息
			else if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_tech+" ");
			}
		}
		
		//group_concat分隔符SEPARATOR
		String sql = "select * from (select a.id userId,user_name userName,mobile,if(sex=0,'男','女') sexName, "
				+ "qq,email,weixin,school_name schoolName,if(user_status=0,'停用','启用') userStatusName, "
				+ "user_status userStatus,'---班级详情---' className,'---课程详情---' lessonName,class_id,dept_id,role_id  "
				+ "from tb_user a "
				+ "left join "
				+ "tb_user_bind b on a.id=b.user_id and b.dept_id=:deptId)base "
				+ "where 1=1 "+whereSql+" group by userId ";
		
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlMap);
		List<Map<String,Object>> listUser = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(listUser);
		return rs;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryUserList(User user, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		MapSqlParameterSource sqlMap = new MapSqlParameterSource();

		if(!"".equals(user.getUserName())&&user.getUserName()!=null){
			whereSql.append(" and userName like concat('%',:userName,'%') ");
			sqlMap.addValue("userName", user.getUserName());
		}
		
		if(!"".equals(user.getClassId())&&user.getClassId()!=null){
			whereSql.append(" and class_id = :classId ");
			sqlMap.addValue("classId", user.getClassId());
		}
		
		sqlMap.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		
		//学生信息
		if("0".equals(user.getFlag())){
			//学生只能看到自己信息
			if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_student+" and userId=:userId ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//老师看到自己所带班学生信息
			else if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_student+" and userId in("
						+ "select user_id from tb_user_bind where class_id in( "
						+ "select class_id from tb_user_bind where user_id=:userId)) ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//管理员看到所有学生信息
			else if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
				if(!"".equals(user.getTechId())&&user.getTechId()!=null){
					whereSql.append(" and role_id="+Role.ROLE_student+" and userId in("
							+ "select user_id from tb_user_bind where class_id in( "
							+ "select class_id from tb_user_bind where user_id=:userId)) ");
					sqlMap.addValue("userId", user.getTechId());
				}else{
					whereSql.append(" and role_id="+Role.ROLE_student+" ");
				}
			}
		}else if("1".equals(user.getFlag())){//老师信息
			//老师看到自己信息
			if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_tech+" and userId=:userId ");
				sqlMap.addValue("userId", userInfo.getUser().getUserId());
			}//管理员看到所有老师信息
			else if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and role_id="+Role.ROLE_tech+" ");
			}
		}
		
		//group_concat分隔符SEPARATOR
		String sql = "select * from (select a.id userId,user_name userName,mobile,if(sex=0,'男','女') sexName, "
				+ "qq,email,weixin,school_name schoolName,if(user_status=0,'停用','启用') userStatusName, "
				+ "user_status userStatus,class_id,dept_id,role_id  "
				+ "from tb_user a "
				+ "left join "
				+ "tb_user_bind b on a.id=b.user_id and b.dept_id=:deptId)base "
				+ "where 1=1 "+whereSql+" group by userId ";
		
		List<?> listUser = nameJdbcTemplate.queryForList(sql, sqlMap);
		return (List<User>) listUser;
	}

	@Override
	public Map<String,Object> addUser(User user) {
		// TODO 自动生成的方法存根
		//用户状态默认禁用,需要管理员去启用才能使用
		String sql = "insert into tb_user(user_name,mobile,password,sex,"
				+ "qq,email,weixin,school_name,user_status,create_date,stu_id)"
				+ "values"
				+ "(:userName,:mobile,:password,:sex,:qq,:email,:weixin,:schoolName,0,now(),:stuId )";
		SqlParameterSource userParam = new BeanPropertySqlParameterSource(user); 
		KeyHolder keyHolder=new GeneratedKeyHolder();
		int rs = nameJdbcTemplate.update(sql, userParam,keyHolder);
		int userId = keyHolder.getKey().intValue();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rs", rs);map.put("userId", userId);
		return map;
	}

	@Override
	public int updateUser(User user) {
		// TODO 自动生成的方法存根
		String sql = "update tb_user set mobile=:mobile,qq=:qq,email=:email"
				+ " where id=:userId";
		SqlParameterSource userParam = new BeanPropertySqlParameterSource(user);
		int rs = nameJdbcTemplate.update(sql, userParam);
		return rs;
	}

	@Override
	public int updatePassword(Long[] userIds,String password) {
		// TODO 自动生成的方法存根
		//数组和列表的区别
		List<Long> ids = Arrays.asList(userIds);
		String sql = "update tb_user set password=:password where id in(:userIds) ";
		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("userIds", ids);
		mapSql.addValue("password", password);
		int rs = nameJdbcTemplate.update(sql, mapSql);
		return rs;
	}

	@Override
	public int addUserBind(User user) {
		// TODO 自动生成的方法存根
		Long[] userIds = user.getUserIds();
		Long[] classIds = user.getClassIds();
		int rs = 0;
		for(int i=0;i<userIds.length;i++){
			user.setUserId(userIds[i]);
			if(!"".equals(classIds)&&null!=classIds){
				for(int j=0;j<classIds.length;j++){
					user.setClassId(classIds[j]);
					String sql = "insert into tb_user_bind(user_id,dept_id,role_id,class_id) "
							+ "values"
							+ "(:userId,:deptId,:roleId,:classId)";
					SqlParameterSource param = new BeanPropertySqlParameterSource(user);
					rs += nameJdbcTemplate.update(sql, param);
				}
			}else{
				String sql = "insert into tb_user_bind(user_id,dept_id,role_id,class_id) "
						+ "values"
						+ "(:userId,:deptId,:roleId,:classId)";
				SqlParameterSource param = new BeanPropertySqlParameterSource(user);
				rs += nameJdbcTemplate.update(sql, param);
			}
			
		}
		return rs;
	}
	
	
	@Override
	public int deleteUserBind(User user) {
		// TODO 自动生成的方法存根
		List<Long> userIds = Arrays.asList(user.getUserIds());
		String sql = "delete from tb_user_bind "
				+ " where dept_id=:deptId and user_id in(:userIds)";
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		mapParam.addValue("userIds", userIds);
		mapParam.addValue("deptId", user.getDeptId());
		int rs = nameJdbcTemplate.update(sql, mapParam);
		return rs;
	}

	@Override
	public List<?> queryTechList(UserInfo userInfo,Long classId) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		MapSqlParameterSource map = new MapSqlParameterSource();
		//查询某班级的老师
		if(!"".equals(classId)&&null!=classId){
			whereSql.append(" and class_id = :classId");
			map.addValue("classId", classId);
		}
		
		//非管理员只能查到自己绑定班级的老师
		if(!Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
			whereSql.append(" and user_id = :userId ");
			map.addValue("userId", userInfo.getUser().getUserId());
		}
		
		String sql = "select id techId,user_name techName from tb_user a "
				//查询该机构的所有老师
				+ "where id in(select user_id from tb_user_bind where dept_id = :deptId and role_id=:roleId "
				//所在班级的老师
				+ "and class_id "
				+ "in(select class_id from tb_user_bind where dept_id = :deptId "+whereSql+" )) ";
		
		map.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		map.addValue("roleId", Role.ROLE_tech);
		
		List<?> listMap = nameJdbcTemplate.queryForList(sql, map);
		return listMap;
	}

	@Override
	public int updateUserStatus(Long[] userIds, String userStatus) {
		// TODO 自动生成的方法存根
		List<Long> ids = Arrays.asList(userIds);
		String sql = "update tb_user set user_status=:userStatus where id in(:userId) ";
		MapSqlParameterSource mapSql = new MapSqlParameterSource();
		mapSql.addValue("userId", ids);
		mapSql.addValue("userStatus", userStatus);
		int rs = nameJdbcTemplate.update(sql, mapSql);
		return rs;
	}

}