package education.model;

import tools.Pagination;

public class Question extends Pagination{

	private Long id;	//序列号
	private Long lessonId;	//课时号
	private Long questionNo;	//题号
	private String questionType;	//题型
	private String knowledge;	//知识点
	private String question;	//题目
	private String techAnswer;	//答案
	private String equivalence;	//等价
	private String typicalFault;	//典型错误
	private String missKeyWord;	//缺失关键字
	
	private String answerId;
	private String stuAnswer;	//学生答案
	private Long classId;	//班级号
	private String stuName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLessonId() {
		return lessonId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public Long getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(Long questionNo) {
		this.questionNo = questionNo;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getTechAnswer() {
		return techAnswer;
	}
	public void setTechAnswer(String techAnswer) {
		this.techAnswer = techAnswer;
	}
	public String getEquivalence() {
		return equivalence;
	}
	public void setEquivalence(String equivalence) {
		this.equivalence = equivalence;
	}
	public String getTypicalFault() {
		return typicalFault;
	}
	public void setTypicalFault(String typicalFault) {
		this.typicalFault = typicalFault;
	}
	public String getMissKeyWord() {
		return missKeyWord;
	}
	public void setMissKeyWord(String missKeyWord) {
		this.missKeyWord = missKeyWord;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getStuAnswer() {
		return stuAnswer;
	}
	public void setStuAnswer(String stuAnswer) {
		this.stuAnswer = stuAnswer;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
}