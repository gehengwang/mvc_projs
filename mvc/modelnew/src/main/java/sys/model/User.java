package sys.model;

import java.io.Serializable;
import java.util.Date;



public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7310122084772844456L;
	private Long userId;	//用户ID
	private String userName;	//用户名
	private String mobile;	//手机号码
	private String password;	//密码
	private Integer sex;	//性别
	private String qq;	
	private String email;
	private String weixin;	//微信号
	private String schoolName;	//学校名称
	private Integer userStatus;	//学生状态:0-禁用,1-正常
	private String userStatusName;	//用户状态
	private Date createDate;	//册时间
	private Integer loginTimes;	//登录次数
	private Integer answerTimes;	//答题次数
	private Long stuId;	//家长所选学生id
	
	//用户相关部门、角色信息
	private Long roleId;	//用户角色：1-admin，2-机构管理员,3-教师,4-家长,5-学生
	private Long deptId;	//机构号
	private Long classId;	//班级号
	
	private String flag;//不同菜单查询不同用户——0:学生信息;1:教师信息
	private String className;	//所选班级名称
	private String sexName;//性别
	private String techId;	//老师
	private String lessonName;
	
	private Long[] userIds;//批量用户
	private Long[] classIds;	//班级ids
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public Integer getAnswerTimes() {
		return answerTimes;
	}
	public void setAnswerTimes(Integer answerTimes) {
		this.answerTimes = answerTimes;
	}
	public Long getStuId() {
		return stuId;
	}
	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUserStatusName() {
		return userStatusName;
	}
	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getTechId() {
		return techId;
	}
	public void setTechId(String techId) {
		this.techId = techId;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public Long[] getUserIds() {
		return userIds;
	}
	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}
	public Long[] getClassIds() {
		return classIds;
	}
	public void setClassIds(Long[] classIds) {
		this.classIds = classIds;
	}
}