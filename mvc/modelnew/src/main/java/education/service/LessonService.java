package education.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import education.model.Lesson;

public interface LessonService {

	
	/**
	 * 课件上传
	 * @param workbook
	 * @param userInfo
	 * @param lessonName
	 * @return
	 */
	AjaxMsg importLesson(Workbook workbook,UserInfo userInfo,Lesson lesson);
	
	/**
	 * 老师查询课程列表
	 * @param lesson
	 * @return
	 */
	PageUI queryLessonPage(Pagination pagination,Lesson lesson,UserInfo userInfo);
	
	
	/**
	 * 课件修改
	 * @param lesson
	 * @return
	 */
	AjaxMsg updateLesson(Lesson lesson);
	
	/**
	 * 删除课件：提供批量删除','号分隔
	 * @param lessonIds
	 * @return
	 */
	AjaxMsg deleteLesson(Long lessonId);
	
	/**
	 * 机构课程
	 * @param techId
	 * @return
	 */
	List<?> queryLessonList(UserInfo userInfo,String lessonName);
	
	/**
	 * 更新课件状态
	 * @param lesson
	 * @return
	 */
	AjaxMsg updateLessonStatus(Lesson lesson);
	
	/**
	 * 老师配课件
	 * @param lesson
	 * @return
	 */
	AjaxMsg saveTechLesson(Lesson lesson);
	
}
