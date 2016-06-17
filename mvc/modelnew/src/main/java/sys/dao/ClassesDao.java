package sys.dao;

import java.util.List;

import sys.model.Classes;
import sys.model.UserInfo;
import tools.PageUI;
import tools.Pagination;

public interface ClassesDao {

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
	int addClassLesson(Classes classes);
	
	/**
	 * 更改班级课件状态
	 * @param lesson
	 * @return
	 */
	int updateClassLessonStatus(Classes classes);
	
	
	/**
	 * 班级信息查询
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
	 * 班级增加
	 * @param classes
	 * @return
	 */
	int insertClass(Classes classes);
	
	/**
	 * 班级修改
	 * @param classes
	 * @return
	 */
	int updateClass(Classes classes);
	
	/**
	 * 解除班级课件绑定
	 * @param classes
	 * @return
	 */
	int deleteClassLesson(Classes classes);
	
	/**
	 * 返回班级对象
	 * @param classes
	 * @return
	 */
	Classes queryClassObject(Classes classes);
	
	/**
	 * 检查班级课件绑定
	 * @param classes
	 * @return
	 */
	int checkClassLesson(Classes classes);

}
