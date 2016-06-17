package sys.model;

import java.io.Serializable;

public class Dept implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102004070781652349L;
	private Long deptId;	//机构序列号
	private String deptCode;	//机构代码
	private String deptName;	//机构名称
	private String deptEmail;	//机构邮箱
	private String deptMobile;	//机构手机号码：邮箱和号码不能同时为空
	private String deptIp;	//注册机构IP地址
	private String createDate;	//注册时间
	private String modifyDate;	//修改时间
	private String applyDate;	//审批通过时间
	private Integer deptStatus;	//机构状态：0-禁用,1-正常，默认注册为0，审批通过后状态为1
	private Integer deptAttr;	//机构类别：0-线上,1-线下
	private String password;
	
	
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptEmail() {
		return deptEmail;
	}
	public void setDeptEmail(String deptEmail) {
		this.deptEmail = deptEmail;
	}
	public String getDeptMobile() {
		return deptMobile;
	}
	public void setDeptMobile(String deptMobile) {
		this.deptMobile = deptMobile;
	}
	public String getDeptIp() {
		return deptIp;
	}
	public void setDeptIp(String deptIp) {
		this.deptIp = deptIp;
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
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getDeptStatus() {
		return deptStatus;
	}
	public void setDeptStatus(Integer deptStatus) {
		this.deptStatus = deptStatus;
	}
	public Integer getDeptAttr() {
		return deptAttr;
	}
	public void setDeptAttr(Integer deptAttr) {
		this.deptAttr = deptAttr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}