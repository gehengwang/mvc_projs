<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="/css/register.css" />
<link rel="stylesheet" type="text/css" href="/css/frame.css" />
<link rel="stylesheet" type="text/css" href="/css/global.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<title>欢迎注册</title>
<script type="text/javascript">
	$(function(){
		initTab();
		xy.initValidations();
	});
	
	function initTab(){
		$(".tab").each(function(i,tab){
			var id = tab.id;
			var tabbody = $(tab).attr("tab-body");
			$("#"+id).click(function(){
				$(".tab").each(function(i,tab){
					$(tab).removeClass("choosed");
				});
				$(".tab-body").each(function(i,body){
					$(body).css("display","none");
				});
				$("#"+id).addClass("choosed");
				$("#"+tabbody).css("display","block");
			});
		});
	}
	
	function checkStudent(){
		var flag = false;
		var stuName = $("#stu_name").val();
		
		var roleId = $("input[name='roleId']:checked").val();
		if(roleId == 4){
			if(stuName==""||stuName==null){
				xy.validateTool.rules.checkStudent.falseMessage="家长必需填写学生信息";
			}else{
				$.ajax({  
			        type : "post",  
			        url : "/validateStudent.do",  
			        data : {"stuName":stuName},  
			        async : false,  
			        success : function(result){  
			        	result = eval("(" + result + ")"); 
			            if(result.success==false){
							xy.validateTool.rules.checkStudent.falseMessage=result.msg;
						}else{
							$("#stu_id").val(result.backParam);
							xy.validateTool.rules.checkStudent.trueMessage=result.msg;
							flag = true;
						}  
			          }  
			     });
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	function checkDept(){
		var flag = false;
		var deptName = $("#dept_name").val();
		if(deptName==""||deptName==null){
			//xy.validateTool.rules.checkDept.falseMessage="机构不能为空";
			$("#dept_name_msg").removeClass("li4");
			$("#dept_name_msg").addClass("li3");
			$("#dept_name_msg").text("机构不能为空");
		}else{
			$.ajax({  
		        type : "post",  
		        url : "/checkDeptName.do",  
		        data : {"deptName":deptName},  
		        async : false,  
		        success : function(result){  
		        	result = eval("(" + result + ")"); 
		            /* if(result.success==false){
						xy.validateTool.rules.checkDept.falseMessage=result.msg;
					}else{
						$("#dept_id").val(result.backParam);
						//机构填写正确后选择班级
						classCombo(".classCombo",$("#dept_id").val());
						xy.validateTool.rules.checkDept.trueMessage=result.msg;
						flag = true;
					}   */
		        	if(result.success==true){//与注册机构相反
						$("#dept_name_msg").removeClass("li4");
						$("#dept_name_msg").addClass("li3");
						$("#dept_name_msg").text("该机构名称尚未注册");
					}else{
						$("#dept_name_msg").removeClass("li3");
						$("#dept_name_msg").addClass("li4");
						//机构填写正确后选择班级
						$("#dept_id").val(result.backParam);
						classCombo("#class_id",$("#dept_id").val());
						$("#dept_name_msg").text("该机构可以使用");
						flag =  true;
					}
		          }  
		     });
		}
		return flag;
	}
	
	function checkClass(){
		var flag = false;
		var deptName = $("#dept_name").val();
		var className = "";
		if(deptName==""||deptName==null){
			className = $("#class_id").val();
		}else{
			className = $("#class_id").combobox("getText");
		}
		if(className==""||className==null){
			xy.validateTool.rules.checkClass.falseMessage="班级不能为空";
		}else{
			$.ajax({  
		        type : "post",  
		        url : "/queryClassObject.do",  
		        data : {"className":className},  
		        async : false,  
		        success : function(result){  
		        	result = eval("(" + result + ")"); 
		            if(result.success==false){
						xy.validateTool.rules.checkClass.falseMessage=result.msg;
					}else{
						xy.validateTool.rules.checkClass.trueMessage=result.msg;
						flag = true;
					}  
		          }  
		     });
		}
		return flag;
	}
	
	function checkMobile(){
		var flag = false;
		var userName = $("#user_name").val();
		var mobile = $("#mobile").val();
		
		$.ajax({  
		        type : "post",  
		        url : "/checkUser.do",  
		        data : {"userName":userName,"mobile":mobile},  
		        async : false,  
		        success : function(result){  
		        	result = eval("(" + result + ")"); 
		            if(result.success==false){
						xy.validateTool.rules.checkMobile.falseMessage=result.msg;
					}else{
						xy.validateTool.rules.checkMobile.trueMessage=result.msg;
						flag = true;
					}  
		          }  
		     });
		return flag;
	}
	
	function checkSchoolName(){
		var flag = false;
		var schoolName = $("#school_name").val();
		$("#school_name").text("");
		var roleId = $("input[name='roleId']:checked").val();
		if(roleId!=3){
			if(schoolName==""){
				xy.validateTool.rules.checkSchoolName.falseMessage="请输入学校名称";
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	function checkPasswd(){
		var flag = false;
		var password = $("#password").val();
		$("#password_msg").text("");
		if(password==""){
			xy.validateTool.rules.checkPasswd.falseMessage="请输入您的密码";
		}else if(password.length<6){
			xy.validateTool.rules.checkPasswd.falseMessage="密码的长度不要少于6位";
		}else{
			flag = true;
		}
		return flag;
	}
	
	function checkRePasswd(){
		var flag = false;
		var password = $("#password").val();
		var repassword = $("#repassword").val();
		$("#repassword_msg").text("");
		if(repassword==""){
			xy.validateTool.rules.checkRePasswd.falseMessage="请再次输入您的密码";
		}else if(password!=repassword){
			xy.validateTool.rules.checkRePasswd.falseMessage="两次输入的密码不同，请确认！";
		}else{
			flag = true;
		}
		return flag;
	}
	
	function hideUL(value){
		//家长
		if(value==4){
			$("#student_ul").css("display","block");
		}else{
			$("#student_ul").css("display","none");
		}
		
		//老师
		if(value==3){
			$("#school_ul").css("display","none");
		}else if(value!=2){
			$("#school_ul").css("display","block");
		}
	}
	
	function doSubmitUser(){
		var flag = true;
		var deptName = $("#dept_name").val();
		if(deptName==""||deptName==null){
			$("#dept_name_msg").text("机构不能为空");
			flag = false;
		}
		if(flag){
			fmSubmitXY("#userForm","/saveUser.do");
		}
	}
	
	function checkDeptName(){
		var flag = false;
		var deptName = $("#dept_name_add").val();
		$.ajax({  
	        type : "post",  
	        url : "/checkDeptName.do",  
	        data : {"deptName":deptName},  
	        async : false,  
	        success : function(result){  
	        	result = eval("(" + result + ")"); 
	            if(result.success==false){
					xy.validateTool.rules.checkDeptName.falseMessage=result.msg;
				}else{
					xy.validateTool.rules.checkDeptName.trueMessage=result.msg;
					flag = true;
				}  
	          }  
	     });
		return flag;
	}
	
	function checkDeptMobile(){
		var flag = false;
		var deptMobile = $("#dept_mobile").val();
		$.ajax({  
	        type : "post",  
	        url : "/checkDeptMobile.do",  
	        data : {"deptMobile":deptMobile},  
	        async : false,  
	        success : function(result){  
	        	result = eval("(" + result + ")"); 
	            if(result.success==false){
					xy.validateTool.rules.checkDeptMobile.falseMessage=result.msg;
				}else{
					flag = true;
				}  
	          }  
	     });
		return flag;
	}
	
	function checkDeptEmail(){
		var flag = false;
		var deptEmail = $("#dept_email").val();
		$.ajax({  
	        type : "post",  
	        url : "/checkDeptEmail.do",  
	        data : {"deptEmail":deptEmail},  
	        async : false,  
	        success : function(result){  
	        	result = eval("(" + result + ")"); 
	            if(result.success==false){
					xy.validateTool.rules.checkDeptEmail.falseMessage=result.msg;
				}else{
					flag = true;
				}  
	          }  
	     });
		return flag;
	}
	
	function checkDeptPasswd(){
		var flag = false;
		var password = $("#dept_password").val();
		$("#dept_password_msg").text("");
		if(password==""){
			xy.validateTool.rules.checkDeptPasswd.falseMessage="请输入您的密码";
		}else if(password.length<6){
			xy.validateTool.rules.checkDeptPasswd.falseMessage="密码的长度不要少于6位";
		}else{
			flag = true;
		}
		return flag;
	}
	
	function checkDeptRePasswd(){
		var flag = false;
		var password = $("#dept_password").val();
		var repassword = $("#dept_repassword").val();
		$("#dept_repassword_msg").text("");
		if(repassword==""){
			xy.validateTool.rules.checkDeptRePasswd.falseMessage="请再次输入您的密码";
		}else if(password!=repassword){
			xy.validateTool.rules.checkDeptRePasswd.falseMessage="两次输入的密码不同，请确认！";
		}else{
			flag = true;
		}
		return flag;
	}
	
	function doSubmitDept(){
		fmSubmitXY("#deptForm","/addDept.do");
	}
</script>
</head>

<body>
	<div id="TopBanner">
  		<div class="content"></div>
	</div>
	<div id="MidBody">
  		<div class="topSeparator"></div>
  		<div class="top">
    		<ul>
      			<li id="userTab" tab-body="user" class="tab choosed">用户注册</li>
      			<li id="deptTab" tab-body="dept"  class="tab" >机构注册</li>
    		</ul>
  		</div>
  		<!-- 用户注册 -->
  		<div id="user" class="tab-body">
  			<form id="userForm" method="post">
		      <div id="content1">
		        <ul>
		          <li class="li1">用 户 名：</li>
		          <li class="li2">
		            <input type="text" id="user_name" name="userName" class="validation" validator-options="{'type':'loginName'}"/> 
		          </li>
		          <li class="li5">*</li>
		          <li class="li4"><span id="user_name_msg">用户名即是真实姓名</span></li>
		        </ul>
		        <ul>
		          <li class="li1">手机号码：</li>
		          <li class="li2">
		            <input type="text" id="mobile" name="mobile" class="validation" validator-options="{'type':'checkMobile'}" />
		           </li>
		           <li class="li5"> *</li>
		          <li class="li3"><span id="mobile_msg"></span></li>
		        </ul>
		         <ul>
		          <li class="li1">性别：</li>
		          <li class="li2">
		            <input type="radio" name="sex" checked="checked" value="0" style="border:0px; width:12px; height:15px;" />男&nbsp;&nbsp;&nbsp;&nbsp;
		            <input type="radio" name="sex" value="1" style="border:0px; width:12px; height:15px;"/>女&nbsp;&nbsp; 
		          </li>
		          <li class="li5"> *</li>
		        </ul>
		       <!--  <ul>
		          <li class="li1">QQ号码：</li>
		          <li class="li2">class="validation" validator-options="{'type':'verifyQQ'}"
		            <input type="text" id="qq" name="qq" />
		          </li>
		          <li class="li3"> <span id="qq_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">邮箱地址：</li>
		          <li class="li2">  class="validation" validator-options="{'type':'verifyEmail'}"
		            <input type="text" id="email" name="email"/>
		          </li>
		          <li class="li3"> <span id="email_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">微信号：</li>
		          <li class="li2">
		            <input type="text" id="weixin" name="weixin" />
		          </li>
		          <li class="li3"> <span id="weixin_msg"></span></li>
		        </ul> -->
		        <ul>
		          <li class="li1">用户角色：</li>
		          <li class="li2">
		            <input type="radio" name="roleId" value="5" checked="checked"  style="border:0px; width:12px; height:15px;" onclick="hideUL('5')"/>学生&nbsp;&nbsp; 
		            <input type="radio" name="roleId" value="3" style="border:0px; width:12px; height:15px;" onclick="hideUL('3')" />老师&nbsp;&nbsp;
		            <input type="radio" name="roleId" value="4" style="border:0px; width:12px; height:15px;" onclick="hideUL('4')"/>家长&nbsp;&nbsp;
		          </li>
		          <li class="li5"> *</li>
		        </ul>
		        <ul id="student_ul" style="display:none">
		          <li class="li1">学生姓名：</li>
		          <li class="li2">
		            <input type="text" id="stu_name" name="stuName" class="validation" validator-options="{'type':'checkStudent'}"/>
		            <input type="hidden" id="stu_id" name="stuId">
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="stu_name_msg"></span></li>
		        </ul>
		        <ul id="dept_ul">
		          <li class="li1">机构名称：</li>
		          <li class="li2"><!-- class="validation" validator-options="{'type':'checkDept'}" -->
		            <input type="text" id="dept_name" onchange="checkDept()"/>
		            <input type="hidden" id="dept_id" name="deptId" value="">
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="dept_name_msg"></span></li>
		        </ul>
		         <ul id="class_ul">
		          <li class="li1">班级：</li>
		          <li class="li2">
		            <input type="text" id="class_id" name="classId" class="validation" validator-options="{'type':'checkClass'}" />
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="class_id_msg"></span></li>
		        </ul>
		        <ul id="school_ul">
		          <li class="li1">学校名称：</li>
		          <li class="li2">
		            <input type="text" id="school_name" name="schoolName" class="validation" validator-options="{'type':'checkSchoolName'}" />
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="school_name_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">密 码：</li>
		          <li class="li2">
		            <input type="password" id="password" name="password" class="validation" validator-options="{'type':'checkPasswd'}" />
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="password_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">确认密码：</li>
		          <li class="li2">
		            <input type="password" id="repassword" name="repassword" class="validation" validator-options="{'type':'checkRePasswd'}"/>
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="repassword_msg"></span></li>
		        </ul>
     			<div id="content3"> 
      				<input type="button" id="dept_btn" style="width:102px; height:52px;cursor:pointer; background-image:url(/images/register_btn.jpg);" onclick="doSubmitUser();" /></div>
		        </div>
    		</form>
    	</div>
    	
    	<!-- 机构注册 -->
    	<div id="dept" class="tab-body" style="display:none">
  		<form id="deptForm" method="post">
		      <div id="content1">
		        <ul>
		          <li class="li1">机构名称：</li>
		          <li class="li2">
		            <input type="text" id="dept_name_add" name="deptName" class="validation" validator-options="{'type':'checkDeptName'}"/> 
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"><span id="dept_name_add_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">机构邮箱：</li>
		          <li class="li2">
		            <input type="text" id="dept_email" name="deptEmail" class="validation" validator-options="{'type':'checkDeptEmail'}"/>
		           </li>
		           <li class="li5"> *</li>
		          <li class="li3"> <span id="dept_email_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">机构电话：</li>
		          <li class="li2">
		            <input type="text" id="dept_mobile" name="deptMobile" class="validation" validator-options="{'type':'checkDeptMobile'}" />
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="dept_mobile_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">密 码：</li>
		          <li class="li2">
		            <input type="password" id="dept_password" name="password" class="validation" validator-options="{'type':'checkDeptPasswd'}" />
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="dept_password_msg"></span></li>
		        </ul>
		        <ul>
		          <li class="li1">确认密码：</li>
		          <li class="li2">
		            <input type="password" id="dept_repassword" name="repassword" class="validation" validator-options="{'type':'checkDeptRePasswd'}"/>
		          </li>
		          <li class="li5"> *</li>
		          <li class="li3"> <span id="dept_repassword_msg"></span></li>
		        </ul>
     			<div id="content3"> 
      				<input type="button" id="dept_btn" style="width:102px; height:52px;cursor:pointer; background-image:url(/images/register_btn.jpg);" onclick="doSubmitDept()" /></div>
		        </div>
    		</form>
    	</div>
  		
	</div>
</body>
</html>