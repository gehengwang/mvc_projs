<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	var query_url="/queryUserPage.do?flag="+${param.flag};
	
	$(function(){
		classCombo(".classCombo");
		techCombo(".techCombo");
		if('${sessionScope.USER_INFO.roles[0].roleId}'!=2&&'${sessionScope.USER_INFO.roles[0].roleId}'!=3){
			$("#download").css("display","none");
			$("#upload").css("display","none");
			$("#add_class").css("display","none");
			$("#user_status_1").css("display","none");
			$("#user_status_0").css("display","none");
			$("#reset_password").css("display","none");
		}
	});
	
	function doQuery(){
		var userName = $("#user_name").val();
		var classId = $("#class_id_query").combobox("getValue");
		var techId = $("#tech_id").combobox("getValue");
		var fmdata = {"userName":userName,"classId":classId,"techId":techId};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	
	function editUser(){
		var rows =  $("#data").datagrid("getSelections");
		var row =  $("#data").datagrid("getSelected");

		if (rows.length>0) {
			if (rows.length > 1) {
			    $.messager.alert("警告","每次只能修改一条记录");
				return;
			}
			$("#dlg").dialog("open").dialog("setTitle", "修改");
			$("#dlgFm").form("load", row);
		}else{
			 $.messager.alert("警告","请选择要修改的数据");
			 return;
		}
	}
	
	function updateUser(){
		var row =  $("#data").datagrid("getSelected");
		var datafm = {"userId":row.userId,"mobile":$("#mobile").val(),
				"qq":$("#qq").val(), "email":$("#email").val()};
		$.post("/updateUser.do",datafm,function(result){
		        if (result.success){
		            $("#dlg").dialog("close");
					$("#data").datagrid("reload");
		            $.messager.show({title: "成功",msg: result.msg});
		        }else $.messager.show({title: "错误",msg: result.msg}); 
		},"json");
	}
	
	function resetPassword(){
		var rows = $("#data").datagrid("getSelections");

		if (rows.length > 0) {
			$.messager.confirm("确认", "是否要重置密码?", function(r) {
				if (r) {
					var userIdArray = [];
					for ( var i = 0; i < rows.length; i++) {
						userIdArray.push(rows[i].userId);
					}
					$.post("/updatePassword.do",{"userIds":userIdArray.toString(),"password":"123456"},function(result){
						if (result.success) {
							$("#data").datagrid("reload");
							$.messager.show({title: "成功",msg: result.msg});
						} else {
							$.messager.show({title: "错误",msg: result.msg}); 
						}
					},"json");
				}
			});
		} else {
			$.messager.alert("警告","请选择要重置的用户!");
			return;
		}
	}
	
	function updateUserStatus(value){
		var rows = $("#data").datagrid("getSelections");

		if (rows.length > 0) {
			var userIdArray = [];
			for ( var i = 0; i < rows.length; i++) {
				userIdArray.push(rows[i].userId);
			}
			var data = {"userIds":userIdArray.toString(),"userStatus":value};
			var msg = "";
			if(value==0){
				msg = "停用用户"
			}else{
				msg = "启用用户";
			}
			$.messager.confirm("确认", "是否要"+msg+"?", function(r) {
				if(r){
					$.post('/updateUserStatus.do',data,function(result){
						if(result.success==true){
							$("#data").datagrid("reload");
							$.messager.show({title: "提示",msg: result.msg});
						}else{
							$.messager.show({title: "错误",msg: result.msg});
						}
					},"json");
				}
			});
		} else {
			$.messager.alert("警告","请选择用户!");
			return;
		}
	}
	
	function classDetail(value,row,index){
		return "<a href='javascript:openClassWin("+row.userId+")' style='color:#307DCA'>"+value+"</a>";
	}

	function openClassWin(userId){
		$('#win_class').window('open');
		$("#class_detail").datagrid("options").url="/queryClassPage.do?userId="+userId;
		$("#user_id").val(userId);
		$('#class_detail').datagrid({"onLoadSuccess":function(dataExists){}});
	}
	
	function addUserClass(){
    	var row =  $("#data").datagrid("getSelected");
		if (row) {
			$("#class_detail").datagrid("options").url="/queryClassPage.do?userId="+row.userId;
			$('#class_detail').datagrid({"onLoadSuccess":function(dataExists){
				$("#class_choose").datagrid({"onLoadSuccess":function(dataAll){
					$("#dlg_class").dialog("open");
					
					for(var i=0;i<dataExists.rows.length;i++){
						for(var j=0;j<dataAll.rows.length;j++){
							if(dataAll.rows[j].classId==dataExists.rows[i].classId){
								$("#class_choose").datagrid('selectRow',j);
							}
						}
					}
				  }
			  });
			}});
		}else{
			$.messager.alert("警告","请先选择用户");
			return;
		}
	}
	
	function saveUserBind(){
		 var rowsUser =  $("#data").datagrid("getSelections");
		 var rowsClass =  $("#class_choose").datagrid("getSelections");
		 
		 var userIdArray = [];
		 for ( var i = 0; i < rowsUser.length; i++) {
			userIdArray.push(rowsUser[i].userId);
		 }
		 var classIdArray = [];
		 if(rowsClass.length==0){
			 $.messager.alert("警告","请至少选择一个班级!");
			 return;
		 }else{
			 for ( var i = 0; i < rowsClass.length; i++) {
				 classIdArray.push(rowsClass[i].classId);
			 }
		 }
		 
		 var fmdata = {"userIds":userIdArray.toString(),"classIds":classIdArray.toString(),"roleId":'5'};
		 $.post("/saveUserBind.do",fmdata,function(result){
			 if(result.success==true){
				 $("#dlg_class").dialog("close");
				 $("#data").datagrid("reload");
				 $.messager.show({title: "提示",msg: result.msg});
			 }else{
				 $.messager.show({title: "错误",msg: result.msg});
			 }
		 },"json");
	}
	
	function uploadUsers(){
		$("#importDlg").dialog("open");
		$("#importFm").form("clear")
	}
	
	function doImport(){
		 //得到上传文件的全路径  
       if ($("#importFm").form("validate") == false)
			return;
		var fileName= $('#file').filebox('getValue');
       //对文件格式进行校验  
       var d1=/\.[^\.]+$/.exec(fileName);
       if(d1==".xls"||d1==".xlsx"){
           fmSubmit("#importFm","/importUsers.do?roleId=5","#importBtn",function(){
       		$("#data").datagrid("load");
       	});
       }else{
            $.messager.alert('提示','请选择xls格式文件！','info');   
            $('#file').filebox('setValue',''); 
        }
	}
	
	function doExport(){
		var url="/userExport.do?flag=0&classId="+$("#class_id_query").combobox("getValue")+
				"&techId="+$("#tech_id").combobox("getValue")+"&userName="+$("#user_name").val();
	    window.location.href=url;
	}
	
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id_query" name="classId" class="classCombo" 
				 	   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				老师:
				<input type="text" id="tech_id" name="techId" class="techCombo"  
					   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				用户名:
				<input type="text" id="user_name" name="user_name" class="easyui-validatebox"/>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
				<br></hr>
				<a id="download" href="/files/用户信息模板.xls" class="easyui-linkbutton" style='color:#0000FF'
					data-options="plain:true,iconCls:'icon-download'" >用户信息模板下载</a>
				&nbsp;&nbsp;&nbsp;
				<a id="upload" href="###" class="easyui-linkbutton" 
					data-options="plain:true,iconCls:'icon-upload'" onclick="uploadUsers()">批量导入</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-excel'" onclick="doExport()">导出</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-edit'" onclick="editUser()">信息修改</a>
				&nbsp;&nbsp;&nbsp;
				<a id="add_class" href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-gears'" onclick="addUserClass()">学生配班</a>&nbsp;&nbsp;&nbsp;
				<a id="user_status_1" href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-ok'" onclick="updateUserStatus(1)">启用</a>
				&nbsp;&nbsp;&nbsp;
				<a id="user_status_0" href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-cancel'" onclick="updateUserStatus(0)">停用</a>
				&nbsp;&nbsp;&nbsp;
				<a id="reset_password"href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-redo'" onclick="resetPassword()">密码重置</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,rownumbers:true,pagination:true,toolbar:'#toolbar',
		pageSize:20,url:query_url,nowrap:false,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true' "></th>
					<th data-options="field:'userName',align:'center',width:80">用户名</th>
					<th data-options="field:'mobile',align:'center',width:120">手机号码</th>
					<th data-options="field:'sexName',align:'center',width:80">性别</th>
					<th data-options="field:'qq',align:'center',width:100">QQ</th>
					<th data-options="field:'email',align:'center',width:150">邮箱</th>
					<th data-options="field:'weixin',align:'center',width:100">微信</th>
					<th data-options="field:'schoolName',align:'center',width:120">学校名称</th>
					<th data-options="field:'userStatusName',align:'center',width:80">用户状态</th>
					<th data-options="field:'className',align:'center',width:120,formatter:classDetail">班级详情</th>
				</tr>
			</thead>
	</table>
	
	<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'用户信息',
				buttons:[
				{ text:'确定',iconCls:'icon-ok', handler:function(){updateUser();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 360px; height: 260px; padding: 20px 20px;">

			<form id="dlgFm" method="post" >
				<table cellpadding="5">
                <tr>
                    <td>手机号码:</td>
                    <td><input type="text" name="mobile" id="mobile"/></input></td>
                </tr>
                <tr>
                    <td>QQ:</td>
                    <td><input type="text" name="qq" id="qq" class="easyui-validatebox" 
						data-options="required:true,validType:'qq'"/></input></td>
                </tr>
                 <tr>
                    <td>email:</td>
                    <td><input type="text" name="email" id="email" class="easyui-validatebox" 
						data-options="required:true,validType:'email'"/></input></td>
                </tr>
           		</table>
			</form>
	</div>
	
	<!-- 班级详情 -->
	<div id="win_class" class="easyui-window" title="班级详情" style="width: 620px; height:80%;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
   		<input type="hidden" id="user_id"/>
   		<table id="class_detail" class="easyui-datagrid" data-options="fit:true,pagination:true">
			<thead>
				<tr>
					<th data-options="field:'classId',align:'center',width:100">班级序号</th>
					<th data-options="field:'className',align:'center',width:150">班级名称</th>
					<th data-options="field:'classStatusName',align:'center',width:120">班级状态</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<!-- 班级选择 -->
	<div id="dlg_class" class="easyui-dialog"
			data-options="iconCls:'icon-gears',closed:true,resizable:true,modal:true,title:'班级配置',
				buttons:[
				{ text:'保存',iconCls:'icon-upload',	handler:function(){saveUserBind();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg_class').dialog('close');}  }]"
			style="width: 620px; height:80%;">

				<table id="class_choose" class="easyui-datagrid"
   					   data-options="fit:true,url:'/queryClassesList.do'">
				<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true'"></th>
					<th data-options="field:'classId',align:'center',width:80">班级序号</th>
					<th data-options="field:'className',align:'center',width:180">班级名称</th>
					<th data-options="field:'classStatusName',align:'center',width:100">班级状态</th>
				</tr>
				</thead>
				</table>
	</div>
	
	<!-- 用户信息导入 -->
	<div id="importDlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'批量导入用户',
				buttons:[
				{ text:'上传',iconCls:'icon-upload',	  id:'importBtn',  handler:function(){doImport();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#importDlg').dialog('close');}  }]"
			style="width: 500px; height: 260px; padding: 20px 20px;">

			<form id="importFm" method="post" enctype="multipart/form-data">
				<table cellpadding="5">
                <tr>
                    <td>上传文件:</td>
                    <td><input class="easyui-filebox" style="width:340px" name="file" id="file"
					data-options="required:true,buttonText:'选择Excel',buttonIcon:'icon-excel', prompt:'选择一个文件...' "></td>
                </tr>
           		</table>
			</form>
	</div>
</body>
</html>