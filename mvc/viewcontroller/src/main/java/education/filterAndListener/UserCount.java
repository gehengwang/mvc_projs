package education.filterAndListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import sys.model.UserInfo;


public class UserCount implements HttpSessionListener,HttpSessionAttributeListener{

	private static Logger log = Logger.getLogger(UserCount.class);

	/** int count 人数统计变量*/
	public static int count = 0;
	public static int timeOut = 1;

	/**
	 * 用户统计
	 */
	public UserCount() {
		log.info("当前在线人数" + count);
	}

	/**
	 * 获得count
	 * @return int
	 */
	public int getCount() {
		return count;
	} 
	
	public static void setCount(int count) {
		UserCount.count = count;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO 自动生成的方法存根
		log.info("sessionCreated"); 
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO 自动生成的方法存根
		log.info("sessionDestroyed");
		UserInfo userInfo=(UserInfo)((HttpSession) se.getSession()).getAttribute("USER_INFO");
		if (null!=userInfo) {
			if (count <= 0)
				count = 0;
			else
				count--;
			log.info("当前在线人数" + count);
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		// TODO 自动生成的方法存根
		UserInfo userInfo = (UserInfo) event.getSession().getAttribute("USER_INFO");
		if (null!=userInfo) {
			count++;
			log.info("当前在线人数" + count);
		}  
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO 自动生成的方法存根
		
	}
}