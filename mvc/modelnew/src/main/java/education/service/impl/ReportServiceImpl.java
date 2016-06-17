package education.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sys.model.UserInfo;
import tools.Pagination;
import tools.PageUI;
import education.dao.ReportDao;
import education.model.Lesson;
import education.model.Question;
import education.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{

	@Resource
	ReportDao reportDao;
	
	@Override
	public PageUI questionComplete(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return reportDao.questionComplete(pagination,lesson, userInfo);
	}

	@Override
	public PageUI answerCorrect(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return reportDao.answerCorrect(pagination,lesson, userInfo);
	}

	@Override
	public PageUI studentRank(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return reportDao.studentRank(pagination,lesson, userInfo);
	}

	@Override
	public PageUI questionCorrect(Pagination pagination,Lesson lesson, Question question,
			UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return reportDao.questionCorrect(pagination,lesson, question, userInfo);
	}

	@Override
	public PageUI answersByQuestionId(Pagination pagination,Long lessonId, Long questionId) {
		// TODO 自动生成的方法存根
		return reportDao.answersByQuestionId(pagination,lessonId, questionId);
	}

	@Override
	public PageUI lessonCorrect(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return reportDao.lessonCorrect(pagination,lesson, userInfo);
	}
}
