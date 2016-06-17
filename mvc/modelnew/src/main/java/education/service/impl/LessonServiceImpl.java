package education.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import tools.excel.ExcelFun;
import education.dao.LessonDao;
import education.dao.QuestionDao;
import sys.service.ServiceException;
import education.model.Lesson;
import education.model.Question;
import education.service.LessonService;

@Service
public class LessonServiceImpl implements LessonService{

	@Resource
	LessonDao lessonDao;
	@Resource
	QuestionDao questionDao;
	
	@Override
	@Transactional(rollbackFor={RuntimeException.class, Exception.class})
	public AjaxMsg importLesson(Workbook workbook, UserInfo userInfo,
			Lesson lesson) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = lessonDao.checkLessonName(lesson);
		if(rs==1){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("璇ヨ浠跺悕绉板凡瀛樺湪锛岃閲嶆柊杈撳叆");
		}else{
			int questionNoNull = 0;
			int questionNull = 0;
			String errorMsg = "";
			try{
				Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
				int rownum = 8;
				int column = sheet.getRow(3).getLastCellNum(); //棰樼洰涓虹┖鍒欒〃绀烘棤鏁堟暟鎹�
				String[][] strArray = new String[column][rownum];
				for(int i=0;i<rownum;i++){
					Row row = sheet.getRow(i);
					if(row==null) break;
					for(int j=0;j<column;j++){
						strArray[j][i] =  ExcelFun.getStrValue(row.getCell(j));
					}
				}
				lesson.setTechIdOwn(userInfo.getUser().getUserId());
				lesson.setDeptId(userInfo.getRoles().get(0).getDeptId());
				lesson.setLessonStatus("inuse");
				Map<String,Object> map = lessonDao.addLesson(lesson);
				int lessonId =(int) map.get("lessonId");
				//鑰佸笀涓婁紶璇句欢锛岄粯璁ら厤鑷繁璇句欢
				Long[] techIds = {lesson.getTechIdOwn()};
				Long[] lessonIds = {Long.parseLong(lessonId+"")};
				lesson.setTechIds(techIds);lesson.setLessonIds(lessonIds);
				lessonDao.addTechLesson(lesson);
				//瀵煎叆璇句欢棰樼洰
				for(int n=1;n<strArray.length;n++){
					if(strArray[n][0]==null||strArray[n][0]==""){
						questionNoNull = n;
						errorMsg = "绗�"+questionNoNull+"棰�,棰樺彿涓虹┖,璇锋鏌�!";
						throw new ServiceException();
					}
					if(strArray[n][3]==null||strArray[n][3]==""){
						questionNull = n;
						errorMsg = "绗�"+questionNull+"棰�,棰樼洰涓虹┖,璇锋鏌�!";
						throw new ServiceException();
					}
					Question question = new Question();
					question.setLessonId(Long.parseLong(lessonId+""));
					question.setQuestionNo(Long.parseLong(strArray[n][0]));
					question.setQuestionType(strArray[n][1]);
					question.setKnowledge(strArray[n][2]);
					question.setQuestion(strArray[n][3]);
					question.setTechAnswer(strArray[n][4]);
					question.setEquivalence(strArray[n][5]);
					question.setTypicalFault(strArray[n][6]);
					question.setMissKeyWord(strArray[n][7]);
					questionDao.addQuesions(question);
				
				}
				Question questionObj = new Question();
				questionObj.setLessonId(Long.parseLong(lessonId+""));
				List<Question> quesList = questionDao.questionList(questionObj, userInfo);
				if(quesList.size()==strArray.length-1){
					ajaxMsg.setSuccess(true);
					ajaxMsg.setMsg("鎴愬姛瀵煎叆"+quesList.size()+"鏉￠鐩�");
				}else{
					ajaxMsg.setSuccess(false);
					ajaxMsg.setMsg("瀵煎叆澶辫触");
				}
			}catch (Exception e) {
				// TODO: handle exception
				throw new ServiceException(errorMsg);
			}
		}
		return ajaxMsg;
	}

	@Override
	public PageUI queryLessonPage(Pagination pagination,Lesson lesson, UserInfo userInfo) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		return lessonDao.queryLessonPage(pagination,lesson, userInfo);
	}
	
	@Override
	public AjaxMsg updateLesson(Lesson lesson) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = lessonDao.updateLesson(lesson);
		if(rs!=0){
			ajaxMsg.setSuccess(true);
			ajaxMsg.setMsg("鏇存柊鎴愬姛锛�");
		}else{
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("鏇存柊澶辫触锛�");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg deleteLesson(Long lessonId) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int lessonRs = lessonDao.deleteLesson(lessonId);
		int questionRs = questionDao.deleteQuestions(lessonId, 0l);
		if(lessonRs>0&&questionRs>0){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("鍒犻櫎鎴愬姛");
		}else{
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("鍒犻櫎澶辫触");
		}
		return ajaxMsg;
	}

	@Override
	public List<?> queryLessonList(UserInfo userInfo,String lessonName) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		return lessonDao.queryLessonList(userInfo,lessonName);
	}

	@Override
	public AjaxMsg updateLessonStatus(Lesson lesson) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = lessonDao.updateLessonStatus(lesson);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("鏇存柊澶辫触");
		}else{
			ajaxMsg.setMsg("鏇存柊鎴愬姛");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg saveTechLesson(Lesson lesson) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		lessonDao.deleteTechLesson(lesson.getDeptId(), lesson.getTechIds());
		int rs = lessonDao.addTechLesson(lesson);
		if(rs==lesson.getTechIds().length*lesson.getLessonIds().length){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("閰嶇疆鎴愬姛!");
		}else{
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("閰嶇疆澶辫触!");
		}
		return ajaxMsg;
	}
}
