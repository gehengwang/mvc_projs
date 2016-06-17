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
		var fmdata = {"classId":classId,"lessonId":lessonId};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	function formatLesson(value,row,index){
		if(row.answerStatus==1){
			return "<a href='/answerInit.do?lessonId="+row.lessonId+"&classId="+row.classId+"' style='color:#307DCA'>"+value+"</a>";
		}else{
			return value;
		}
	}
	function onClickRow(index,row){
		if(row.answerStatus==1){
			window.location.href = "/answerInit.do?lessonId="+row.lessonId+"&classId="+row.classId;
		}
	}
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				data-options="onSelect:function(newValue){doQuery();}"/>&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lesson_id" class="lessonGrid" style="width:180px" 
					   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">重置</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,singleSelect:true,toolbar:'#toolbar',
		pageSize:20,url:query_url,onClickRow:onClickRow ">
			<thead>
				<tr>
					<th data-options="field:'className',align:'center',width:100">班级</th>
					<th data-options="field:'techNameUse',align:'center',width:100">上课老师</th>
					<th data-options="field:'lessonName',align:'center',width:220,formatter:formatLesson">课程</th>
					<th data-options="field:'answerStatusName',align:'center',width:100">是否开放答案</th>
					<th data-options="field:'lessonStatus',align:'center',width:100">课程状态</th>
				</tr>
			</thead>
	</table>
</body>
</html>