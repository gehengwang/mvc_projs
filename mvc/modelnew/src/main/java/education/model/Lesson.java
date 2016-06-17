package education.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Lesson {

	private Long lessonId;	//	课时号
	private String lessonNo;	//课程编号
	private String lessonName;	//课时名
	private Long techIdOwn;	//教师id
	private Long deptId;	//机构id
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;	//创建时间
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;	//修改时间
	private String lessonStatus;	//课件状态：inuse,temp,nouse
	
	//用于展示
	private String techNameOwn;	//老师姓名
	private Long classId;	//班级号
	private String className;	//班级名称
	private String deptName;	//机构名称
	private Long techIdUse;
	private String techNameUse;
	private Long[] techIds;
	private Long[] lessonIds;
	
	public Long getLessonId() {
		return lessonId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public String getLessonNo() {
		return lessonNo;
	}
	public void setLessonNo(String lessonNo) {
		this.lessonNo = lessonNo;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	
	public Long getTechIdOwn() {
		return techIdOwn;
	}
	public void setTechIdOwn(Long techIdOwn) {
		this.techIdOwn = techIdOwn;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getLessonStatus() {
		return lessonStatus;
	}
	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}
	
	public String getTechNameOwn() {
		return techNameOwn;
	}
	public void setTechNameOwn(String techNameOwn) {
		this.techNameOwn = techNameOwn;
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
	public Long[] getTechIds() {
		return techIds;
	}
	public void setTechIds(Long[] techIds) {
		this.techIds = techIds;
	}
	public Long[] getLessonIds() {
		return lessonIds;
	}
	public void setLessonIds(Long[] lessonIds) {
		this.lessonIds = lessonIds;
	}
}