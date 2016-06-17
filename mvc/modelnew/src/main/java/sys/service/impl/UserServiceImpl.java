package sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.service.ServiceException;
import sys.dao.UserDao;
import sys.service.UserService;
import sys.model.User;
import sys.model.UserInfo;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;
import tools.SecurityUtil;
import tools.excel.ExcelFun;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Resource
	UserDao userDao;

	@Override
	public User queryUserObj(User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		User userObj = userDao.queryUserObj(user);
		return userObj;
	}
	
	@Override
	//@Cacheable("queryUser")
	public PageUI queryUserPage(Pagination pagination, User user,UserInfo userInfo){
		return userDao.queryUserPage(pagination, user, userInfo);
	}

	@Override
	public List<User> queryUserList(User user, UserInfo userInfo) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		return userDao.queryUserList(user, userInfo);
	}
	
	@Override
	public AjaxMsg saveUser(User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		try{
			user.setPassword(SecurityUtil.md5(user.getPassword()));
			Map<String,Object> map = userDao.addUser(user);
			Long[] userIds = {Long.parseLong(map.get("userId")+"")};
			user.setUserIds(userIds);
			int rs = userDao.addUserBind(user);
			if(rs==0){
				ajaxMsg.setSuccess(false);
				ajaxMsg.setMsg("鐢ㄦ埛娉ㄥ唽澶辫触锛�");
			}else{
				ajaxMsg.setSuccess(true);
				ajaxMsg.setMsg("鎭枩浣犳敞鍐屾垚鍔燂紒绠＄悊鍛樹細灏藉揩瀹℃牳鎮ㄨ祫鏂欙紝鎰熻阿鎮ㄧ殑鏀寔锛�");
			}
		}catch(Exception e){
			throw new RuntimeException();
		}
		
		return ajaxMsg;
	}
	

	@Override
	//@CachePut(key="#user.userId",value="user")
	public AjaxMsg updateUser(User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = userDao.updateUser(user);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("鐢ㄦ埛淇敼澶辫触锛�");
		}else{
			ajaxMsg.setSuccess(true);
			ajaxMsg.setMsg("鐢ㄦ埛淇敼鎴愬姛锛�");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg updatePassword(Long[] userIds,String password) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		String passwordMd5 = SecurityUtil.md5(password);
		int rs = userDao.updatePassword(userIds,passwordMd5);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("瀵嗙爜淇敼澶辫触锛�");
		}else{
			ajaxMsg.setSuccess(true);
			ajaxMsg.setMsg("瀵嗙爜淇敼涓猴細"+password);
		}
		return ajaxMsg;
	}


	@Override
	public AjaxMsg checkUser(User user) {
		// TODO Auto-generated method stub
		AjaxMsg ajaxMsg = new AjaxMsg();
		User userObj = userDao.queryUserObj(user);
		if(null!=userObj){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("璇ョ敤鎴峰悕鍜屾墜鏈哄彿宸叉敞鍐�,璇烽噸鏂拌緭鍏�");
		}else{
			ajaxMsg.setMsg("璇ョ敤鎴峰悕鍜屾墜鏈哄彿鍙互浣跨敤锛�");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg validateStudent(User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		User userObj = userDao.queryUserObj(user);
		if(userObj==null){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("璇ュ鐢熷皻鏈敞鍐�,璇烽噸鏂拌緭鍏ワ紒");
		}else{
			ajaxMsg.setMsg("淇℃伅姝ｇ‘");
			ajaxMsg.setBackParam(userObj.getUserId());
		}
		return ajaxMsg;
	}

	@Override
	public List<?> techCombo(UserInfo userInfo,Long classId) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		return userDao.queryTechList(userInfo, classId);
	}

	@Override
	public AjaxMsg updateUserStatus(Long[] userIds, String userStatus) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = userDao.updateUserStatus(userIds, userStatus);
		if(rs>0){
			ajaxMsg.setSuccess(true);
			ajaxMsg.setMsg("鏇存柊鎴愬姛");
		}else{
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("鏇存柊澶辫触");
		}
		return ajaxMsg;
	}

	@Override
	public AjaxMsg saveUserBind(User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		userDao.deleteUserBind(user);
		int rs = userDao.addUserBind(user);
		if(rs==user.getUserIds().length*user.getClassIds().length){
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("閰嶇疆鎴愬姛!");
		}else{
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("閰嶇疆澶辫触!");
		}
		return ajaxMsg;
	}

	@Transactional
	@Override
	public AjaxMsg importUsers(Workbook workbook, UserInfo userInfo,User user) {
		// TODO 鑷姩鐢熸垚鐨勬柟娉曞瓨鏍�
		AjaxMsg ajaxMsg = new AjaxMsg();
		int errorNo = 0;
		String errorMsg = "";
		try{
			Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
			int rownum = sheet.getLastRowNum()-1;
			int column = sheet.getRow(0).getLastCellNum();
			String[][] strArray = new String[rownum][column];
			
			for(int i=2;i<=rownum+1;i++){
				Row row = sheet.getRow(i);
				if(row==null) break;
				for(int j=0;j<row.getLastCellNum();j++){
					strArray[i-2][j] =  ExcelFun.getStrValue(row.getCell(j));
				}
			}
			for(int n=0;n<strArray.length;n++){
				User userObj = new User();
				if(strArray[n][0]==null||strArray[n][0]==""){
					errorMsg = "绗�"+errorNo+"琛�,濮撳悕涓虹┖,璇蜂粩缁嗘鏌�!";
					throw new ServiceException();
				}
				if(strArray[n][1]==null||strArray[n][1]==""){
					errorMsg = "绗�"+errorNo+"琛�,鎵嬫満鍙风爜涓虹┖,璇蜂粩缁嗘鏌�!";
					throw new ServiceException();
				}
				userObj.setUserName(strArray[n][0]);
				userObj.setMobile(strArray[n][1]);
				if(strArray[n][2].equals("鐢�")){
					userObj.setSex(0);
				}else if(strArray[n][2].equals("濂�")){
					userObj.setSex(1);
				}
				userObj.setQq(strArray[n][3]);
				userObj.setWeixin(strArray[n][4]);
				userObj.setSchoolName(strArray[n][5]);
				userObj.setRoleId(user.getRoleId());
				userObj.setUserStatus(1);//鎵归噺瀵煎叆鐨勯粯璁ゅ惎鐢�
				userObj.setPassword(SecurityUtil.md5("123456"));
				Map<String,Object> map = userDao.addUser(userObj);
				Long[] userIds = {Long.parseLong(map.get("userId")+"")};
				userObj.setUserIds(userIds);
				userObj.setDeptId(userInfo.getRoles().get(0).getDeptId());
				userDao.addUserBind(userObj);
			}
			
			if(strArray.length>0){
				ajaxMsg.setSuccess(true);
				ajaxMsg.setMsg("鎴愬姛瀵煎叆"+strArray.length+"鏉¤褰�");
			}else{
				ajaxMsg.setSuccess(false);
				ajaxMsg.setMsg("瀵煎叆澶辫触");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(errorMsg);
		}
		return ajaxMsg;
	}
}