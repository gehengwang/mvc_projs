package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	public static  String getParameter(String name, Map<?, ?> parameterMap){
		Object valueObj = parameterMap.get(name);
		if(valueObj==null)return null;
		else if(valueObj instanceof String[]) {
			String[] rs=(String[]) valueObj;
			return rs[0];
		}else return valueObj.toString();
	}
	public static  int getIntParameter(String name, Map<?, ?> parameterMap){
		String str=getParameter( name, parameterMap);
		int rs=0;
		try {
			rs=Integer.parseInt(str);
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return rs;
		
	}
	public static  String[] getParameterValues(String name, Map<?, ?> parameterMap){
		Object valueObj = parameterMap.get(name);
		if(valueObj instanceof String[]) {
			String[] rs=(String[]) valueObj;
			return rs;
		}else return null;
	}
	

	public static String StrVal(Map<String, String[]> parameterMap){
		StringBuffer sb=new StringBuffer();
		for(String  key:parameterMap.keySet()){
			String[] values=parameterMap.get(key);
			for(String val:values){
				sb.append("&"+key+"="+val);
			}
		}
		return sb.toString();	
	}
	
	public static void ToJson(Object obj, HttpServletResponse response) throws IOException {
        String result = JSON.toJSONString(obj);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}
