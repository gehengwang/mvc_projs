package education.service;

import java.util.List;

import education.model.Question;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
public interface QuestionService {

	/**
	 * 题目列表查询：查询全部题目或根据用户id查询相应的错题
	 * @param lesson
	 * @param userInfo
	 * @return
	 */
	List<Question> questionList(Question question,UserInfo userInfo);
	
	/**
	 * 题目保存
	 * @param question
	 * @return
	 */
	AjaxMsg saveQuestion(Question question);
	
	/**
	 * 根据课时号和题号删除：提供批量删除','分隔
	 * @return
	 */
	AjaxMsg deleteQuestions(Long lessonId,Long questionNo);
	
	/**
	 * 题目列表
	 * @param pagination
	 * @param question
	 * @return
	 */
	PageUI questioPage(Question question);
	
}