package sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sys.dao.ClassesDao;
import sys.model.Classes;
import sys.model.UserInfo;
import sys.service.ClassesService;
import tools.AjaxMsg;
import tools.PageUI;
import tools.Pagination;

@Service
public class ClassesServiceImpl implements ClassesService{

	@Resource
	private ClassesDao classesDao;
	
	@Transactional
	@Override
	public AjaxMsg saveClassLesson(Classes classes) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		classesDao.deleteClassLesson(classes);
		Long[] classIds = classes.getClassIds();
		Long[] lessonIds = classes.getLessonIds();
		int rsAdd = 0;
		int rsCheck = 0;
		try{
			for(int i=0;i<classIds.length;i++){
				classes.setClassId(classIds[i]);
				for(int j=0;j<lessonIds.length;j++){
					classes.setLessonId(lessonIds[j]);
					int check = classesDao.checkClassLesson(classes);
					rsCheck += check;
					if(check==0){
						rsAdd += classesDao.addClassLesson(classes);
					}
				}
			}
			if(rsCheck+rsAdd==classes.getClassIds().length*classes.getLessonIds().length){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("配置成功"+(rsCheck+rsAdd)+"条记录");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("配置失败");
			}
		}catch(Exception e){
			throw new RuntimeException();
		}
		
		return ajaxMsg;
	}

	@Override
	public AjaxMsg updateClassLessonStatus(Classes classes) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		int rs = classesDao.updateClassLessonStatus(classes);
		if(rs==0){
			ajaxMsg.setSuccess(false);
			ajaxMsg.setMsg("更改失败");
		}else{
			ajaxMsg.setMsg("更改成功");
		}
		return ajaxMsg;
	}

	
	@Override
	public List<?> queryClassesList(UserInfo userInfo,Long deptId) {
		// TODO 自动生成的方法存根
		return classesDao.queryClassesList(userInfo,deptId);
	}

	@Override
	public PageUI queryClassPage(Pagination pagination,UserInfo userInfo, Classes classes) {
		// TODO 自动生成的方法存根
		return classesDao.queryClassPage(pagination,userInfo,classes);
	}

	@Override
	public PageUI classLessonPage(Pagination pagination,Classes classes,UserInfo userInfo) {
		// TODO 自动生成的方法存根
		return classesDao.classLessonPage(pagination, classes,userInfo);
	}
	
	@Override
	public AjaxMsg saveClass(Classes classes) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		classes.setClassNo("0");
		Long classId = classes.getClassId();
		if(classId==null){
			int rs = classesDao.insertClass(classes);
			if(rs>0){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("添加成功");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("添加失败");
			}
		}else{
			int rs = classesDao.updateClass(classes);
			if(rs>0){
				ajaxMsg.setSuccess(true);ajaxMsg.setMsg("更新成功");
			}else{
				ajaxMsg.setSuccess(false);ajaxMsg.setMsg("更新失败");
			}
		}
		
		return ajaxMsg;
	}

	@Override
	public AjaxMsg queryClassObject(Classes classes) {
		// TODO 自动生成的方法存根
		AjaxMsg ajaxMsg = new AjaxMsg();
		Classes classesObj = classesDao.queryClassObject(classes);
		if(classesObj == null){
			ajaxMsg.setSuccess(false);ajaxMsg.setMsg("请确认是否配置了该班级");
		}else{
			ajaxMsg.setSuccess(true);ajaxMsg.setMsg("班级有效");
		}
		return ajaxMsg;
	}

}
