package education.model;

import java.io.Serializable;

public class Report implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7985042559567179095L;
	private Long deptId;	//机构id
	private String deptName;
	private Long techIdUse;	//教师id
	private String techNameUse;
	private Long stuId;	//学生id
	private String stuName;
	private Long lessonId;	//	课时号
	private String lessonName;	//课时名
	private Long quesSum;	//题目总数
	private Long quesComp;	//完成题目数
	private Long quesCompRight;	//完成数正确
	private Long quesCompError;	//完成数错误
	private String quesCompRate;	//完成数正确率
	private Long quesUndo;	//未完成题目数
	private String stuScoresRank;	//学生成绩排名
	private String question;	//题目
	private String answerRate;	//题目正确率
	private String lessonRate;	//课件正确率
	private Long questionId;	//题目id
	private String stuAnswer;	//答案
	private String answerResult;	//答案结果
	private String lessonSum;	//课时总数
	private String lessonRight;	//正确数
	private String lessonError;	//错误数
	private Long questionNo;	//题目编号
	private Long classId;	//班级号
	private String className;	//班级名称
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getTechIdUse() {
		return techIdUse;
	}
	public void setTechIdUse(Long techIdUse) {
		this.techIdUse = techIdUse;
	}
	public String getTechNameUse() {
		return techNameUse;
	}
	public void setTechNameUse(String techNameUse) {
		this.techNameUse = techNameUse;
	}
	public Long getStuId() {
		return stuId;
	}
	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Long getLessonId() {
		return lessonId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public Long getQuesSum() {
		return quesSum;
	}
	public void setQuesSum(Long quesSum) {
		this.quesSum = quesSum;
	}
	public Long getQuesComp() {
		return quesComp;
	}
	public void setQuesComp(Long quesComp) {
		this.quesComp = quesComp;
	}
	public Long getQuesCompRight() {
		return quesCompRight;
	}
	public void setQuesCompRight(Long quesCompRight) {
		this.quesCompRight = quesCompRight;
	}
	public Long getQuesCompError() {
		return quesCompError;
	}
	public void setQuesCompError(Long quesCompError) {
		this.quesCompError = quesCompError;
	}
	public String getQuesCompRate() {
		return quesCompRate;
	}
	public void setQuesCompRate(String quesCompRate) {
		this.quesCompRate = quesCompRate;
	}
	public Long getQuesUndo() {
		return quesUndo;
	}
	public void setQuesUndo(Long quesUndo) {
		this.quesUndo = quesUndo;
	}
	public String getStuScoresRank() {
		return stuScoresRank;
	}
	public void setStuScoresRank(String stuScoresRank) {
		this.stuScoresRank = stuScoresRank;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswerRate() {
		return answerRate;
	}
	public void setAnswerRate(String answerRate) {
		this.answerRate = answerRate;
	}
	public String getLessonRate() {
		return lessonRate;
	}
	public void setLessonRate(String lessonRate) {
		this.lessonRate = lessonRate;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getStuAnswer() {
		return stuAnswer;
	}
	public void setStuAnswer(String stuAnswer) {
		this.stuAnswer = stuAnswer;
	}
	public String getAnswerResult() {
		return answerResult;
	}
	public void setAnswerResult(String answerResult) {
		this.answerResult = answerResult;
	}
	public String getLessonSum() {
		return lessonSum;
	}
	public void setLessonSum(String lessonSum) {
		this.lessonSum = lessonSum;
	}
	public String getLessonRight() {
		return lessonRight;
	}
	public void setLessonRight(String lessonRight) {
		this.lessonRight = lessonRight;
	}
	public String getLessonError() {
		return lessonError;
	}
	public void setLessonError(String lessonError) {
		this.lessonError = lessonError;
	}
	public Long getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(Long questionNo) {
		this.questionNo = questionNo;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}