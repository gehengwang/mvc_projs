package education.dao;

import java.util.List;
import java.util.Map;

import education.model.Lesson;
import sys.model.UserInfo;
import tools.PageUI;
import tools.Pagination;

public interface LessonDao {

	/**
	 * 老师查询课程列表
	 * @param lesson
	 * @return
	 */
	PageUI queryLessonPage(Pagination pagination,Lesson lesson,UserInfo userInfo);
	
	/**
	 * 课件添加
	 * @param lesson
	 * @return
	 */
	Map<String,Object> addLesson(Lesson lesson);
	
	/**
	 * 课件修改
	 * @param lesson
	 * @return
	 */
	int updateLesson(Lesson lesson);
	
	/**
	 * 删除课件：提供批量删除','号分隔
	 * @param lessonIds
	 * @return
	 */
	int deleteLesson(Long lessonId);
	
	/**
	 * 机构的所有课程
	 * @param techId
	 * @return
	 */
	List<?> queryLessonList(UserInfo userInfo,String lessonName);
	
	/**
	 * 更新课件状态
	 * @param lesson
	 * @return
	 */
	int updateLessonStatus(Lesson lesson);
	
	/**
	 * 课件名称唯一性
	 * @param lesson
	 * @return
	 */
	int checkLessonName(Lesson lesson);
	
	/**
	 * 老师配课
	 * @param lesson
	 * @return
	 */
	int addTechLesson(Lesson lesson);
	
	/**
	 * 删除老师配课
	 * @param techIds
	 * @param lessonIds
	 * @return
	 */
	int deleteTechLesson(Long deptId,Long[] techIds);
}
