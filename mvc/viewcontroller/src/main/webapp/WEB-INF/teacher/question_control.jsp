<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,education.model.*"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课堂题目控制</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<style type="text/css">
.view{
	width: 100%;
	position: relative;
	font-size:56px;
	font-weight:bold;
}
.datagrid-mask{
      display:none;
      opacity:0;
      filter:alpha(opacity=0);
}
.datagrid-mask-msg{
      display:none;
      opacity:0;
      filter:alpha(opacity=0);
}
</style>
<script type="text/javascript">
	
	var query_url="/questionPage.do?lessonId="+${param.lessonId}+"&classId="+${param.classId};
	$().ready(function() {
		//30S刷新一次datagrid，更新已完成学生信息
		setInterval(function(){
			var row = $('#question').datagrid('getSelected');
			var rowIndex = $('#question').datagrid('getRowIndex', row);
			//jquery easyui加载成功后,选中一行
			$('#question').datagrid({"onLoadSuccess":function(data){
    				$(this).datagrid('selectRow',rowIndex);
			}});
		},30000);
	});
	
	function onClickCell(rowIndex, field, value){
		var row = $("#question").datagrid("getRows")[rowIndex];
		var regx = /<br>/g;
		//3.12点到题目列时，显示所有字段，比如题型、知识点、典型错误等
		if(field =='question'){
			$("#answerDiv").css("display","none");
			$("#questionDiv").css("display","block");
			var data = "题目："+value.replace(regx,"\t")+"\n题型:"+row.questionType.replace(regx,"\t")
					  +"\n知识点:"+row.knowledge.replace(regx,"\t")+"\n等价:"+row.equivalence.replace(regx,"\t")
					  +"\n典型错误:"+row.typicalFault.replace(regx,"\t")+"\n缺失关键字:"+row.missKeyWord.replace(regx,"\t");
			$("#questionName").val(data);
		}else if(field =='techAnswer'){
			$("#answerDiv").css("display","none");
			$("#questionDiv").css("display","block");
			$("#questionName").val(value.replace(regx,"\n"));
		}else if(field =='stuName'){
			$("#questionDiv").css("display","none");
			$("#answerDiv").css("display","block");
			var url = "/answerList.do?lessonId=${param.lessonId}&classId=${param.classId}&questionNo="+row.questionNo;
			$("#answer").attr("src",url);
		}
	}
	
	function doQuery(){
		$("#question").datagrid("reload");
	}
	
</script>
</head>
<body class="easyui-layout">

	<div data-options="region:'center',split:true" style="background:#fafafa;overflow:hidden;" >
		<table id="question" class="easyui-datagrid"
			data-options="fit:true,singleSelect:true,
			border:false,nowrap:false,url:query_url,onClickCell:onClickCell" >
			<thead>
				<tr>
					<th data-options="field:'questionNo',align:'center'">题号</th>
					<th data-options="field:'question',align:'center'">题目</th>
					<th data-options="field:'techAnswer',align:'center'">答案</th>
					<th id="a" data-options="field:'stuName',align:'center'">已完成</th>
					<!-- <th data-options="field:'questionType',align:'center',width:100">题型</th>
					<th data-options="field:'knowledge',align:'center',width:100">知识点</th>
					<th data-options="field:'equivalence',align:'center',width:100">等价</th>
					<th data-options="field:'typicalFault',align:'center',width:100">典型错误</th>
					<th data-options="field:'missKeyWord',align:'center',width:100">缺少关键字</th> -->
				</tr>
			</thead>
		</table>
	</div>
	
	<div data-options="region:'south' ,split:true" 
			style="background:#fafafa;overflow:hidden;height:82%;">
		<div id="questionDiv" style="position:absolute; height:100%; width:100%;overflow:auto">
			<textarea rows="100%" id="questionName" class="view" ></textarea>
		</div>
		<div id="answerDiv"	style="overflow:auto;height:100%;width:100%;display:none" >
			<iframe id="answer" src="" style="background-color:white;width:100%;height:100%;"></iframe>
		</div>
	</div>
</body>
</html>