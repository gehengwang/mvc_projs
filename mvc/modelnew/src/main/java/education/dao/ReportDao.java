package education.dao;

import sys.model.UserInfo;
import tools.Pagination;
import tools.PageUI;
import education.model.Lesson;
import education.model.Question;

public interface ReportDao {

	/**
	 * 学生:题目完成情况
	 * @param lesson
	 * @param userInfo
	 * @return
	 */
	PageUI questionComplete(Pagination pagination,Lesson lesson,UserInfo userInfo);
	
	/**
	 * 学生:答题正确率
	 * @param lesson
	 * @param userInfo
	 * @return
	 */
	PageUI answerCorrect(Pagination pagination,Lesson lesson,UserInfo userInfo);
	
	/**
	 * 学生：成绩排名
	 * @param lesson
	 * @param userInfo
	 * @return
	 */
	PageUI studentRank(Pagination pagination,Lesson lesson,UserInfo userInfo);

	/**
	 * 老师：题目正确率
	 * @param lesson
	 * @param question
	 * @param userInfo
	 * @return
	 */
	PageUI questionCorrect(Pagination pagination,Lesson lesson,Question question,UserInfo userInfo);
	
	/**
	 * 老师：题目正确率,每道题目的答案详情
	 * @param lessonId
	 * @param questionId
	 * @return
	 */
	PageUI answersByQuestionId(Pagination pagination,Long lessonId,Long questionId);
	
	
	/**
	 * 老师：课件正确率
	 * @param lesson
	 * @param userInfo
	 * @return
	 */
	PageUI lessonCorrect(Pagination pagination,Lesson lesson,UserInfo userInfo);
}
