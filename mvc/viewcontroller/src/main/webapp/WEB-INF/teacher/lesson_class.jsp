<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程列表</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	var query_url="/classLessonPage.do";
	$(function(){
		classCombo(".classCombo");
		lessonGrid(".lessonGrid");
	});
	
	function doQuery(){
		var classId = $("#class_id").combobox("getValue");
		var lessonId = $("#lesson_id").combobox("getValue");
		var fmdata = {"lessonId":lessonId,"classId":classId};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	function updateQuestion(value){
		var row =  $("#data").datagrid("getSelected");
		if(row){
			var data = {"lessonId":row.lessonId,"classId":row.classId,"questionStatus":value,
					"answerStatus":row.answerStatus};
			var msg = "";
			if(value==1){
				msg = "开放答题"
			}else{
				msg = "关闭答题";
			}
			$.messager.confirm("确认", "是否要"+msg+"?", function(r) {
				if(r){
					$.post('/updateClassLessonStatus.do',data,function(result){
						if(result.success==true){
							$("#data").datagrid("reload");
							$.messager.show({title: "提示",msg: result.msg});
						}else{
							$.messager.show({title: "错误",msg: result.msg});
						}
					},"json");
				}
			});
		}else{
			 $.messager.alert("警告","请选择班级课件","info");
				return false;
		}
	}
	
	function updateAnswer(value){
		var row =  $("#data").datagrid("getSelected");
		if(row){
			var data = {"lessonId":row.lessonId,"classId":row.classId,"questionStatus":row.questionStatus,
					"answerStatus":value};
			var msg = "";
			if(value==1){
				msg = "开放答案"
			}else{
				msg = "关闭答案";
			}
			$.messager.confirm("确认", "是否要"+msg+"?", function(r) {
				if(r){
					$.post('/updateClassLessonStatus.do',data,function(result){
						if(result.success==true){
							$("#data").datagrid("reload");
							$.messager.show({title: "提示",msg: result.msg});
						}else{
							$.messager.show({title: "错误",msg: result.msg});
						}
					},"json");
				}
			});
		}else{
			 $.messager.alert("警告","请选择班级课件","info");
				return false;
		}
	}
	
	function formatTime(value,row,index){
		var date = new Date(value);
		var format = formatDate(date);
		return format;
	}
	
	function openWin(){
		var row =  $("#data").datagrid("getSelected");
		if(row){
			var url = "/questionControl.do?lessonId="+row.lessonId+"&classId="+row.classId
			var newWindow = window.open(url,"","fullscreen=1"); 
			newWindow.moveTo(0,0); 
			newWindow.resizeTo(screen.width+20,screen.height); 
			newWindow.focus();
			newWindow.location=url; 
		}else{
			 $.messager.alert("警告","请选择班级课件","info");
			 return false;
		}
	}
	
	function dateformate(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+""+(m<10?('0'+m):m)+""+(d<10?('0'+d):d);
	}
	
	function addAnserSnap(){
		var rows =  $("#data").datagrid("getSelections");
		if (rows.length>0) {
			if(rows.length>1){
				$.messager.alert("警告","每次只能选择一条记录");
				return;
			}
			var date = dateformate(new Date());
			var snapName = rows[0].className+date+rows[0].lessonName;
			$("#snap_name").val(snapName);
			$("#dlg").dialog("open");
		}else{
			$.messager.alert("警告","请先选择一条记录");
			return;
		}
	}
	
	function saveSnap(){
		var rows =  $("#data").datagrid("getSelections");
		var data = {"lessonId":rows[0].lessonId,"classId":rows[0].classId,"snapName":$("#snap_name").val()};
		
		$.post('/insertAnserSnap.do',data,function(result){
			if(result.success==true){
				$("#dlg").dialog("close");
				$("#data").datagrid("reload");
				$.messager.show({title: "提示",msg: result.msg});
			}else{
				$.messager.show({title: "提示",msg: result.msg});
			}
		},"json");
	}
	
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				 	   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lessonId" class="lessonGrid" style="width:180px" 
					   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
				
				<br><hr>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-redo'" onclick="updateQuestion(1)">开放答题</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-edit'" onclick="updateQuestion(0)">关闭答题</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-redo'" onclick="updateAnswer(1)">开放答案</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-edit'" onclick="updateAnswer(0)">关闭答案</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-upload'" onclick="addAnserSnap()">保存快照</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-redo'" onclick="openWin()">投影</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,toolbar:'#toolbar',
		pageSize:20,url:query_url,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'className',align:'center',width:120">班级</th>
					<th data-options="field:'techNameUse',align:'center',width:120">上课老师</th>
					<th data-options="field:'lessonName',align:'center',width:260">课程</th>
					<th data-options="field:'questionStatusName',align:'center',width:100">开放答题</th>
					<th data-options="field:'answerStatusName',align:'center',width:100">开放答案</th>
					<th data-options="field:'lessonStatus',align:'center',width:100">课程状态</th>
				</tr>
			</thead>
	</table>
	<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'快照名称',
				buttons:[
				{ text:'确定',iconCls:'icon-ok', handler:function(){saveSnap();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 560px; height:180px; padding: 20px 20px;">

			<form id="dlgFm" method="post" >
				<table cellpadding="5">
                <tr>
                    <td>快照名称:</td>
                    <td><input type="text" name="snapName" id="snap_name" class="easyui-validatebox" 
                    	data-options="required:true" style="width:360px"/></input></td>
                </tr>
           		</table>
			</form>
	</div>
</body>
</html>