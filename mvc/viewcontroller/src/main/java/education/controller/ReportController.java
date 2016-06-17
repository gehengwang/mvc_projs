package education.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.UserInfo;
import tools.Pagination;
import tools.PageUI;
import education.model.Lesson;
import education.model.Question;
import education.service.ReportService;

@Controller
public class ReportController {

	@Resource
	ReportService reportService;
	
	@RequestMapping("questionCompleteInit")
	public String questionCompleteInit(){
		return "/report/question_complete";
	}
	
	@ResponseBody
	@RequestMapping("questionComplete")
	public PageUI questionComplete(HttpServletRequest request,Pagination pagination,Lesson lesson){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = reportService.questionComplete(pagination,lesson, userInfo);
		return pageUI;
	}
	
	@RequestMapping("answerCorrectInit")
	public String answerCorrectInit(){
		return "/report/answer_correct";
	}
	
	@ResponseBody
	@RequestMapping("answerCorrect")
	public PageUI answerCorrect(HttpServletRequest request,Pagination pagination,Lesson lesson){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = reportService.answerCorrect(pagination,lesson, userInfo);
		return pageUI;
	}
	
	@RequestMapping("studentRankInit")
	public String studentRankInit(){
		return "/report/student_rank";
	}
	
	@ResponseBody
	@RequestMapping("studentRank")
	public PageUI studentRank(HttpServletRequest request,Pagination pagination,Lesson lesson){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = reportService.studentRank(pagination,lesson, userInfo);
		return pageUI;
	}
	
	@RequestMapping("questionCorrectInit")
	public String questionCorrectInit(){
		return "/report/question_correct";
	}
	
	@ResponseBody
	@RequestMapping("questionCorrect")
	public PageUI questionCorrect(HttpServletRequest request,Pagination pagination,Lesson lesson,Question question){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = reportService.questionCorrect(pagination,lesson, question, userInfo);
		return pageUI;
	}
	
	@ResponseBody
	@RequestMapping("answersByQuestionId")
	public PageUI answersByQuestionId(Pagination pagination,Long lessonId, Long questionNo){
		PageUI pageUI = reportService.answersByQuestionId(pagination,lessonId, questionNo);
		return pageUI;
	}
	
	@RequestMapping("lessonCorrectInit")
	public String lessonCorrectInit(){
		return "/report/lesson_correct";
	}
	
	@ResponseBody
	@RequestMapping("lessonCorrect")
	public PageUI lessonCorrect(HttpServletRequest request,Pagination pagination,Lesson lesson){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = reportService.lessonCorrect(pagination,lesson, userInfo);
		return pageUI;
	}
}
