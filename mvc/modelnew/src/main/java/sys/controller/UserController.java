package sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//import education.filterAndListener.UserCount;
import sys.service.UserService;
import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.DateTimeUtils;
import tools.PageUI;
import tools.Pagination;
import tools.excel.ExcelFun;
import tools.excel.ExcelLocation;
import tools.excel.ReportDataDraw;
import tools.excel.ReportExcelView;
import tools.excel.ReportHeadDraw;
import tools.excel.ReportTitleDraw;

/**
 * 用户信息控制
 * @author xy
 *
 */
@Controller
public class UserController {

	@Resource
	UserService userService;
	
	@RequestMapping("queryStuInit")
	public String queryStuInit(String flag){
		return "/info/student_info";
	}
	
	@RequestMapping("queryTechInit")
	public String queryTechInit(String flag){
		return "/info/teacher_info";
	}
	
	@ResponseBody
	@RequestMapping("queryUserPage")
	public PageUI queryUserPage(HttpServletRequest request,Pagination pagination,User user){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		PageUI pageUI = userService.queryUserPage(pagination, user, userInfo);
		return pageUI;
	}
	
	@ResponseBody
	@RequestMapping("saveUser")
	public AjaxMsg saveUser(User user){
		AjaxMsg ajaxMsg = userService.saveUser(user);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("updateUser")
	public AjaxMsg updateUser(User user){
		AjaxMsg ajaxMsg = userService.updateUser(user);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("updateUserStatus")
	public AjaxMsg updateUserStatus(Long[] userIds,String userStatus){
		AjaxMsg ajaxMsg = userService.updateUserStatus(userIds, userStatus);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("checkUser")
	public AjaxMsg checkUser(User user){
		AjaxMsg ajaxMsg = userService.checkUser(user);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("updatePassword")
	public AjaxMsg updatePassword(HttpServletRequest request,Long[] userIds,String password){
		if(null == userIds){
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
			Long[] userId = {userInfo.getUser().getUserId()};
			userIds = userId;
		}
		AjaxMsg ajaxMsg = userService.updatePassword(userIds, password);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("validateStudent")
	public AjaxMsg validateStudent(User user){
		AjaxMsg ajaxMsg = userService.validateStudent(user);
		return ajaxMsg;
	}
	
	@RequestMapping("userExport")
	public ModelAndView userExport(User user,HttpServletRequest request){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<?> userList = userService.queryUserList(user, userInfo);
		ReportExcelView reportExcelView = new ReportExcelView("用户信息表"+DateTimeUtils.getDateTime(), 0);
		String sheetName = "用户信息表";
		reportExcelView.setHeadDraw(new ReportHeadDraw(new ExcelLocation(sheetName,1,7), "用户信息表"));
		reportExcelView.setTitleDraw(new ReportTitleDraw(sheetName,
				new String[]{"姓名","手机号码","性别","QQ","微信","学校名称","用户状态"}));
		reportExcelView.setDataDraw(new ReportDataDraw(new ExcelLocation(sheetName), userList
				,new String[]{"userName","mobile","sexName","qq","weixin","schoolName","userStatusName"}));
		return new ModelAndView(reportExcelView);
	}
	
	@ResponseBody
	@RequestMapping("techCombo")
	List<?> techCombo(HttpServletRequest request,Long classId){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		List<?> ListMap = userService.techCombo(userInfo, classId);
		return ListMap;
	}
	
	@ResponseBody
	@RequestMapping("saveUserBind")
	AjaxMsg saveUserBind(HttpServletRequest request,User user){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER_INFO");
		user.setDeptId(userInfo.getRoles().get(0).getDeptId());
		AjaxMsg ajaxMsg = userService.saveUserBind(user);
		return ajaxMsg;
	}
	
	@ResponseBody
	@RequestMapping("/importUsers")
	public AjaxMsg importUsers(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "file") MultipartFile file,User user) throws Exception{
		Workbook workbook = ExcelFun.getWorkBook(file.getInputStream(), file.getOriginalFilename());
		UserInfo userInfo  = (UserInfo)request.getSession().getAttribute("USER_INFO");
		AjaxMsg rs = userService.importUsers(workbook, userInfo,user);
		return rs;
	}
	
	 /**
     * 在线人数统计
     * @return String
     * @throws IOException
     */
	
	@Autowired  
	private HttpServletRequest request;  
	@ResponseBody
	@RequestMapping("online")
    public Map<String,Integer> online(){

    	int online = 0;//UserCount.count;
    	HttpSession session = request.getSession();
    	Long last_accessed=(Long) session.getAttribute("LAST_ACCESSED");
    	int time_out = 0;//UserCount.timeOut;
    	int remain_time=(int) ((last_accessed+time_out*60*1000-System.currentTimeMillis())/1000);
    	Map<String, Integer> rs=new HashMap<String, Integer>();
    	rs.put("online", online);
    	rs.put("remain_time", remain_time);
		return rs;
    }
}