<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>答案查询</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var query_url="/answerPage.do?lessonId="+${param.lessonId}+"&classId="+${param.classId};
	
	function doQuery(){
		var questionNo = $("#question_no").val();
		var question = $("#question").val();
		var answerResult = $("#answer_result").combobox("getValue");
		var fmdata = {"questionNo":questionNo,"question":question,"answerResult":answerResult,"classId":'${param.classId}'};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
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
				<a href="javascript:history.back();" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-back'" >返回</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<span>|</span>
				题号:
				<input type="text" name="questionNo" id="question_no" style="width:120px" 
					   class="easyui-textbox" data-options="onChange:function(){doQuery();}"/>&nbsp;&nbsp;&nbsp;&nbsp;
				题目:
				<input type="text" name="question" id="question" style="width:120px" 
					   class="easyui-textbox" data-options="onChange:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;&nbsp;
				答题结果:
				<select class="easyui-combobox" name="answerResult" id="answer_result" style="width:120px"
					data-options="onSelect:function(){doQuery();}">
					<option selected="selected" value="">--全部--</option>
					<option value="0">错误</option>
					<option value="1">正确</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">重置</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,singleSelect:true,toolbar:'#toolbar',nowrap:false,
		pageSize:20,url:query_url ">
			<thead>
				<tr>
					<th data-options="field:'questionNo',align:'center',width:60">题号</th>
					<th data-options="field:'question',align:'center',width:260">题目</th>
					<th data-options="field:'answerResultName',align:'center',width:80,formatter:formatResult">答题结果</th>
					<th data-options="field:'stuAnswer',align:'center',width:280,formatter:formatAnswer">学生答案</th>
					<th data-options="field:'techAnswer',align:'center',width:280">标准答案</th>
					
				</tr>
			</thead>
	</table>
</body>
</html>