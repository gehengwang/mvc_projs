<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目正确率</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	
	var query_url="/questionCorrect.do";
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
	}
	
	function studentDetail(value,row,index){
		if(row.answerRate!='-- 未作答 --'){
			return "<a href='javascript:openWin("+row.lessonId+","+row.questionNo+")' style='color:#307DCA'>"+row.question+"</a>";
		}else{
			return value;
		}
		
	}
	
	function onClickRow(index,row){
		openWin(row.lessonId,row.questionNo);
	}
	
	function openWin(lessonId,questionNo){
		$('#win').window('open');
		$("#winData").datagrid("options").url="/answersByQuestionId.do?lessonId="+lessonId+"&questionNo="+questionNo;
		$("#winData").datagrid("load");
	}
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				data-options="onSelect:function(){doQuery();}"/>&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lessonId" class="lessonGrid" style="width:180px"
					data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				<!-- <a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a> -->
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,toolbar:'#toolbar',
		 pageSize:20,url:query_url,onClickRow:onClickRow ">
			<thead>
				<tr>
					<th data-options="field:'className',align:'center',width:100">班级</th>
					<th data-options="field:'techNameUse',align:'center',width:100">老师</th>
					<th data-options="field:'lessonName',align:'center',width:260">课程</th>
					<th data-options="field:'question',align:'center',width:260,formatter:studentDetail">题目</th>
					<th data-options="field:'answerRate',align:'center',width:120">正确率</th>
				</tr>
			</thead>
	</table>
	<div id="win" class="easyui-window" title="学生答题信息" style="width:600px;height:50%"
        data-options="iconCls:'icon-save',modal:true,closed:true">
   		<table id="winData" class="easyui-datagrid" data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true ">
			<thead>
				<tr>
					<th data-options="field:'stuName',align:'center',width:100">学生</th>
					<th data-options="field:'stuAnswer',align:'center',width:360">答案详情</th>
					<th data-options="field:'answerResult',align:'center',width:80">答案对错</th>
				</tr>
			</thead>
	</table>
	</div>
</body>
</html>