package education.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import education.filterAndListener.AnswerListener;
import education.model.Answer;
import education.service.AnswerService;

/**
 * 答案控制
 * @author xy
 *
 */
@Controller
public class AnswerController {
	
	static int INITAI = 10000;
	static AtomicInteger AI = new AtomicInteger(INITAI);
	static void resetAI(){
		AI.set(INITAI);
	}
	
	@Resource
	AnswerService answerService;
	
	@RequestMapping("lessonQueryQuestion")
	public String lessonQueryQuestion(){
		return "/student/lesson_query_question";
	}
	
	@RequestMapping("lessonQueryAnswer")
	public String lessonQueryAnswer(){
		return "/student/lesson_query_answer";
	}
	
	@RequestMapping("answerInit")
	public String answerInit(){
		return "/student/question_answer";
	}
	
	@RequestMapping("querySnapInit")
	public String querySnapInit(){
		return "/teacher/query_snap";
	}
	
	@RequestMapping("querySnapDetailInit")
	public String querySnapDetailInit(String snapName){
		return "/teacher/snap_detail";
	}
	
	@ResponseBody
	@RequestMapping("saveStuAnswer")
	public AjaxMsg saveStuAnswer(HttpServletRequest request,Answer answer){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		/*answer.setId(answer.getAnswerId());
		answer.setStuId(userInfo.getUser().getUserId());
		List<Map<String,Object>> answerListMap = AnswerListener.answerListMap;
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String dateStr = dateFormater.format(date);
		
		if(!"".equals(answer.getStuAnswer())&&null!=answer.getStuAnswer()){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("serialNum", dateStr+AI.getAndIncrement());
			map.put("lessonId", answer.getLessonId());
			map.put("classId", answer.getClassId());
			map.put("questionNo", answer.getQuestionNo());
			map.put("stuId", answer.getStuId());
			map.put("stuAnswer", answer.getStuAnswer());
			answerListMap.add(map);
		}*/
		answer.setAnswerType(0L);
		answer.setStuId(userInfo.getUser().getUserId());
		AjaxMsg rs = answerService.saveAnswer(answer);
		return rs;
	}
	
	@ResponseBody
	@RequestMapping("getStuAnswer")
	public List<?> getStuAnswer(){
		resetAI();
		return AnswerListener.answerListMap;
	}
	
	@ResponseBody
	@RequestMapping("sendStuAnswer")
	public String sendStuAnswer(String serialNum){
		AnswerListener.serialNum = serialNum;
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("answerPage")
	public PageUI answerPage(HttpServletRequest request,Pagination pagination,Answer answer){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = answerService.answerPage(pagination, answer, userInfo);
		return pageUI;
	}
	
	@RequestMapping("answerList")
	public String answerList(HttpServletRequest request,Answer answer){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<Answer> listAnswer = answerService.answerList(answer, userInfo);
		request.setAttribute("listAnswer", listAnswer);
		return "/teacher/answer_view";
	}
	
	public static void main(String[] args) {
		//AtomicInteger ai = new AtomicInteger(10000);
	}
	
	@ResponseBody
	@RequestMapping("insertAnserSnap")
	public AjaxMsg insertAnserSnap(Answer answer){
		AjaxMsg ajaxMsg = answerService.insertAnswerSnap(answer);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("querySnapPage")
	public PageUI querySnapPage(HttpServletRequest request,Pagination pagination,Answer answer){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = answerService.querySnapPage(pagination,userInfo,answer);
		return pageUI;
	}
	
	@ResponseBody
	@RequestMapping("querySnapDetail")
	public PageUI querySnapDetail(Pagination pagination,Answer answer){
		PageUI pageUI = answerService.querySnapDetail(pagination, answer);
		return pageUI;
	}
	
	@ResponseBody
	@RequestMapping("deleteSnapDetail")
	public AjaxMsg deleteSnapDetail(Answer answer){
		AjaxMsg ajaxMsg = answerService.deleteSnapDetail(answer);
		return ajaxMsg;
	}
}
