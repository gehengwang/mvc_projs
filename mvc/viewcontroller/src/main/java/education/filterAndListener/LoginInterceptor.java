package education.filterAndListener;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器:Spring拦截器
 * @author xy
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	public static String AJAX_HEADER="X-Requested-With";
	public static String NO_TIME_COUNT="NO_TIME_COUNT";
	public static String SESSION_TIME_OUT="SESSION_TIME_OUT";
	public static String LAST_ACCESSED="LAST_ACCESSED";
	String[] excludes={"/login/index.do","/login/loginout.do","/login/login.do",
			"/login/register.do","/saveUser.do","/addDept.do","/validateStudent.do",
			"/validateDept.do","/checkUser.do","/checkDeptName.do","/checkDeptMobile.do",
			"/checkDeptEmail.do","/queryClassesList.do","/getStuAnswer.do","/sendStuAnswer.do","/queryClassObject.do"};
	
	/**  
	* 在业务处理器处理请求之前被调用  
	* 如果返回false  
	*     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链  
	*   
	* 如果返回true  
	*    执行下一个拦截器,直到所有的拦截器都执行完毕  
	*    再执行被拦截的Controller  
	*    然后进入拦截器链,  
	*    从最后一个拦截器往回执行所有的postHandle()  
	*    接着再从最后一个拦截器往回执行所有的afterCompletion()  
	*/  
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("====拦截器到这啦===="+request.getRequestURL()); 
		// TODO 自动生成的方法存根
		 String noTimeCount = request.getHeader(NO_TIME_COUNT);
		 String type = request.getHeader(AJAX_HEADER);
	     String uri = request.getRequestURI();
	       
	        //排除不需要拦截的页面
	     	Arrays.sort(excludes);
	        int tmp = Arrays.binarySearch(excludes, uri);
	        if(tmp>=0)return true;
	        HttpSession session = request.getSession();
	        if(session!=null){
	        	Object last_accessed=session.getAttribute(LAST_ACCESSED);
	        	int time_out = 0;// UserCount.timeOut;
	        	long last_time=last_accessed!=null?((Long)last_accessed).longValue()+time_out*60*1000:Long.MAX_VALUE;
	        	//判断是否超时：session设置30分钟超时
	        	if(System.currentTimeMillis()>(last_time)){
	    			session.invalidate();
	    		}
	        }
	        
	        //session 是否失效
	        if(session==null||session.getAttribute("USER_INFO")==null){
	        	//Ajax 请求的跳转
			/*	if("XMLHttpRequest".equalsIgnoreCase(type)){
					response.setHeader(SESSION_TIME_OUT, SESSION_TIME_OUT);
					return false;
				}else{*///其他请求的跳转
					response.sendRedirect("/sessionInvalid.jsp");
					return false;
				//}
	        	
	        }/*else{
	        	//不会触发计时的请求 
	        	if(NO_TIME_COUNT.equals(noTimeCount)){		
	        	}else{//普通请求 计时
	        		session.setAttribute(LAST_ACCESSED, System.currentTimeMillis());
	        	}
	        }*/
	        return true;
	}

	/** 
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，
     * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操 作。
     * 这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像， 
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，
     * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor 
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，
     * 要在Interceptor之后调用的内容都写在调用invoke方法之后。 
     */  
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO 自动生成的方法存根
		
	}

	/** 
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。 
     */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO 自动生成的方法存根
	}
}