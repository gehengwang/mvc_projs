package education.model;

public class Answer {

	private Long id;	//序列号
	private Long lessonId;	//课时号
	private Long questionNo;	//题号
	private Long stuId;	//学生Id
	private String stuAnswer;	//学生答案
	private String answerResult;	//答题结果，对错：0-错,1-对
	private String errorFlag;	//错误位置标识
	private String createDate;	//创建时间
	private String modifyDate;	//修改时间
	private Long answerType;	//操作类型：0-课堂作业,1-家庭作业
	private Long answerTimes;	//答题次数：第几次答题
	
	private Long answerId;	//接收answer的id
	private String question;	//题目
	private String techAnswer;	//正确答案
	private String answerResultName;	//判定结果
	private int[][] errorFlagShow;	//前台标注展示
	private Long classId;
	private String stuName;
	
	private String snapName;	//快照名称
	private String[] snapNames;	//批量快照
	
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
	public Long getStuId() {
		return stuId;
	}
	public void setStuId(Long stuId) {
		this.stuId = stuId;
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
	public String getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getAnswerType() {
		return answerType;
	}
	public void setAnswerType(Long answerType) {
		this.answerType = answerType;
	}
	public Long getAnswerTimes() {
		return answerTimes;
	}
	public void setAnswerTimes(Long answerTimes) {
		this.answerTimes = answerTimes;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
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
	public String getAnswerResultName() {
		return answerResultName;
	}
	public void setAnswerResultName(String answerResultName) {
		this.answerResultName = answerResultName;
	}
	public int[][] getErrorFlagShow() {
		return errorFlagShow;
	}
	public void setErrorFlagShow(int[][] errorFlagShow) {
		this.errorFlagShow = errorFlagShow;
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
	public String getSnapName() {
		return snapName;
	}
	public void setSnapName(String snapName) {
		this.snapName = snapName;
	}
	public String[] getSnapNames() {
		return snapNames;
	}
	public void setSnapNames(String[] snapNames) {
		this.snapNames = snapNames;
	}
	public static void main(String[] args) {
	}
}