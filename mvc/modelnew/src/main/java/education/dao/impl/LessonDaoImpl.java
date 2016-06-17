package education.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sys.model.Role;
import sys.model.UserInfo;
import tools.Page;
import tools.PageUI;
import tools.Pagination;
import education.dao.LessonDao;
import education.model.Lesson;

@Repository
public class LessonDaoImpl implements LessonDao{

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public PageUI queryLessonPage(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and a.id = :lessonId ");
		}
		lesson.setDeptId(Long.parseLong(userInfo.getRoles().get(0).getDeptId()+""));
		
		if(!"".equals(lesson.getTechIdUse())&&lesson.getTechIdUse()!=null){
			//管理员查看老师课件
			lesson.setTechIdUse(lesson.getTechIdUse());
		}else{
			//老师查看自己课件
			lesson.setTechIdUse(userInfo.getUser().getUserId());
		}
		
		
		String sql = "select c.id lessonId,lesson_no lessonNo,lesson_name lessonName, "
				+ "a.tech_id_use techIdUse,a.dept_id deptId,b.user_name techNameUse,  "
				+ "lesson_status lessonStatus,a.create_date createDate,a.modify_date modifyDate, "
				+ "d.user_name techNameOwn from tb_tech_lesson a,tb_user b,tb_lesson c ,tb_user d  "
				+ "where a.tech_id_use=b.id and a.lesson_id=c.id and a.dept_id=c.dept_id and  "
				+ "c.tech_id_own=d.id and a.dept_id=:deptId and a.tech_id_use=:techIdUse ";
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(lesson);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), namedParameterJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> lessonList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(lessonList);
		return rs;
	}
	
	
	@Override
	public Map<String,Object> addLesson(Lesson lesson) {
		// TODO 自动生成的方法存根
		
		String sql = "insert into tb_lesson(lesson_name,tech_id_own,dept_id,create_date,"
				+ "modify_date,lesson_status)"
				+ "values"
				+ "(:lessonName,:techIdOwn,:deptId,now(),now(),:lessonStatus)";
		SqlParameterSource userParam = new BeanPropertySqlParameterSource(lesson); 
		KeyHolder keyHolder=new GeneratedKeyHolder();
		int rs = namedParameterJdbcTemplate.update(sql, userParam,keyHolder);
		int lessonId = keyHolder.getKey().intValue();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rs", rs);map.put("lessonId", lessonId);
		return map;
	}

	@Override
	public int updateLesson(Lesson lesson) {
		// TODO 自动生成的方法存根
		String sql = "update tb_lesson set lesson_name=:lessonName,modify_date=now(), "
				+ "lesson_status=:lessonStatus where id=:lessonId ";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(lesson);
		int rs = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
		return rs;
	}

	@Override
	public int deleteLesson(Long lessonId) {
		// TODO 自动生成的方法存根
		String sql = "delete from tb_lesson where id=:lessonId ";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("lessonId", lessonId);
		int rs = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
		return rs;
	}

	@Override
	public List<?> queryLessonList(UserInfo userInfo,String lessonName) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		sqlParam.addValue("deptId",userInfo.getRoles().get(0).getDeptId());
		
		if(!"".equals(lessonName)&&null!=lessonName){
			whereSql.append(" and lesson_name like concat('%',:lessonName,'%') ");
			sqlParam.addValue("lessonName", lessonName);
		}
		
		String sql = "";
		
		//管理员看到所有课程
		if(Role.ROLE_dept_admin.equals(userInfo.getRoles().get(0).getRoleId())){
			sql = "select a.id lessonId,a.lesson_name lessonName,b.user_name techNameOwn "
				+ "from tb_lesson a,tb_user b where a.tech_id_own=b.id and dept_id=:deptId "+whereSql;
		}else if(Role.ROLE_tech.equals(userInfo.getRoles().get(0).getRoleId())){//老师看到自己配置的课程
			sql = "select a.id lessonId,a.lesson_name lessonName,b.user_name techNameOwn "
			    + "from tb_lesson a,tb_user b where a.tech_id_own=b.id and dept_id=:deptId and "
			    + "a.id in(select lesson_id from tb_tech_lesson where tech_id_use=:techIdUser)"+whereSql;
			sqlParam.addValue("techIdUser", userInfo.getUser().getUserId());
		}else{//学生看到自己班级的所有课程
			sql = "select a.id lessonId,a.lesson_name lessonName,b.user_name techNameOwn "
				+ "from tb_lesson a,tb_user b where a.tech_id_own=b.id and dept_id=:deptId and "
				+ "a.id in(select lesson_id from tb_class_lesson where class_id in(select class_id from "
				+ "tb_user_bind where user_id=:userId))"+whereSql;
			sqlParam.addValue("userId", userInfo.getUser().getUserId());
		}
		
		List<?> listMap = namedParameterJdbcTemplate.queryForList(sql, sqlParam);
		return listMap;
	}

	@Override
	public int updateLessonStatus(Lesson lesson) {
		// TODO 自动生成的方法存根
		String sql = "update tb_lesson set lesson_status=:lessonStatus where lesson_id=:lessonId ";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(lesson);
		int rs = namedParameterJdbcTemplate.update(sql, sqlParameterSource);
		return rs;
	}


	@Override
	public int checkLessonName(Lesson lesson) {
		// TODO 自动生成的方法存根
		String sql = "select count(*) from tb_lesson where lesson_name=:lessonName ";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(lesson);
		int rs = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, Integer.class);
		return rs;
	}

	@Override
	public int addTechLesson(Lesson lesson) {
		// TODO 自动生成的方法存根
		Long[] techIds = lesson.getTechIds();
		Long[] lessonIds = lesson.getLessonIds();
		int rs = 0;
		for(int i=0;i<techIds.length;i++){
			lesson.setTechIdUse(techIds[i]);
			for(int j=0;j<lessonIds.length;j++){
				lesson.setLessonId(lessonIds[j]);
				SqlParameterSource param = new BeanPropertySqlParameterSource(lesson); 
				String sql = "insert into tb_tech_lesson(dept_id,tech_id_use,lesson_id,create_date,"
						+ "modify_date)"
						+ "values"
						+ "(:deptId,:techIdUse,:lessonId,now(),now())";
				rs += namedParameterJdbcTemplate.update(sql, param);
			}
		}
		return rs;
	}

	@Override
	public int deleteTechLesson(Long deptId,Long[] techIds) {
		// TODO 自动生成的方法存根
		List<Long> idsList = Arrays.asList(techIds);
		String sql = "delete from tb_tech_lesson where dept_id=:deptId and tech_id_use in(:techIds)";
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		sqlParam.addValue("deptId", deptId);
		sqlParam.addValue("techIds", idsList);
		int rs = namedParameterJdbcTemplate.update(sql, sqlParam);
		return rs;
	}
}
