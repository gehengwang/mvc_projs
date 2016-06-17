package sys.service;

import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;

public interface LoginService {

	public AjaxMsg login(User user,UserInfo userInfo);
}
