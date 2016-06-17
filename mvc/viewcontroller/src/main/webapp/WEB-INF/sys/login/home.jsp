<!doctype html>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>欢迎登录</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
</head>
<script type="text/javascript">
window.onload = function(){
	$('#loading-mask').fadeOut();
	$.post("/queryMenus.do","",function(datas){
		_menus=datas;
		InitLeftMenu();
	},"json");
}
//菜单
var _menus = {};
//初始化左侧
function InitLeftMenu() {
	$("#nav").accordion({animate:false,fit:true,border:false});
	var selectedPanelname = '';
    $.each(_menus, function(i, n) {
		var menulist ='';
		menulist +='<ul class="navlist">';
        $.each(n.childMenu, function(j, o) {
			menulist += '<li><div ><a ref="'+o.menuId+'" href="#" rel="' + o.menuUrl + '" ><span class="nav">' + o.menuName + '</span></a></div></li> ';
        })
		menulist += '</ul>';
		$('#nav').accordion('add', {
            title: n.menuName,
            content: menulist,
			border:false,
        });
		if(i==1)
			selectedPanelname =n.menuName;
    });
	$('#nav').accordion('select',selectedPanelname);
	
	$('.navlist li a').click(function(){
		var tab = $('#tabs').tabs('getSelected');
		var tabTitle = $(this).children('.nav').text();
		var url = $(this).attr("rel");
		var menuId = $(this).attr("ref");
		$("#_iframe").attr("src",url);
		$('#tabs').tabs('update', {
			tab: tab,
			options: {
				title: tabTitle
					/* href:url  // the new content URL */
			}
		});
	});
}

//密码修改
function editPassword(){
	$("#editPassword").window("open");
}

function savePassword(){
	var newPass = $('#newPass').val();
    var reNewPass = $('#reNewPass').val();

    if (newPass == '') {
    	$.messager.alert("提示","请输入密码！");
        return false;
    }
    if (reNewPass == '') {
    	$.messager.alert('提示', '请再次输入密码！');
        return false;
    }

    if (newPass != reNewPass) {
    	$.messager.alert('提示', '两次密码不一至！请重新输入');
        return false;
    }

    $.post('/updatePassword.do',{"password":newPass}, function(result) {
    	if(result.success==true){
    		$.messager.show({title: "提示",msg: result.msg});
    		canclePassword();
    	}else{
    		$.messager.show({title: "提示",msg: result.msg});
    	}
    },"json");
}

function canclePassword(){
	$("#editPassword").window("close");
}

function loginOut(){
	 $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
         if (r) {
             location.href = '/login/loginOut.do';
         }
     });
}

online();
window.setInterval("online()", 6*1000);
function online(){
    $.ajax({
      type: "POST",
      url: "/online.do",
      success: function(result){
        $("#userCount").html(result.online);
        alert(result.remain_time);
        if(result.remain_time <= 0){
        	window.top.location.href = "/login/index.do";
        }
      },
      headers: {"noTimeCount" : "true"},
      dataType: "json"
   });
}
</script>
<!-- 页面布局 -->
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    		<img src="../../../images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>

	<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
		<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
   	 		<img src="../../../images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...
   		</div>
	</div>

    <!-- 北：页头 -->
    <div region="north" border="false" style="overflow:hidden; height:40px;
        background: url(../../../images/header.gif) #7f99be repeat-x center 80%;line-height: 40px;color: #fff;">
        <span style="padding-left:10px; font-size: 22px; ">电子课件云服务平台</span>
        <span style="float:right; padding-right:50px;" class="head">欢迎您：${sessionScope.USER_INFO.user.userName} 
        	&nbsp;&nbsp;&nbsp;&nbsp;在线人数：<span id="userCount"></span>
        	&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:yellow;text-decoration:none " href="#" onclick="editPassword();">修改密码</a> 
        	&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:yellow;text-decoration:none" href="#" onclick="loginOut();">安全退出</a>
        </span>
    </div>
    
    <!-- 西：菜单导航 -->
    <div region="west" split="true" title="菜单列表" style="width:135px;">
		<div id="nav"><!--  导航内容 --></div>
    </div>
    
    <!-- 首页 -->
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="欢迎使用" style="padding:8px;overflow:hidden;" >
				<iframe id="_iframe" style="width:100%;height:100%" frameborder="0"></iframe>
			</div>
		</div>
    </div>
    
     <!-- 南：页脚 -->
    <div region="south" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright ©飞行云2016</div>
    </div>
    
    <!--修改密码窗口-->
    <div id="editPassword" class="easyui-window" title="修改密码" closed="true" collapsible="false" minimizable="false" 
        maximizable="false" icon="icon-save" style="width:320px; height:180px; padding:5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="newPass" type="password" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="reNewPass" type="password" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a class="easyui-linkbutton" icon="icon-ok" onclick="savePassword();">确定</a> 
                <a class="easyui-linkbutton" icon="icon-cancel" onclick="canclePassword();">取消</a>
            </div>
        </div>
    </div>
</body>
</html>