package tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;



/**
 * 加载配置文件公用方法
 * @author
 * @version   2015-12-24 12:15:19
 */
public class ConfigUtil {
	
	/**
	 * @param  name properties文件名称
	 * @return properties对象
	 * @throws Exception
	 */
	public static Properties loadProperties(String name) throws Exception{
		Properties properties=new Properties();
		//URL fileUrl = Thread.currentThread().getContextClassLoader().getResource(name + ".properties");
		URL fileUrl = ConfigUtil.class.getClassLoader().getResource(name + ".properties");
        if (fileUrl == null) {
            /*if (LOG.isDebugEnabled()) {
        	LOG.debug(name + ".properties missing");
            }*/
            return null;
        }
		InputStream in = null;
        try {
        	in = fileUrl.openStream();
            properties.load(in);
        } catch (Exception e) {
            throw new Exception("Could not load " + name + ".properties:" + e, e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException io) {
                    /*if (LOG.isTraceEnabled()) {
                	LOG.warn("Unable to clos+
                	e input stream", io);
                    }*/
                }
            }
        }
		return properties;
	}
}