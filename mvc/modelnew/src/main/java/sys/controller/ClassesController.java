package sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.Classes;
import sys.model.UserInfo;
import sys.service.ClassesService;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;

@Controller
public class ClassesController {

	@Resource
	private ClassesService classesService;
	
	@RequestMapping("queryClassInit")
	public String queryClassInit(){
		return "/info/class_info";
	}
	
	@ResponseBody
	@RequestMapping("saveClassLesson")
	public AjaxMsg saveClassLesson(HttpServletRequest request,Classes classes){
		UserInfo userInfo  = (UserInfo)request.getSession().getAttribute("USER_INFO");
		classes.setDeptId(userInfo.getRoles().get(0).getDeptId());
		classes.setTechIdUse(userInfo.getUser().getUserId());
		classes.setQuestionStatus(0L);
		classes.setAnswerStatus(0L);
		AjaxMsg rs = classesService.saveClassLesson(classes);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("updateClassLessonStatus")
	public AjaxMsg updateClassLessonStatus(Classes classes){
		AjaxMsg rs = classesService.updateClassLessonStatus(classes);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("queryClassPage")
	public PageUI queryClassPage(HttpServletRequest request,Pagination pagination,Classes classes){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI rs = classesService.queryClassPage(pagination,userInfo,classes);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("classLessonPage")
	public PageUI classLessonPage(HttpServletRequest request,Pagination pagination,Classes classes){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		classes.setDeptId(Long.parseLong(userInfo.getRoles().get(0).getDeptId()+""));
		PageUI pageUI = classesService.classLessonPage(pagination, classes,userInfo);
		return pageUI;
	}
	
	
	@ResponseBody
	@RequestMapping("queryClassesList")
	public List<?> queryClassesList(HttpServletRequest request,Long deptId){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<?> listMap = classesService.queryClassesList(userInfo,deptId);
		return listMap;
	}
	

	@ResponseBody
	@RequestMapping("saveClass")
	public AjaxMsg saveClass(HttpServletRequest request,Classes classes) {
		// TODO 自动生成的方法存根
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		classes.setDeptId(userInfo.getRoles().get(0).getDeptId());
		AjaxMsg ajaxMsg = classesService.saveClass(classes);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("queryClassObject")
	public AjaxMsg queryClassObject(Classes classes){
		AjaxMsg ajaxMsg = classesService.queryClassObject(classes);
		return ajaxMsg;
	}
}
