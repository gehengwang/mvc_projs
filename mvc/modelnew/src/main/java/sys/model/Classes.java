package sys.model;

public class Classes {

	private Long classId;	//序列号
	private String classNo;	//班级编号，生成规则待定
	private String className;	//班级名称
	private Long deptId;	//tb_dept表dept_id
	private String classStatus;
	
	private String classStatusName;
	private String deptName;
	private Long lessonId;
	private String lessonName;
	private Long techIdUse;
	private String techNameUse;	//老师姓名
	private Long questionStatus;	//是否开始答题：0-否,1-是
	private Long answerStatus;	//是否开放答案：0-否,1-是
	private String questionStatusName;
	private String answerStatusName;
	private String lessonStatus;	//课件状态：inuse,temp,nouse
	
	private Long userId;//用户id
	private Long[] classIds;	//批量班级id
	private Long[] lessonIds;	//批量课件id
	private Long techIdOwn;
	private String techNameOwn;	//老师姓名
	
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
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
	public String getClassStatus() {
		return classStatus;
	}
	public void setClassStatus(String classStatus) {
		this.classStatus = classStatus;
	}
	public String getClassStatusName() {
		return classStatusName;
	}
	public void setClassStatusName(String classStatusName) {
		this.classStatusName = classStatusName;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public Long getTechIdUse() {
		return techIdUse;
	}
	public void setTechIdUse(Long techIdUse) {
		this.techIdUse = techIdUse;
	}
	public Long getLessonId() {
		return lessonId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public Long getQuestionStatus() {
		return questionStatus;
	}
	public void setQuestionStatus(Long questionStatus) {
		this.questionStatus = questionStatus;
	}
	public Long getAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(Long answerStatus) {
		this.answerStatus = answerStatus;
	}
	public String getQuestionStatusName() {
		return questionStatusName;
	}
	public void setQuestionStatusName(String questionStatusName) {
		this.questionStatusName = questionStatusName;
	}
	public String getAnswerStatusName() {
		return answerStatusName;
	}
	public void setAnswerStatusName(String answerStatusName) {
		this.answerStatusName = answerStatusName;
	}
	
	public String getTechNameUse() {
		return techNameUse;
	}
	public void setTechNameUse(String techNameUse) {
		this.techNameUse = techNameUse;
	}
	public String getLessonStatus() {
		return lessonStatus;
	}
	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long[] getClassIds() {
		return classIds;
	}
	public void setClassIds(Long[] classIds) {
		this.classIds = classIds;
	}
	public Long[] getLessonIds() {
		return lessonIds;
	}
	public void setLessonIds(Long[] lessonIds) {
		this.lessonIds = lessonIds;
	}
	public Long getTechIdOwn() {
		return techIdOwn;
	}
	public void setTechIdOwn(Long techIdOwn) {
		this.techIdOwn = techIdOwn;
	}
	public String getTechNameOwn() {
		return techNameOwn;
	}
	public void setTechNameOwn(String techNameOwn) {
		this.techNameOwn = techNameOwn;
	}
	
}