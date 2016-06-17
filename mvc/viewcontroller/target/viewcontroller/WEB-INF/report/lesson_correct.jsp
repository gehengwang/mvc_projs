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
	var query_url="/lessonCorrect.do";
	$(function(){
		classCombo(".classCombo");
		techCombo(".techCombo");
		lessonGrid(".lessonGrid");
	});
	
	function doQuery(){
		var deptId = $("#dept_id").combobox("getValue");
		var techId = $("#tech_id").combobox("getValue");
		var lessonId = $("#lesson_id").combobox("getValue");
		var fmdata = {"deptId":deptId,"techId":techId,"lessonId":lessonId};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	}
	
	function quesDetail(value,row,index){
		return "<a href='javascript:openWin("+row.lessonId+")' style='color:#307DCA'>"+value+"</a>";
	}
	
	function onClickRow(index,row){
		openWin(row.lessonId);
	}
	
	function openWin(lessonId){
		$('#win').window('open');
		$("#winData").datagrid("options").url="/questionCorrect.do?lessonId="+lessonId;
		$("#winData").datagrid("load");
	}
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				data-options="onSelect:function(newValue){return techCombo('#tech_id',newValue.classId);}"/>&nbsp;&nbsp;&nbsp;
				老师:
				<input type="text" id="tech_id" name="techId" class="techCombo" />&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lesson_id" class="lessonGrid" />&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">重置</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,toolbar:'#toolbar',
		pageSize:20,url:query_url,onClickRow:onClickRow,nowrap:false ">
			<thead>
				<tr>
					<th data-options="field:'deptName',align:'center',width:100">机构</th>
					<th data-options="field:'techNameUse',align:'center',width:100">老师</th>
					<th data-options="field:'lessonName',align:'center',width:260,formatter:quesDetail">课程</th>
					<th data-options="field:'lessonSum',align:'center',width:100">总数</th>
					<th data-options="field:'lessonRight',align:'center',width:100">正确数</th>
					<th data-options="field:'lessonError',align:'center',width:100">错误数</th>
					<th data-options="field:'lessonRate',align:'center',width:100">正确率</th>
				</tr>
			</thead>
	</table>
	
	<div id="win" class="easyui-window" title="题目详情" style="width:600px;height:50%"
        data-options="iconCls:'icon-save',modal:true,closed:true">
   		<table id="winData" class="easyui-datagrid" data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true ">
			<thead>
				<tr>
					<th data-options="field:'question',align:'center',width:260">题目</th>
					<th data-options="field:'answerRate',align:'center',width:220">正确率</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>