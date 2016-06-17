package sys.model;

import java.io.Serializable;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4462438979780017129L;
	private Integer roleGroup;	//角色组
	private Long roleId;	//角色代码
	private String roleName ;	//角色名称
	private Long orderId;	//排序号
	private Integer roleFlag; 	//角色状态
	
	//部门信息
	private Long deptId;	//部门机构
	private Long classId;	//班级号
	
	public static final Long ROLE_admin = 1l;
	public static final Long ROLE_dept_admin = 2l;
	public static final Long ROLE_tech = 3l;
	public static final Long ROLE_parent = 4l;
	public static final Long ROLE_student = 5l;
	
	public Integer getRoleGroup() {
		return roleGroup;
	}
	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
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
}