package education.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import education.model.Question;
import education.service.QuestionService;

/**
 * 题目控制
 * @author xy
 *
 */
@Controller
public class QuestionController {

	@Resource
	QuestionService questionService;
	
	
	@RequestMapping("questionInit")
	public String questionInit(Long lessonId){
		return "/student/lesson_question";
	}
	
	@RequestMapping("questionControl")
	public String questionControl(){
		return "/teacher/question_control";
	}
	
	@RequestMapping("questionListInit")
	public String questionList(Long lessonId){
		return "/teacher/question_list";
	}
	
	@ResponseBody
	@RequestMapping("questionList")
	public List<Question> questionList(HttpServletRequest request,Question question){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<Question> questionList = questionService.questionList(question,userInfo);
		return questionList;
	}
	
	@ResponseBody
	@RequestMapping("questionPage")
	public PageUI questionPage(Question question){
		PageUI rs = questionService.questioPage(question);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("saveQuestion")
	public AjaxMsg saveQuestion(Question question){
		AjaxMsg rs = questionService.saveQuestion(question);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("deleteQuestion")
	public AjaxMsg deleteQuestion(Long lessonId,Long questionNo){
		AjaxMsg rs = questionService.deleteQuestions(lessonId, questionNo);
		return rs;
	}
}