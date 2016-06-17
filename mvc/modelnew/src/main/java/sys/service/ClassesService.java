package sys.service;

import java.util.List;

import sys.model.Classes;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;

public interface ClassesService {

	/**
	 * 班级列表
	 * @param userInfo
	 * @return
	 */
	List<?> queryClassesList(UserInfo userInfo,Long deptId);
	
	/**
	 * 给班级绑定课件
	 * @param lesson
	 * @return
	 */
	AjaxMsg saveClassLesson(Classes classes);
	
	/**
	 * 更改班级课件状态
	 * @param lesson
	 * @return
	 */
	AjaxMsg updateClassLessonStatus(Classes classes);
	
	/**
	 * 班级查询
	 * @param pagination
	 * @param classes
	 * @return
	 */
	PageUI queryClassPage(Pagination pagination,UserInfo userInfo,Classes classes);
	
	/**
	 * 班级课程列表
	 * @param lesson
	 * @return
	 */
	PageUI classLessonPage(Pagination pagination,Classes classes,UserInfo userInfo);
	
	/**
	 * 保存班级
	 * @param classes
	 * @return
	 */
	AjaxMsg saveClass(Classes classes);
	
	/**
	 * 班级对象
	 * @param classes
	 * @return
	 */
	AjaxMsg queryClassObject(Classes classes);
}
