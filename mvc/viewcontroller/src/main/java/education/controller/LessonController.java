package education.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import tools.excel.ExcelFun;
import education.filterAndListener.ServiceException;
import education.model.Lesson;
import education.service.LessonService;

/**
 * 课时控制
 * @author xy
 *
 */
@Controller
public class LessonController {

	@Resource
	LessonService lessonService;
	
	@RequestMapping("lessonTechInit")
	public String lessonTechInit(){
		return "/teacher/lesson_tech";
	}
	
	@ResponseBody
	@RequestMapping("queryLessonPage")
	public PageUI queryLessonPage(HttpServletRequest request,Pagination pagination,Lesson lesson){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = lessonService.queryLessonPage(pagination,lesson,userInfo);
		return pageUI;
	}
	
	@RequestMapping("lessonClassInit")
	public String lessonClassInit(){
		return "/teacher/lesson_class";
	}
	
	@ResponseBody
	@RequestMapping("updateLesson")
	public AjaxMsg updateLesson(Lesson lesson){
		AjaxMsg ajaxMsg = lessonService.updateLesson(lesson);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("queryLessonList")
	public List<?> queryLessonList(HttpServletRequest request,String q){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		/** String q easyUI Combo等动态查询参数*/
		List<?> listMap = lessonService.queryLessonList(userInfo, q);
		return listMap;
	}
	
	@ResponseBody
	@RequestMapping("updateLessonStatus")
	public AjaxMsg updateLessonStatus(Lesson lesson){
		AjaxMsg ajaxMsg = lessonService.updateLessonStatus(lesson);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("/importLesson")
	public AjaxMsg importLesson(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "file") MultipartFile file,Lesson lesson) throws Exception{
		Workbook workbook = ExcelFun.getWorkBook(file.getInputStream(), file.getOriginalFilename());
		UserInfo userInfo  = (UserInfo)request.getSession().getAttribute("USER_INFO");
		AjaxMsg rs = new AjaxMsg();
		try{
			rs = lessonService.importLesson(workbook, userInfo, lesson);
		}catch(ServiceException e){
			rs.setSuccess(false);rs.setMsg(e.getMessage());
		}
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("deleteLesson")
	public AjaxMsg deleteLesson(Long lessonId){
		AjaxMsg rs = lessonService.deleteLesson(lessonId);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("saveTechLesson")
	public AjaxMsg saveTechLesson(HttpServletRequest request,Lesson lesson){
		UserInfo userInfo  = (UserInfo)request.getSession().getAttribute("USER_INFO");
		lesson.setDeptId(userInfo.getRoles().get(0).getDeptId());
		AjaxMsg rs = lessonService.saveTechLesson(lesson);
		return rs;
	}
}
