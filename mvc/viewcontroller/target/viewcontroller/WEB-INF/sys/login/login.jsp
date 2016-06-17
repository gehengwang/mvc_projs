<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<title>登录</title>
<script type="text/javascript">

function validateForm(){
	var flag=true;
    if($("#user_name").val()==""){
       	$("#errors").html("<font color='red'>用户名不能为空</font>");
      	flag=false;
    }else if($("#mobile").val()==""){
    	$("#errors").html("<font color='red'>手机号码不能为空</font>");
    	flag=false;
     }else if($("#password").val()==""){
    	$("#errors").html("<font color='red'>密码不能为空</font>");
    	flag=false;
    }
    return flag;
 }

function getKey(event)  
{  
    if(event.keyCode==13){ 
		if ((navigator.userAgent.indexOf('MSIE') >= 0) 
			    && (navigator.userAgent.indexOf('Opera') < 0)){
			doSubmit();
		}else{
			doSubmit();
		}
	} 
}

function login(){
	$("#fm").form("submit",{
    	type:'post',
    	url:  "/login/login.do" ,
    	onSubmit: function(){
			var flag=validateForm();
				return flag;
			},
    	success: function(rs){
				var result = eval("("+rs+")");
				if (result.success){
					window.location.href="/login/home.do"; 
				} else {
					$("#errors").html(result.msg);
				}
		}
	});
}
</script>
</head>
<body>
	<div class="dl">
	<div class="logo">
	</div>
	<div class="dlk">
		<div class="dlk_l"><img src="/images/logo.png" ></div>
		<div class="dlk_r">
		<form id="fm" class="login-form" method="post" action="/login/login.do" onsubmit="return validateForm();">
        <div>
        	<input type="text" name="userName" id="user_name" value="${user_name}" placeholder="用户名">
        </div>
        <div style="margin-top:10px;">
        	<input type="text" name="mobile" id="mobile" value="${mobile}" placeholder="手机号码">
        </div>
        <div style="margin-top:10px;">
        	<input type="password" name="password" id="password" value="${password}" placeholder="密码">
        </div style="margin-top:40px;">
        <div class="login-btn">
        	<button type="submit" >登录</button>
        	<button type="button" onClick="javascript:window.location.href='/login/register.do'">注册</button>
        </div>
        <div id="errors"><font color='red'>${errors}</font></div>
      </form>
      </div>
	</div>
	<div class="footer">
		<p>系统建议使用IE8以上浏览器或Chrome、Firefox进行浏览</p>
		<p>Copyright ©飞行云2016</p>
	</div>
  </div>
</body>
</html>