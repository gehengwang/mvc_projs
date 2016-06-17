package education.dao;

import java.util.List;

import sys.model.UserInfo;
import tools.PageUI;
import education.model.Question;

public interface QuestionDao {

	/**
	 * 题目列表查询：查询课件对应的题目
	 * @param question
	 * @return
	 */
	List<Question> questionList(Question question,UserInfo userInfo);
	
	/**
	 * 题目新增
	 * @param question
	 * @return
	 */
	int addQuesions(Question question);
	
	/**
	 * 题目修改
	 * @param question
	 * @return
	 */
	int updateQuestions(Question question);
	
	/**
	 * 根据课时号和题号删除：提供批量删除','分隔
	 * @return
	 */
	int deleteQuestions(Long lessonId,Long questionNo);
	
	/**
	 * 题目列表
	 * @param pagination
	 * @param question
	 * @return
	 */
	PageUI questioPage(Question question);
	
	/**
	 * 检查题目的唯一性
	 * @param question
	 * @return
	 */
	int checkQuestion(Question question);
}
