package education.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.model.Role;
import sys.model.UserInfo;
import tools.CommonUtil;
import tools.Page;
import tools.PageUI;
import tools.Pagination;
import education.dao.AnswerDao;
import education.model.Answer;

@Repository
public class AnswerDaoImpl implements AnswerDao{

	@Resource
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public PageUI answerPage(Pagination pagination,Answer answer,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(answer.getLessonId())&&null!=answer.getLessonId()){
			whereSql.append(" and lesson_id = :lessonId ");
		}
		
		if(!"".equals(answer.getQuestionNo())&&null!=answer.getQuestionNo()){
			whereSql.append(" and question_no = :questionNo ");
		}
		
		if(!"".equals(answer.getQuestion())&&null!=answer.getQuestion()){
			whereSql.append(" and question like  concat('%',:question,'%') ");
		}
		
		if(!"".equals(answer.getAnswerResult())&&null!=answer.getAnswerResult()){
			whereSql.append(" and answer_result = :answerResult ");
		}
		
		if(!"".equals(answer.getClassId())&&null!=answer.getClassId()){
			whereSql.append(" and class_id = :classId ");
		}
		
		if(Role.ROLE_student.equals(userInfo.getRoles().get(0).getRoleId())){
			whereSql.append("and stu_id='"+userInfo.getUser().getUserId()+"'");
			answer.setStuId(userInfo.getUser().getUserId());
		}
		
