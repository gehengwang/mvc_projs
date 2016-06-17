package sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sys.model.Dept;
import sys.model.UserInfo;
import sys.service.DeptService;
import tools.AjaxMsg;
import tools.ClientInfo;

/**
 * 机构管理
 * @author xy
 *
 */
@Controller
public class DeptController {

	@Resource
	DeptService deptService;
	
	@RequestMapping("dept_init")
	public String init(){
		return "/sys/dept/dept_init";
	}
	
	@RequestMapping("queryDeptList")
	public List<Dept> queryDeptList(Dept dept){
		List<Dept> deptList = deptService.queryDeptList(dept);
		return deptList;
	}
	
	@RequestMapping("queryDeptObj")
	public Dept queryDeptObj(Dept dept){
		return deptService.queryDeptObj(dept);
	}
	
	
	/**
	 * 机构注册
	 * @param request
	 * @param dept
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addDept")
	public AjaxMsg addDept(HttpServletRequest request,Dept dept) throws Exception{
		ClientInfo clientInfo = new ClientInfo(request);
		dept.setDeptIp(clientInfo.getClient_ip());
		dept.setDeptAttr(0);
		AjaxMsg ajaxMsg = deptService.addDept(dept);
		return ajaxMsg;
	}
	
	@RequestMapping("updateDept")
	public AjaxMsg updateDept(Dept dept){
		AjaxMsg ajaxMsg = deptService.updateDept(dept);
		return ajaxMsg;
	}
	
	@RequestMapping("deleteDepts")
	public AjaxMsg deleteDepts(String deptIds){
		AjaxMsg ajaxMsg = deptService.deleteDepts(deptIds);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("checkDeptName")
	public AjaxMsg checkDeptName(String deptName){
		AjaxMsg ajaxMsg = deptService.checkDeptName(deptName);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("checkDeptEmail")
	public AjaxMsg checkDeptEmail(String deptEmail){
		AjaxMsg ajaxMsg = deptService.checkDeptEmail(deptEmail);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("checkDeptMobile")
	public AjaxMsg checkDeptMobile(String deptMobile){
		AjaxMsg ajaxMsg = deptService.checkDeptMobile(deptMobile);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("deptCombo")
	public List<?> deptCombo(HttpServletRequest request){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<?> listMap = deptService.deptCombo(userInfo);
		return listMap;
	}
}