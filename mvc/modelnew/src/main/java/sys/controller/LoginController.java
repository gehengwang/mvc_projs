package sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import sys.service.LoginService;
import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.ClientInfo;


/**
 * 登录控制
 * @author xy
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private Logger log = Logger.getLogger(LoginController.class);
	@Resource
	private LoginService loginService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "/sys/login/login";
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("register")
	public String register(){
		return "/sys/login/register";
	}
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("home")
	public String home(){
		return "/sys/login/home";
	}
	
	/**
	 * 登录
	 * @param httpServletRequest
	 * @param user
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest httpServletRequest,User user,Model model){
		UserInfo userInfo = new UserInfo();
		HttpSession session = httpServletRequest.getSession();
		if(StringUtils.isEmpty(user.getUserName())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getPassword())){
			return"/sys/login/login";
		}
		AjaxMsg ajaxMsg = loginService.login(user,userInfo);
		if(ajaxMsg.isSuccess()){
			ClientInfo clientInfo = new ClientInfo(httpServletRequest);
			userInfo.setClientInfo(clientInfo);
			session.setAttribute("USER_INFO", userInfo);
			session.setAttribute("LAST_ACCESSED", System.currentTimeMillis());
			return "/sys/login/home";
		}else{
			model.addAttribute("errors", ajaxMsg.getMsg());
			model.addAttribute("user_name", user.getUserName());
			model.addAttribute("mobile", user.getMobile());
			model.addAttribute("password", user.getPassword());
			return"/sys/login/login";
		}
	}
	
	@RequestMapping("loginOut")
	public String loginOut(HttpSession session){
		if(session!=null&&session.getAttribute("USER_INFO")!=null){
			UserInfo userInfo=(UserInfo) session.getAttribute("USER_INFO");
			log.info(userInfo.getUser().getUserName()+"注销");
		}
		session.invalidate();
		return "/sys/login/login";
	}
	
}