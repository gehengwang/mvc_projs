package sys.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.dao.ClassesDao;
import sys.model.Classes;
import sys.model.Role;
import sys.model.UserInfo;
import tools.Page;
import tools.PageUI;
import tools.Pagination;

@Repository
public class ClassesDaoImpl implements ClassesDao {

	@Resource
	private NamedParameterJdbcTemplate nameJdbcTemplate;
	
	@Override
	public List<?> queryClassesList(UserInfo userInfo,Long deptId) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		
		
		if(null!=deptId){
			whereSql.append("and dept_id=:deptId ");
			sqlParam.addValue("deptId", deptId);
		}else if(null!=userInfo){
				if(!Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
					whereSql.append(" and id in(select class_id from tb_user_bind where user_id=:userId)");
					sqlParam.addValue("userId", userInfo.getUser().getUserId());
				}
				whereSql.append("and dept_id=:deptId ");
				sqlParam.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		}
		String sql = "select id classId,class_name className, "
				+ "if(class_status=0,'停用','启用') classStatusName from tb_class where "
				+ "class_status=1 "+whereSql+" order by id desc";
		
		List<?> list = nameJdbcTemplate.queryForList(sql, sqlParam);
		return list;
	}

	@Override
	public int addClassLesson(Classes classes) {
		// TODO 自动生成的方法存根
		String sql = "insert into tb_class_lesson(dept_id,tech_id_use,class_id,lesson_id,"
				+ "question_status,answer_status)"
				+ "values"
				+ "(:deptId,:techIdUse,:classId,:lessonId,:questionStatus,:answerStatus)";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		int rs = nameJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int updateClassLessonStatus(Classes classes) {
		// TODO 自动生成的方法存根
		String sql = "update tb_class_lesson set question_status=:questionStatus, "
				+ "answer_status=:answerStatus where lesson_id=:lessonId and class_id=:classId ";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(classes);
		int rs = nameJdbcTemplate.update(sql, sqlParameterSource);
		return rs;
	}
	
	
	@Override
	public PageUI queryClassPage(Pagination pagination,UserInfo userInfo, Classes classes) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(classes.getClassId())&&null!=classes.getClassId()){
			whereSql.append(" and a.id=:classId");
		}
		
		//管理员查询所有班级、用户绑定的班级
		if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
			if(!"".equals(classes.getUserId())&&null!=classes.getUserId()){
				whereSql.append(" and a.id in(select class_id from tb_user_bind where user_id=:userId)");
				classes.setUserId(classes.getUserId());
			}
		}else{//非管理员查询绑定的班级
			whereSql.append(" and a.id in(select class_id from tb_user_bind where user_id=:userId)");
			if(!"".equals(classes.getUserId())&&null!=classes.getUserId()){
				classes.setUserId(classes.getUserId());
			}else{
				classes.setUserId(userInfo.getUser().getUserId());
			}
		}
		
		classes.setDeptId(userInfo.getRoles().get(0).getDeptId());
		
		String sql = "select a.id classId,a.class_name className,b.dept_name deptName,"
				+ "'---选课详情---' lessonName,class_status classStatus, "
				+ "if(class_status=0,'停用','启用') classStatusName "
				+ "from tb_class a,tb_dept b where a.dept_id=b.id and "
				+ "a.dept_id=:deptId "+whereSql+" order by a.id desc";
		
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParam);
		List<Map<String,Object>> listClass = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(listClass);
		return rs;
	}

	@Override
	public PageUI classLessonPage(Pagination pagination,Classes classes,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(classes.getLessonId())&&classes.getLessonId()!=null){
			whereSql.append(" and a.id = :lessonId ");
		}
		
		//指定班级课程
		if(!"".equals(classes.getClassId())&&classes.getClassId()!=null){
			whereSql.append(" and c.id = :classId ");
		}
		
		if(!"".equals(classes.getTechIdUse())&&classes.getTechIdUse()!=null){
			whereSql.append(" and d.tech_id_use = :userId ");
		}else{//自己所选班级课程
			if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and d.tech_id_use = :userId");
				classes.setUserId(userInfo.getUser().getUserId());
			}else if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
				whereSql.append(" and d.class_id in(select class_id from tb_user_bind where user_id= :userId) ");
				classes.setUserId(userInfo.getUser().getUserId());
			}
			
		}
		
		String sql = "select a.id lessonId,lesson_no lessonNo,lesson_name lessonName,a.tech_id_own techIdOwn,"
				+ "a.dept_id deptId,b.user_name techNameOwn,d.question_status questionStatus, "
				+ "if(d.question_status=0,'否','是') as questionStatusName,d.answer_status answerStatus, "
				+ "if(d.answer_status=0,'否','是') as answerStatusName,lesson_status lessonStatus, "
				+ "a.create_date createDate,a.modify_date modifyDate,c.id classId,c.class_name className, "
				+ "e.id techIdUse,e.user_name techNameUse from "
				+ "tb_lesson a,tb_user b,tb_class c,tb_class_lesson d,tb_user e "
				+ "where a.id=d.lesson_id and b.id=a.tech_id_own and c.id=d.class_id and d.tech_id_use=e.id "
				+ "and c.class_status=1 and c.dept_id=:deptId "+whereSql+" order by c.id desc";
		
		
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParam);
		List<Map<String,Object>> classList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(classList);
		return rs;
	}
	
	@Override
	public int insertClass(Classes classes) {
		// TODO 自动生成的方法存根
		String sql = "insert into tb_class(class_name,class_no,dept_id,class_status)values("
				+ ":className,:classNo,:deptId,:classStatus)";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		int rs = nameJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	@Override
	public int updateClass(Classes classes) {
		// TODO 自动生成的方法存根
		String sql = "update tb_class set class_name=:className,class_status=:classStatus where id=:classId ";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		int rs = nameJdbcTemplate.update(sql, sqlParam);
		return rs;
	}

	
	@Override
	public int deleteClassLesson(Classes classes) {
		// TODO 自动生成的方法存根
		List<Long> classIds = Arrays.asList(classes.getClassIds());
		List<Long> lessonIds = Arrays.asList(classes.getLessonIds());
		String sql = "delete from tb_class_lesson where class_id in(:classIds) and "
				+ "dept_id=:deptId and tech_id_use=:techIdUse and lesson_id not in(:lessonIds)";
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		mapParam.addValue("classIds", classIds);
		mapParam.addValue("lessonIds", lessonIds);
		mapParam.addValue("deptId", classes.getDeptId());
		mapParam.addValue("techIdUse", classes.getTechIdUse());
		int rs = 0;
		rs += nameJdbcTemplate.update(sql, mapParam);
		return rs;
	}
	
	public Classes queryClassObject(Classes classes){
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(classes.getClassName())&&classes.getClassName()!=null){
			whereSql.append(" and class_name=:className");
		}
		
		String sql = "select id classId,class_name className "
				+ "from tb_class where 1=1 "+whereSql+" ";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		
		Classes classesObj = null;
		try{
			classesObj = nameJdbcTemplate.queryForObject(sql, sqlParam, 
					new BeanPropertyRowMapper<Classes>(Classes.class));
		}catch(EmptyResultDataAccessException  e){
			return null;
		}
		return classesObj;
	}

	@Override
	public int checkClassLesson(Classes classes) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from tb_class_lesson where dept_id=:deptId and "
				+ "class_id=:classId and lesson_id=:lessonId and tech_id_use=:techIdUse ";
		SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(classes);
		int rs = nameJdbcTemplate.queryForObject(sql, sqlParam, Integer.class);
		return rs;
	}

}
