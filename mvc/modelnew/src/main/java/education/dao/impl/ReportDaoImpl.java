package education.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.model.Role;
import sys.model.UserInfo;
import tools.Page;
import tools.Pagination;
import tools.PageUI;
import education.dao.ReportDao;
import education.model.Lesson;
import education.model.Question;

@Repository
public class ReportDaoImpl implements ReportDao{

	private Logger log = Logger.getLogger(ReportDaoImpl.class);
	
	@Resource
	private NamedParameterJdbcTemplate nameJdbcTemplate;
	
	@Override
	public PageUI questionComplete(Pagination pagination,Lesson lesson,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		StringBuffer stuSql = new StringBuffer();
		
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		
		//学生和家长登录只能看到该学生的情况 老师可以看到班级的全部学生
		if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getUserId());
		}else if(Role.ROLE_parent.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getStuId());
		}
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and lessonId = :lessonId ");
			sqlParam.addValue("lessonId", lesson.getLessonId());
		}
		
		if(!"".equals(lesson.getClassId())&&lesson.getClassId()!=null){
			whereSql.append(" and classId = :classId ");
			sqlParam.addValue("classId", lesson.getClassId());
		}
		
		String sql = "select * from ("
				+ "select a.classId,className,a.lessonId,lessonName,techIdUse,techNameUse,b.stuId,stuName, "
				+ "ifnull(quesSum,0) quesSum,ifnull(quesComp,0) quesComp,ifnull(quesSum-quesComp,0) quesUndo,"
				+ "case when ifnull(quesSum,0)=0 then 0 when ifnull(quesComp,0)=0 then 0 "
				+ "else concat(round(ifnull(quesComp,0)/quesSum*100,2),'%') end quesCompRate from "
				
				//根据用户绑定的班级，查询该班级所上的所有课程:班级课程
				+ "(select b.id classId,class_name className,a.id lessonId,lesson_name lessonName,c.tech_id_use techIdUse, "
				+ "user_name techNameUse from tb_lesson a,(select * from tb_class where id in(select class_id "
				+ "from tb_user_bind where dept_id=:deptId and user_id=:userId))b,tb_class_lesson c,tb_user d "
				+ "where a.id=c.lesson_id and b.id=c.class_id and c.tech_id_use=d.id)a "
				
				//上这个课的学生
				+ "left join "
				+ "(select a.id stuId,user_name stuName,b.class_id classId from tb_user a,tb_user_bind b "
				+ "where a.id=b.user_id and b.role_id="+Role.ROLE_student+")b "
				+ "on a.classId=b.classId "+stuSql+" "
				
				//课件的题目数
				+ "left join"
				+ "(select count(*) as quesSum,lesson_id from tb_question group by lesson_id)c "
				+ "on a.lessonId=c.lesson_id  "
				
				//学生完成题目数
				+ "left join "
				+ "(select count(*) as quesComp,lesson_id,stu_id from tb_answer a group by lesson_id,stu_id)d "
				+ "on a.lessonId=d.lesson_id and b.stuId=d.stu_id)base where 1=1 "+whereSql+" order by lessonId";
		
		sqlParam.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		sqlParam.addValue("userId", userInfo.getUser().getUserId());
		
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParam);
		List<Map<String,Object>> questionCompleteList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(questionCompleteList);
		return rs;
	}

	@Override
	public PageUI answerCorrect(Pagination pagination,Lesson lesson,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		StringBuffer stuSql = new StringBuffer();
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		
		//学生和家长登录只能看到该学生的情况 老师可以看到班级的全部学生
		if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getUserId());
		}else if(Role.ROLE_parent.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getStuId());
		}
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and lessonId = :lessonId ");
			sqlParam.addValue("lessonId", lesson.getLessonId());
		}
		
		if(!"".equals(lesson.getClassId())&&lesson.getClassId()!=null){
			whereSql.append(" and classId = :classId ");
			sqlParam.addValue("classId", lesson.getClassId());
		}
		
		String sql = "select * from ("
				+ "select a.classId,className,a.lessonId,lessonName,techIdUse,techNameUse,b.stuId,stuName, "
				+ "ifnull(quesSum,0) quesSum,ifnull(quesCompRight,0) quesCompRight,ifnull(quesCompError,0) quesCompError, "
				+ "case when ifnull(quesSum,0)=0 then 0 when ifnull(quesCompRight,0)=0 then 0 "
				+ "else concat(round(ifnull(quesCompRight,0)/quesSum*100,2),'%') end quesCompRate from "
				
				//根据用户绑定的班级，查询该班级所上的所有课程:班级课程
				+ "(select b.id classId,class_name className,a.id lessonId,lesson_name lessonName,c.tech_id_use techIdUse, "
				+ "user_name techNameUse from tb_lesson a,(select * from tb_class where id in(select class_id "
				+ "from tb_user_bind where dept_id=:deptId and user_id=:userId))b,tb_class_lesson c,tb_user d "
				+ "where a.id=c.lesson_id and b.id=c.class_id and c.tech_id_use=d.id)a "
				
				//上这个课的学生
				+ "left join "
				+ "(select a.id stuId,user_name stuName,b.class_id classId from tb_user a,tb_user_bind b "
				+ "where a.id=b.user_id and b.role_id="+Role.ROLE_student+")b "
				+ "on a.classId=b.classId "+stuSql+" "
				
				//课件题目数
				+ "left join "
				+ "(select count(*) as quesSum,lesson_id from tb_question group by lesson_id)c "
				+ "on a.lessonId=c.lesson_id "
				
				//课件正确数
				+ "left join "
				+ "(select count(*) as quesCompRight,lesson_id,stu_id from tb_answer where answer_result='1' group by lesson_id,stu_id)d "
				+ "on a.lessonId=d.lesson_id and b.stuId=d.stu_id "
				
				//课件错误数
				+ "left join "
				+ "(select count(*) as quesCompError,lesson_id,stu_id from tb_answer where answer_result='0' group by lesson_id,stu_id)e "
				+ "on a.lessonId=e.lesson_id and b.stuId=e.stu_id)base where 1=1 "+whereSql+" order by lessonId ";
		
		sqlParam.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		sqlParam.addValue("userId", userInfo.getUser().getUserId());
		
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParam);
		List<Map<String,Object>> answerCorrectRateList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(answerCorrectRateList);
		return rs;
	}

	@Override
	public PageUI studentRank(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		StringBuffer stuSql = new StringBuffer();
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		
		//学生和家长登录只能看到该学生的情况 老师可以看到班级的全部学生
		if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getUserId());
		}else if(Role.ROLE_parent.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getStuId());
		}
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and lessonId = :lessonId ");
			sqlParam.addValue("lessonId", lesson.getLessonId());
		}
		
		if(!"".equals(lesson.getClassId())&&lesson.getClassId()!=null){
			whereSql.append(" and classId = :classId ");
			sqlParam.addValue("classId", lesson.getClassId());
		}
		
		//MySQL类似oracle rownum的实现,百分号的实现不能直接||'%'拼接，用concat字符串拼接函数
		String sql = "select case when @lessonName != lessonName then @rownum:= 1 "
				+ "else @rownum:= @rownum + 1 end as stuScoresRank,tmp.* from( "
				//进行排名
				+ "select * from "
				+ "(select a.classId,className,techIdUse,techNameUse,lessonId,lessonName,b.stuId,stuName,quesCompRight from "
				
				//根据用户绑定的班级，查询该班级所上的所有课程:班级课程
				+ "(select b.id classId,class_name className,a.id lessonId,lesson_name lessonName,c.tech_id_use techIdUse, "
				+ "user_name techNameUse from tb_lesson a,(select * from tb_class where id in(select class_id "
				+ "from tb_user_bind where dept_id=:deptId and user_id=:userId))b,tb_class_lesson c,tb_user d "
				+ "where c.tech_id_use=d.id and a.id=c.lesson_id and b.id=c.class_id)a "
				
				//上这个课的学生
				+ "left join "
				+ "(select a.id stuId,user_name stuName,b.class_id classId from tb_user a,tb_user_bind b "
				+ "where a.id=b.user_id and b.role_id="+Role.ROLE_student+")b "
				+ "on a.classId=b.classId "+stuSql+" "
				
				//课件正确数
				+ "left join "
				+ "(select count(*) as quesCompRight,lesson_id,stu_id from tb_answer where answer_result='1' group by lesson_id,stu_id)c "
				+ "on a.lessonId=c.lesson_id and b.stuId=c.stu_id)base_data "
				
				+ "where 1=1 "+whereSql+"  group by lessonId,stuId,quesCompRight desc)tmp,(select @rownum:=0)rw, "
				+ "(select @lessonName:='') lesson ";
		log.info("==stuScoresRankList=="+sql);
		
		sqlParam.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		sqlParam.addValue("userId", userInfo.getUser().getUserId());
		
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParam);
		List<Map<String,Object>> stuScoresRankList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(stuScoresRankList);
		return rs;
	}

	@Override
	public PageUI questionCorrect(Pagination pagination,Lesson lesson, Question question,
			UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		StringBuffer stuSql = new StringBuffer();
		MapSqlParameterSource sqlParam = new MapSqlParameterSource();
		
		//学生和家长登录只能看到该学生的情况 老师可以看到班级的全部学生
		if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getUserId());
		}else if(Role.ROLE_parent.equals(userInfo.getRoles().get(0).getRoleId())){
			stuSql.append(" and b.stuId=:stuId");
			sqlParam.addValue("stuId", userInfo.getUser().getStuId());
		}
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and lessonId = :lessonId ");
			sqlParam.addValue("lessonId", lesson.getLessonId());
		}
		
		if(!"".equals(lesson.getClassId())&&lesson.getClassId()!=null){
			whereSql.append(" and classId = :classId ");
			sqlParam.addValue("classId", lesson.getClassId());
		}
		
		
		String sql = "select a.classId,className,techIdUse,techNameUse,lessonId,lessonName,"
				+ "c.question_no questionNo,question,ifnull(answerSum,0) answerSum, "
				+ "ifnull(answerRight,0) answerRight, "
				+ "case when ifnull(answerSum,0)=0 then '-- 未作答 --' when ifnull(answerRight,0)=0 then 0 "
				+ "else concat(round(ifnull(answerRight,0)/answerSum*100,2),'%') end as answerRate from "
				
				+ "(select * from tb_question )c "
				
				+ "left join "
				//根据用户绑定的班级，查询该班级所上的所有课程:班级课程
				+ "(select b.id classId,class_name className,a.id lessonId,lesson_name lessonName,c.tech_id_use techIdUse, "
				+ "user_name techNameUse from tb_lesson a,(select * from tb_class where id in(select class_id "
				+ "from tb_user_bind where dept_id=:deptId and user_id=:userId))b,tb_class_lesson c,tb_user d "
				+ "where c.tech_id_use=d.id and a.id=c.lesson_id and b.id=c.class_id)a "
				+ "on a.lessonId=c.lesson_id "
				
				//上这个课的学生
				+ "left join "
				+ "(select a.id stuId,user_name stuName,b.class_id classId from tb_user a,tb_user_bind b "
				+ "where a.id=b.user_id and b.role_id="+Role.ROLE_student+")b "
				+ "on a.classId=b.classId "+stuSql+" "

				//题目的答题数
				+ "left join "
				+ "(select count(*) answerSum,lesson_id,class_id,question_no,stu_id from tb_answer group by question_no)d "
				+ "on a.lessonId=d.lesson_id and a.classId=d.class_id and c.question_no=d.question_no "
				
				//题目答题正确数
				+ "left join "
				+ "(select count(*) answerRight,lesson_id,class_id,question_no,stu_id from tb_answer where answer_result='1' group by lesson_id,class_id,question_no,stu_id)e "
				+ "on a.lessonId=e.lesson_id and a.classId=e.class_id and c.question_no=e.question_no";
		
		sqlParam.addValue("deptId", userInfo.getRoles().get(0).getDeptId());
		sqlParam.addValue("userId", userInfo.getUser().getUserId());
		
		Page page = new Page(sql,pagination.getPage(),pagination.getRows(),nameJdbcTemplate,sqlParam);
		List<Map<String,Object>> quesCorrectRateList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(quesCorrectRateList);
		return rs;
	}

	@Override
	public PageUI answersByQuestionId(Pagination pagination,Long lessonId, Long questionNo) {
		// TODO 自动生成的方法存根
		String sql = "select a.stu_id stuId,b.user_name stuName,stu_answer stuAnswer,"
				+ "if(answer_result=0,'错','对') as answerResult from "
				+ "tb_answer a,tb_user b where a.stu_id=b.id and "
				+ "lesson_id=:lessonId and question_no=:questionNo ";
		
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("lessonId", lessonId);
		sqlParameterSource.addValue("questionNo", questionNo);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> answersByQuestionIdList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(answersByQuestionIdList);
		return rs;
	}

	@Override
	public PageUI lessonCorrect(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(lesson.getLessonId())&&lesson.getLessonId()!=null){
			whereSql.append(" and a.lesson_id = :lessonId ");
		}
		if(!"".equals(lesson.getDeptId())&&lesson.getDeptId()!=null){
			whereSql.append(" and c.dept_id = :deptId ");
		}
		
		String sql = "select dept_name deptName,techNameUse,a.lesson_id lessonId,lesson_name lessonName,ifnull(lessonSum,0) lessonSum, "
				+ "ifnull(lessonRight,0) lessonRight,ifnull(lessonError,0) lessonError, "
				+ "case when ifnull(lessonSum,0)=0 then '-- 未作答 --' when ifnull(lessonRight,0)=0 then 0 "
				+ "else concat(round(ifnull(lessonRight,0)/lessonSum*100,2),'%') end as lessonRate from "
				+ "(select dept_name,d.user_name techNameUse,a.id lesson_id,lesson_name,lessonSum from "
				+ "(select dept_id,tech_id_own,id,lesson_name from tb_lesson )a, "
				+ "(select count(*) as lessonSum,lesson_id from tb_question group by lesson_id)b, "
				+ "tb_dept c,tb_user d,tb_tech_lesson e where a.id=b.lesson_id and a.id=e.lesson_id  "
				+ "and a.dept_id=c.id and e.tech_id_use=d.id "+whereSql+")a "
				+ "left join "
				+ "(select count(*) as lessonRight,lesson_id from tb_answer where answer_result='1' group by lesson_id,stu_id)c "
				+ "on a.lesson_id=c.lesson_id "
				+ "left join "
				+ "(select count(*) as lessonError,lesson_id from tb_answer where answer_result='0' group by lesson_id,stu_id)d "
				+ "on a.lesson_id=d.lesson_id ";
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(lesson);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), nameJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> lessonCorrectRateList = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(lessonCorrectRateList);
		return rs;
	}
}
