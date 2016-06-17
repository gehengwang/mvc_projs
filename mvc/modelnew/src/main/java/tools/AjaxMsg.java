package tools;

import java.io.Serializable;

public class AjaxMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2826274926462843777L;
	private boolean success = true;	//成功与否：默认为true，则只要给失败的AjaxMsg赋值
	private String msg;	//消息内容
	private Object backParam;	//回传参数
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getBackParam() {
		return backParam;
	}
	public void setBackParam(Object backParam) {
		this.backParam = backParam;
	}
}