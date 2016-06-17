package education.service;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;

import java.util.List;

import education.model.Answer;

public interface AnswerService {

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
	 * 学生答题入库
	 * @param question
	 * @param userInfo
	 * @return
	 */
	AjaxMsg saveAnswer(Answer answer);
	
	/**
	 * 班级学生答题快照
	 * @param lessonId
	 * @param classId
	 * @return
	 */
	AjaxMsg insertAnswerSnap(Answer answer);
	
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
	AjaxMsg deleteSnapDetail(Answer answer);
}
