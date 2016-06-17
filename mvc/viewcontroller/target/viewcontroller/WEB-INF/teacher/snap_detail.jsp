<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史答案查询</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var query_url="/querySnapDetail.do?snapName=${param.snapName}";
	
	$(function(){
		$("#snap_name").val('${param.snapName}');
	});
	
	function doQuery(){
		var question = $("#question").val();
		var stuName = $("#stu_name").val();
		var fmdata = {"question":question,"stuName":stuName};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	function formatResult(value,row,index){
		if(row.answerResult == 0){
			return "<font color='red'>"+value+"</font>";
		}else if(row.answerResult == 1){
			return value;
		}else{
			return "<font color='blue'>"+value+"</font>";
		}
	}
	
	//答案错误位置标红显示
	function formatAnswer(value,row,index){
		var regx = /\n/g;
		if(row.answerResult==0){
			var errorFlagShow = row.errorFlagShow;
			var stuAnswer = row.stuAnswer;
			var fontStart = "<font color='red'>";//标注颜色
			var fontEnd = "</font>";
			for(var i=0;i<errorFlagShow.length;i++){
   				var flag0 = errorFlagShow[i][0];
   				var flag1 = errorFlagShow[i][1];
   				//按照指定位置截取字符串，并加上标注的html字符串
   				stuAnswer = stuAnswer.substring(0,flag0)
   						+fontStart
   						+stuAnswer.substring(flag0,flag1+1)
   						+fontEnd
   						+stuAnswer.substring(flag1+1)
   						;
   			}
			return stuAnswer.replace(regx,"<br>");
		}else{
			return value.replace(regx,"<br>");
		}
	}
	
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				<a href="/querySnapInit.do" class="easyui-linkbutton" 
					data-options="plain:true,iconCls:'icon-back'" >返回</a>&nbsp;&nbsp;&nbsp;
				<input type="hidden" name="snapName" id="snap_name"/>
				题目:
				<input type="text" id="question" name="question" class="easyui-validatebox"/>
				&nbsp;&nbsp;&nbsp;
				学生:
				<input type="text" id="stu_name" name="stuName" class="easyui-validatebox"/>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,singleSelect:true,toolbar:'#toolbar',nowrap:false,
		pageSize:20,url:query_url ">
			<thead>
				<tr>
					<th data-options="field:'questionNo',align:'center',width:60">题号</th>
					<th data-options="field:'question',align:'center',width:260">题目</th>
					<th data-options="field:'stuName',align:'center',width:120">学生姓名</th>
					<th data-options="field:'answerResultName',align:'center',width:80,formatter:formatResult">答题结果</th>
					<th data-options="field:'stuAnswer',align:'center',width:280,formatter:formatAnswer">学生答案</th>
					<th data-options="field:'techAnswer',align:'center',width:280">标准答案</th>
				</tr>
			</thead>
	</table>
</body>
</html>