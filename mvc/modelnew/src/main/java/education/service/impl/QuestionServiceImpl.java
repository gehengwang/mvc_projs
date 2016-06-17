package education.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import education.dao.QuestionDao;
import education.model.Question;
import education.service.QuestionService;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;

@Service
public class QuestionServiceImpl implements QuestionService{

	@Resource
	QuestionDao questionDao;
	
	@Override
	public List<Question> questionList(Question question,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		List<Question> questionList = questionDao.questionList(question,userInfo);
		return questionList;
	}

	@Override
	public AjaxMsg saveQuestion(Question question) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = questionDao.checkQuestion(question);
		if(rs>0){
			int updateRs = questionDao.updateQuestions(question);
			if(updateRs>0){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("更新成功");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("更新失败");
			}
		}else{
			int updateRs = questionDao.addQuesions(question);
			if(updateRs>0){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("您增加的题目已经保存成功！<br>是否继续增加题目?");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("新增失败");
			}
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg deleteQuestions(Long lessonId,Long questionNo) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs =  questionDao.deleteQuestions(lessonId, questionNo);
		if(rs>0){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("删除成功");
		}
		return ajaxMsg;
	}

	@Override
	public PageUI questioPage( Question question) {
		// TODO Auto-generated method stub
		return questionDao.questioPage( question);
	}
}