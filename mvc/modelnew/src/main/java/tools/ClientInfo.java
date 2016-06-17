package tools;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息
 * @author XY
 *
 */
public class ClientInfo implements Serializable{
	/** long serialVersionUID **/
	private static final long serialVersionUID = 8569358771051627463L;

	private String client_name;
	private String client_ver;
	private String client_ip;
	
	public ClientInfo(HttpServletRequest request){
		String agent = request.getHeader("user-agent");
		setVer(agent);
			    
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }
	    client_ip=ip;
	}
	
	public ClientInfo(String client_name, String client_ver, String client_ip) {
		super();
		this.client_name = client_name;
		this.client_ver = client_ver;
		this.client_ip = client_ip;
	}

	private void setVer(String agent){
		agent=agent.toLowerCase();
		Pattern p = null;

		for(_CLIENT client:_CLIENT.values()){
			String key=client.getKey();
			if("".equals(key)){client_ver="OHTER VER";return;}
			if(agent.contains(client.getKey())){
				p=Pattern.compile(client.getRegex());
				client_name=client.getName();
				break;
			}
		}
		 
		Matcher m = p.matcher(agent);
		try{
			m.find();
			client_ver=m.group();
		}catch(Exception e){
			
		}

	}




	/**
	 * @return the client_name
	 */
	public String getClient_name() {
		return client_name;
	}

	/**
	 * @param client_name the client_name to set
	 */
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	/**
	 * @return the client_ver
	 */
	public String getClient_ver() {
		return client_ver;
	}

	/**
	 * @param client_ver the client_ver to set
	 */
	public void setClient_ver(String client_ver) {
		this.client_ver = client_ver;
	}

	/**
	 * @return the client_ip
	 */
	public String getClient_ip() {
		return client_ip;
	}

	/**
	 * @param client_ip the client_ip to set
	 */
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"ClientInfo [client_name=%s, client_ver=%s, client_ip=%s]",
				client_name, client_ver, client_ip);
	}
	
	
}

/**
 * Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/536.28.10 (KHTML, like Gecko) Version/6.0.3 Safari/536.28.10
 * Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36
 * Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:21.0) Gecko/20100101 Firefox/21.0
 * Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)
 * Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2)
 * Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
 * Mozilla/4.0 (compatible; MSIE 5.0; Windows NT)
 * Mozilla/5.0 (Windows; U; Windows NT 5.2) Gecko/2008070208 Firefox/3.0.1
 * Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070309 Firefox/2.0.0.3
 * Mozilla/5.0 (Windows; U; Windows NT 5.1) Gecko/20070803 Firefox/1.5.0.12
 * Opera/9.27 (Windows NT 5.2; U; zh-cn)
 * Opera/8.0 (Macintosh; PPC Mac OS X; U; en)
 * Mozilla/5.0 (Macintosh; PPC Mac OS X; U; en) Opera 8.0
 * Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 Safari/525.13
 * Mozilla/5.0 (iPhone; U; CPU like Mac OS X) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/4A93 Safari/419.3 
 * Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13
 */
    enum _CLIENT{
	IE("ie","msie","msie ([\\d.]+)"),
	FIREFOX("firefox","firefox","firefox\\/([\\d.]+)"),
	CHROME("chrome","chrome","chrome\\/([\\d.]+)"),
	OPERA("opera","opera","opera.([\\d.]+)"),
	SAFARI("safari","version","version\\/([\\d.]+)"),
	OTHER("other","","");
	
	private String name;
	private String key;
	private String regex;
	_CLIENT(String name,String key,String regex){
		this.name=name;
		this.key=key;
		this.regex=regex;
	}
    public String getName(){
    	return this.name;
    }
    public String getKey(){
    	return this.key;
    }
    public String getRegex(){
    	return this.regex;
    }

	
}