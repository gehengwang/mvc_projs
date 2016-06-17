<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目完成情况</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	var query_url="/questionComplete.do";
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
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
					data-options="onSelect:function(newValue){doQuery();}"/>&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lesson_id" class="lessonGrid" style="width:220px" 
					data-options="onSelect:function(newValue){doQuery();}" />&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">重置</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,toolbar:'#toolbar',
		pageSize:20,url:query_url ">
			<thead>
				<tr>
					<th data-options="field:'className',align:'center',width:100">班级</th>
					<th data-options="field:'techNameUse',align:'center',width:100">上课老师</th>
					<th data-options="field:'lessonName',align:'center',width:260">课程</th>
					<th data-options="field:'stuName',align:'center',width:100">学生</th>
					<th data-options="field:'quesSum',align:'center',width:100">题目数</th>
					<th data-options="field:'quesComp',align:'center',width:100">完成数</th>
					<th data-options="field:'quesUndo',align:'center',width:100">未完成数</th>
					<th data-options="field:'quesCompRate',align:'center',width:100">完成率</th>
				</tr>
			</thead>
	</table>
</body>
</html>