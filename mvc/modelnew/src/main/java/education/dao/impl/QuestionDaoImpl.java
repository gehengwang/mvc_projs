package education.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import sys.model.UserInfo;
import tools.PageUI;
import education.dao.QuestionDao;
import education.model.Question;

@Repository
public class QuestionDaoImpl implements QuestionDao{

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Question> questionList(Question question,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		String sql = "select a.id questionId,a.lesson_id lessonId,a.question_no questionNo, "
				+ "ifnull(question_type,'') questionType,ifnull(knowledge,'') knowledge,question, "
				+ "ifnull(equivalence,'') equivalence,ifnull(typical_fault,'') typicalFault, "
				+ "ifnull(miss_key_word,'') missKeyWord,b.id answerId,b.stu_answer stuAnswer from "
				+ "(select * from tb_question where lesson_id=:lessonId) a "
				+ "left join "
				+ "(select * from tb_answer where stu_id=:stuId and class_id=:classId) b "
				+ "on a.lesson_id=b.lesson_id and a.question_no=b.question_no ";
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("lessonId", question.getLessonId());
		mapSqlParameterSource.addValue("classId", question.getClassId());
		mapSqlParameterSource.addValue("stuId", userInfo.getUser().getUserId());
		List<Map<String,Object>> listMap = namedParameterJdbcTemplate.queryForList(sql, mapSqlParameterSource);
		List<Question> questionList = new ArrayList<Question>();
		for(Map<String,Object> map : listMap){
			Question questionObj = new Question();
			try {
				BeanUtils.populate(questionObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			questionList.add(questionObj);
		}
		return questionList;
	}

	@Override
	public int addQuesions(Question question) {
		// TODO 自动生成的方法存根
		//replace into 覆盖旧记录
		String sql = "replace into tb_question(lesson_id,question_no,question_type,knowledge,"
				+ "question,tech_answer,equivalence,typical_fault,miss_key_word)"
				+ "values"
				+ "(:lessonId,:questionNo,:questionType,:knowledge,:question,:techAnswer,"
				+ ":equivalence,:typicalFault,:missKeyWord)";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(question);
		int rs = namedParameterJdbcTemplate.update(sql, paramSource);
		return rs;
	}

	@Override
	public int updateQuestions(Question question) {
		// TODO 自动生成的方法存根
		String sql = "update tb_question set question_type=:questionType,knowledge=:knowledge,"
				+ "question=:question,tech_answer=:techAnswer,equivalence=:equivalence, "
				+ "typical_fault=:typicalFault,miss_key_word=:missKeyWord "
				+ "where lesson_id=:lessonId and question_no=:questionNo ";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(question);
		int rs = namedParameterJdbcTemplate.update(sql, paramSource);
		return rs;
	}

	@Override
	public PageUI questioPage(Question question) {
		// TODO Auto-generated method stub
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		String sql = "select a.id questionId,a.lesson_id lessonId,a.question_no questionNo, "
				+ "ifnull(question_type,'') questionType,ifnull(knowledge,'') knowledge, "
				+ "question,tech_answer techAnswer, ifnull(equivalence,'') equivalence, "
				+ "ifnull(typical_fault,'') typicalFault, ifnull(miss_key_word,'') missKeyWord,stuName from "
				+ "(select * from tb_question where lesson_id=:lessonId)a "
				+ "left join "
				//mysql行转列group_concat
				+ "(select lesson_id,question_no,class_id,group_concat(user_name) stuName from "
				+ "tb_answer a,tb_user b where a.stu_id=b.id group by lesson_id,question_no,class_id)b "
				+ "on a.question_no=b.question_no and a.lesson_id=b.lesson_id and "
				+ "b.class_id=:classId ";
		
		mapSqlParameterSource.addValue("lessonId", question.getLessonId());
		mapSqlParameterSource.addValue("classId", question.getClassId());
		
		List<Map<String,Object>> listMap = namedParameterJdbcTemplate.queryForList(sql, mapSqlParameterSource);
		List<Question> questionList = new ArrayList<Question>();
		for(Map<String,Object> map : listMap){
			Question questionObj = new Question();
			try {
				BeanUtils.populate(questionObj, map);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			questionList.add(questionObj);
		}
		for(int i=0;i<questionList.size();i++){
			questionList.get(i).setTechAnswer(questionList.get(i).getTechAnswer().replace("\n", "<br>"));
			questionList.get(i).setEquivalence(questionList.get(i).getEquivalence().replace("\n", "<br>"));
		}
		PageUI rs = new PageUI();
		rs.setTotal(questionList.size());
		rs.setRows(questionList);
		return rs;
	}

	@Override
	public int deleteQuestions(Long lessonId,Long questionNo) {
		// TODO 自动生成的方法存根
		StringBuffer whereSql = new StringBuffer();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		if(0L!=questionNo){
			whereSql.append(" and question_no=:questionNo");
			mapSqlParameterSource.addValue("questionNo", questionNo);
		}
		String sql = "delete from tb_question where lesson_id=:lessonId "+whereSql+" ";
		mapSqlParameterSource.addValue("lessonId", lessonId);
		
		int rs = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
		return rs;
	}

	@Override
	public int checkQuestion(Question question) {
		// TODO 自动生成的方法存根
		String sql = "select count(*) from tb_question where lesson_id=:lessonId and question_no=:questionNo ";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(question);
		int rs = namedParameterJdbcTemplate.queryForObject(sql, paramSource, Integer.class);
		return rs;
	}

}
