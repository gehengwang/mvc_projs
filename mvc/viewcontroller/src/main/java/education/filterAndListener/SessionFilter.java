package education.filterAndListener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet拦截器：
 * Spring配置了mvc:interceptors后，
 * servlet标注@WebFilter
 * @WebFilter(urlPatterns={"/login/login.do","/login/main.do","/login/index.do"})
 * @author xy
 *
 */

public class SessionFilter implements Filter{

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO 自动生成的方法存根
		System.out.println("====servlet==interceptor==");
		//把ServletRequest和ServletResponse转换成真正的类型
        HttpServletRequest req = (HttpServletRequest)arg0;
        //HttpServletResponse resp = (HttpServletResponse) arg1;
        HttpSession session = req.getSession();
        //由于web.xml中设置Filter过滤全部请求，可以排除不需要过滤的url
        String requestURI = req.getRequestURI();
        if(requestURI.endsWith("login.jsp")||requestURI.endsWith("register.jsp")||requestURI.endsWith("CheckRegisterServlet")||
        		requestURI.endsWith("RegisterServlet")||requestURI.endsWith("LoginServlet")){
        	//处理完后调用FilterChain继续处理，直接return整个处理就结束了，css、js等就加载不进来
        	arg2.doFilter(arg0, arg1);
        	return;
        }
        if(session.getAttribute("userno")==null){
        	//resp.sendRedirect("/login/login.jsp");//sendRedirect、forward的区别
        	req.getRequestDispatcher("/login/login.jsp").forward(arg0, arg1);
        	return;
        }
        arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
	}
}