		String sql = "select lesson_id lessonId,question_no questionNo,question,stu_id stuId, "
				+ "a.stu_answer stuAnswer,answer_result answerResult,error_flag errorFlag, "
				+ "tech_answer techAnswer,case when answer_result='0' then '错误' when "
				+ "answer_result='1' then '正确' else '--未知--' end as answerResultName,class_id, "
				+ "user_name stuName from( "
				+ "select question,tech_answer,b.* from tb_question a  "
				+ "left join "
				+ "(select lesson_id,question_no,a.stu_id,stu_answer,answer_result,error_flag,"
				+ "answer_type,class_id,user_name from tb_answer a,tb_user b where a.stu_id=b.id)b "
				+ "on a.lesson_id=b.lesson_id and a.question_no=b.question_no)a "
				+ "where 1=1 "+whereSql+" order by question_no";
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(answer);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), namedJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> listMap = page.getResultList();
		//将List<Map<>>转为List<object>,要对List<object>做处理
		List<Answer> answerList = new ArrayList<Answer>();
		for(Map<String,Object> map : listMap){
			Answer answerObj = new Answer();
			try {
				BeanUtils.populate(answerObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			answerList.add(answerObj);
		}
		//错误位置标红 算法
		for(int i=0;i<answerList.size();i++){
			int[][] regions = CommonUtil.megerRegions(CommonUtil.getRegionFromStrWithConstraint(answerList.get(i).getErrorFlag(),answerList.get(i).getStuAnswer().length()-1));
			answerList.get(i).setErrorFlagShow(regions);
			answerList.get(i).setTechAnswer(answerList.get(i).getTechAnswer().replaceAll("\n", "<br>"));
			answerList.get(i).setStuAnswer(answerList.get(i).getStuAnswer());
		}
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(answerList);
		return rs;
	}
	
	@Override
	public List<Answer> answerList(Answer answer,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
		if(!"".equals(answer.getLessonId())&&null!=answer.getLessonId()){
			whereSql.append(" and a.lesson_id = :lessonId ");
		}
		
		if(!"".equals(answer.getQuestionNo())&&null!=answer.getQuestionNo()){
			whereSql.append(" and a.question_no = :questionNo ");
		}
		
		if(!"".equals(answer.getQuestion())&&null!=answer.getQuestion()){
			whereSql.append(" and b.question like  concat('%',:question,'%') ");
		}
		
		if(!"".equals(answer.getAnswerResult())&&null!=answer.getAnswerResult()){
			whereSql.append(" and a.answer_result = :answerResult ");
		}
		
		if(!"".equals(answer.getClassId())&&null!=answer.getClassId()){
			whereSql.append(" and a.class_id = :classId ");
		}
		
		String sql = "select a.lesson_id lessonId,a.question_no questionNo,question,stu_answer stuAnswer,"
				+ "answer_result answerResult,error_flag errorFlag,tech_answer techAnswer, "
				+ "case when answer_result='0' then '错误' when answer_result='1' then '正确' else '--未知--' end as answerResultName, "
				+ "c.user_name stuName from tb_answer a,tb_question b,tb_user c "
				+ "where a.stu_id=c.id and a.lesson_id=b.lesson_id and a.question_no=b.question_no "
				+ " "+whereSql+" order by a.question_no ";
		
		//未完成题目学生
		String sql_undo = "select group_concat(a.user_name) stuName from tb_user a,tb_user_bind b "
				+ "where a.id=b.user_id and class_id=:classId and role_id="+Role.ROLE_student+" "
				+ "and a.id not in(select stu_id from tb_answer where class_id=:classId and "
				+ "lesson_id=:lessonId and question_no=:questionNo) group by a.id";
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(answer);
		List<Map<String,Object>> listMap = namedJdbcTemplate.queryForList(sql, sqlParameterSource);
		List<Answer> answerList = new ArrayList<Answer>();
		for(Map<String,Object> map : listMap){
			Answer answerObj = new Answer();
			try {
				BeanUtils.populate(answerObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			answerList.add(answerObj);
		}
		//错误位置标红 算法
		for(int i=0;i<answerList.size();i++){
			int[][] regions = CommonUtil.megerRegions(CommonUtil.getRegionFromStrWithConstraint(answerList.get(i).getErrorFlag(),answerList.get(i).getStuAnswer().length()-1));
			answerList.get(i).setErrorFlagShow(regions);
			answerList.get(i).setTechAnswer(answerList.get(i).getTechAnswer());
			answerList.get(i).setStuAnswer(answerList.get(i).getStuAnswer());
		}
		List<Map<String,Object>> listUndo = namedJdbcTemplate.queryForList(sql_undo, sqlParameterSource);
		String stuNameUndo = "";
		for(Map<String,Object> map : listUndo){
			stuNameUndo += map.get("stuName")+" ";
		}
		Answer answerUndo = new Answer();
		answerUndo.setStuName(stuNameUndo);
		answerList.add(answerUndo);
		return answerList;
	}

	public int checkAnswer(Answer answer){
		String sql = "select count(*) from tb_answer_flow where lesson_id=:lessonId and "
				+ "question_no=:questionNo and class_id=:classId and stu_id=:stuId ";
		SqlParameterSource answerParam = new BeanPropertySqlParameterSource(answer);
		int rs = namedJdbcTemplate.queryForObject(sql, answerParam, Integer.class);
		return rs;
	}
	
	@Override
	public int insertAnswer(Answer answer) {
		// TODO 自动生成的方法存根
		String sql = "insert into tb_answer_flow(lesson_id,question_no,stu_id,stu_answer,"
				+ "create_date,modify_date,answer_type,class_id)"
				+ "values"
				+ "(:lessonId,:questionNo,:stuId,:stuAnswer,now(),now(),:answerType,:classId) ";
		SqlParameterSource answerParam = new BeanPropertySqlParameterSource(answer);
		int rs = namedJdbcTemplate.update(sql, answerParam);
		return rs;
	}

	@Override
	public int updateAnswer(Answer answer) {
		// TODO 自动生成的方法存根
		String sql = "update tb_answer_flow set stu_answer=:stuAnswer,answer_result=:answerResult,"
				+ "error_flag=:errorFlag,modify_date=now(), "
				+ "answer_type=:answerType where stu_id=:stuId and "
				+ "lesson_id=:lessonId and question_no=:questionNo and class_id=:classId ";
		SqlParameterSource answerParam = new BeanPropertySqlParameterSource(answer);
		int rs = namedJdbcTemplate.update(sql, answerParam);
		return rs;
	}

	@Override
	public int insertAnswerSnap(Answer answer) {
		// TODO 自动生成的方法存根
		int rs = 0;
		try{
			String sql = "insert into tb_answer_snap select a.*,:snapName from tb_answer a "
					+ "where lesson_id=:lessonId and class_id=:classId ";
			MapSqlParameterSource map = new MapSqlParameterSource();
			map.addValue("lessonId", answer.getLessonId());
			map.addValue("classId", answer.getClassId());
			map.addValue("snapName", answer.getSnapName());
			rs = namedJdbcTemplate.update(sql, map);
		}catch(Exception e){
			throw e;
		}
		return rs;
	}

	public PageUI querySnapPage(Pagination pagination, UserInfo userInfo,Answer answer) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		StringBuffer dateSql = new StringBuffer();
		
		if(!"".equals(answer.getLessonId())&&null!=answer.getLessonId()){
			whereSql.append(" and lesson_id = :lessonId ");
		}
		
		if(!"".equals(answer.getClassId())&&null!=answer.getClassId()){
			whereSql.append(" and class_id = :classId ");
		}else{
			whereSql.append(" and class_id in(select class_id from "
					+ "tb_user_bind where user_id="+userInfo.getUser().getUserId()+")");
		}
		
		if(!"".equals(answer.getCreateDate())&&null!=answer.getCreateDate()){
			dateSql.append(" and date_format(create_date,'%Y%m%d')=:createDate");
		}
		
			
		String sql = "select class_id classId,class_name className,lesson_id lessonId, "
				+ "lesson_name lessonName,snap_name snapName from "
				+ "(select distinct lesson_id,class_id,snap_name "
				+ "from tb_answer_snap where 1=1 "+dateSql+" group by snap_name order by id desc)a, "
				+ " tb_class b,tb_lesson c where a.class_id=b.id and a.lesson_id=c.id"+whereSql;
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(answer);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), namedJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> listMap = page.getResultList();
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(listMap);
		return rs;
	}
	
	
	@Override
	public PageUI querySnapDetail(Pagination pagination, Answer answer) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		
			
		if(!"".equals(answer.getQuestion())&&null!=answer.getQuestion()){
			whereSql.append(" and b.question like concat('%',:question,'%')");
		}
		
		if(!"".equals(answer.getStuName())&&null!=answer.getStuName()){
			whereSql.append(" and c.user_name like concat('%',:stuName,'%')");
		}
		
		if(!"".equals(answer.getSnapName())&&null!=answer.getSnapName()){
			whereSql.append(" and a.snap_name = :snapName ");
		}
		
		String sql = "select a.lesson_id lessonId,a.question_no questionNo,question,stu_answer stuAnswer,"
				+ "answer_result answerResult,error_flag errorFlag,tech_answer techAnswer, "
				+ "case when answer_result='0' then '错误' when answer_result='1' then '正确' else '--未知--' end as answerResultName,"
				+ "c.user_name stuName from tb_answer_snap a,tb_question b,tb_user c "
				+ "where a.stu_id=c.id and a.lesson_id=b.lesson_id and a.question_no=b.question_no "
				+ " "+whereSql+" order by a.question_no ";
		
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(answer);
		Page page = new Page(sql, pagination.getPage(), pagination.getRows(), namedJdbcTemplate, sqlParameterSource);
		List<Map<String,Object>> listMap = page.getResultList();
		List<Answer> answerList = new ArrayList<Answer>();
		for(Map<String,Object> map : listMap){
			Answer answerObj = new Answer();
			try {
				BeanUtils.populate(answerObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			answerList.add(answerObj);
		}
		//错误位置标红 算法
		for(int i=0;i<answerList.size();i++){
			int[][] regions = CommonUtil.megerRegions(CommonUtil.getRegionFromStrWithConstraint(answerList.get(i).getErrorFlag(),answerList.get(i).getStuAnswer().length()-1));
			answerList.get(i).setErrorFlagShow(regions);
			answerList.get(i).setTechAnswer(answerList.get(i).getTechAnswer().replaceAll("\n", "<br>"));
			answerList.get(i).setStuAnswer(answerList.get(i).getStuAnswer());
		}
		PageUI rs = new PageUI();
		rs.setTotal(page.getTotalRows());
		rs.setRows(answerList);
		return rs;
	}

	@Override
	public int deleteSnapDetail(Answer answer) {
		// TODO 自动生成的方法存根
		String[] snapNames = answer.getSnapNames();
		List<String> snapNamesList = Arrays.asList(snapNames);
		String sql = "delete from tb_answer_snap where snap_name in(:snapNames) ";
		MapSqlParameterSource mapParam = new MapSqlParameterSource();
		mapParam.addValue("snapNames", snapNamesList);
		int rs = namedJdbcTemplate.update(sql, mapParam);
		return rs;
	}
	
}