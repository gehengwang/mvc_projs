package education.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import education.dao.AnswerDao;
import education.model.Answer;
import education.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService{

	@Resource
	AnswerDao answerDao;
	
	@Override
	public PageUI answerPage(Pagination pagination,Answer answer,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return answerDao.answerPage(pagination, answer, userInfo);
	}

	@Override
	public AjaxMsg saveAnswer(Answer answer) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int checkInt = answerDao.checkAnswer(answer);
		int rs = 0;
		if(0 == checkInt){
			rs = answerDao.insertAnswer(answer);
		}else{
			rs = answerDao.updateAnswer(answer);
		}
		if(rs!=0){
			ajaxMsg.setSuccess(true);
			ajaxMsg.setMsg("答题成功");
		}
		return ajaxMsg;
	}

	@Override
	public List<Answer> answerList(Answer answer, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return answerDao.answerList(answer, userInfo);
	}

	@Override
	public AjaxMsg insertAnswerSnap(Answer answer) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = 0;
		try{
			rs =  answerDao.insertAnswerSnap(answer);
			if(rs>0){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("保存成功"+rs+"条记录！");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("没有答题,无需保存~");
			}
		}catch(Exception e){
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("该快照已保存,不能重复保存！");
		}
		return ajaxMsg;
	}

	@Override
	public PageUI querySnapPage(Pagination pagination, UserInfo userInfo, Answer answer) {
		// TODO 自动生成的方法存根
		return answerDao.querySnapPage(pagination, userInfo,answer);
	}

	@Override
	public PageUI querySnapDetail(Pagination pagination, Answer answer) {
		// TODO 自动生成的方法存根
		return answerDao.querySnapDetail(pagination, answer);
	}

	@Override
	public AjaxMsg deleteSnapDetail(Answer answer) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = answerDao.deleteSnapDetail(answer);
		if(rs>0){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("删除成功");
		}else{
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("删除失败");
		}
		return ajaxMsg;
	}
}
