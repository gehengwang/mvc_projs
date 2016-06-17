package education.filterAndListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tools.AjaxMsg;
import education.model.Answer;
import education.service.AnswerService;

/**
 * Servlet监听：支持@WebListener标注
 * @WebServlet(description="init", loadOnStartup=1,urlPatterns = { "" }
 * @author xy
 *
 */
//@WebListener
public class AnswerListener extends ContextLoaderListener{

	public static List<Map<String,Object>> answerListMap = new ArrayList<Map<String,Object>>();
	public static String serialNum = "";
	
	@Autowired
	private Timer timer = null;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根
		timer = new Timer();
		/*ServletContext servletContext=arg0.getServletContext();
        ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(servletContext);
        AnswerService answerService=(AnswerService)ctx.getBean("answerService");*/
	}
}