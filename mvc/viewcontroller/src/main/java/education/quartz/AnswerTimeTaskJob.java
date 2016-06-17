package education.quartz;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import education.filterAndListener.AnswerListener;
import education.model.Answer;
import education.service.AnswerService;
import tools.AjaxMsg;

@Component
public class AnswerTimeTaskJob{

	@Resource
	AnswerService answerService;
	

	public void execute()  {
		// TODO Auto-generated method stub
		List<Map<String,Object>> answerListMap = AnswerListener.answerListMap;
		String serialNum = AnswerListener.serialNum;
		try{
			//python入库成功，返回序列号
			if(!"".equals(serialNum)&&null!=serialNum){
				System.out.println("====Python正常==="+serialNum);
				AnswerListener.answerListMap = new ArrayList<Map<String,Object>>();
				AnswerListener.serialNum = "";
			}else{//python连接超时，或入库失败，不返回序列号
				for(int i=0;i<answerListMap.size();i++){
					System.out.println("====Python异常==="+serialNum+"===listMap=="+answerListMap);	
					Answer answer = new Answer();
					answer.setLessonId(Long.parseLong(answerListMap.get(i).get("lessonId")+""));
					answer.setClassId(Long.parseLong(answerListMap.get(i).get("classId")+""));
					answer.setQuestionNo(Long.parseLong(answerListMap.get(i).get("questionNo")+""));
					answer.setStuId(Long.parseLong(answerListMap.get(i).get("stuId")+""));
					answer.setStuAnswer(answerListMap.get(i).get("stuAnswer")+"");
					AjaxMsg rs = answerService.saveAnswer(answer);
					if(rs.isSuccess()==true){
						//答题成功后从列表清除
						AnswerListener.answerListMap.remove(answerListMap.get(i));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
