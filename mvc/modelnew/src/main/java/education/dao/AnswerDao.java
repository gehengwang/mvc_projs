package education.dao;

import sys.model.UserInfo;
import tools.PageUI;
import tools.Pagination;

import java.util.List;

import education.model.Answer;

public interface AnswerDao {

	/**
	 * 根据UserInfo判断：
	 * 学生登录：查询自己相关的答案
	 * 老师登录：查询所带学生答题信息
	 * 家长登录：查询所绑定学生答题信息
	 * 机构管理员登录：查询该机构下所有学生答题信息
	 * @param userInfo
	 * @return
	 */
	PageUI answerPage(Pagination pagination,Answer answer,UserInfo userInfo);
	
	List<Answer> answerList(Answer answer,UserInfo userInfo);
	
	/**
	 * 校验是否已经答过题目
	 * @param answer
	 * @return
	 */
	int checkAnswer(Answer answer);
	
	/**
	 * 插入答案
	 * @param answer
	 * @return
	 */
	int insertAnswer(Answer answer);
	
	/**
	 * 更新答案
	 * @param answer
	 * @return
	 */
	int updateAnswer(Answer answer);
	
	/**
	 * 班级学生答题快照
	 * @param lessonId
	 * @param classId
	 * @return
	 */
	int insertAnswerSnap(Answer answer);
	
	/**
	 * 快照查询
	 * @param pagination
	 * @param answer
	 * @return
	 */
	PageUI querySnapPage(Pagination pagination, UserInfo userInfo,Answer answer);
	
	/**
	 * 快照详情
	 * @param pagination
	 * @param answer
	 * @return
	 */
	PageUI querySnapDetail(Pagination pagination, Answer answer);
	
	/**
	 * 删除答案快照
	 * @param answer
	 * @return
	 */
	int deleteSnapDetail(Answer answer);
}
