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
	var query_url="/querySnapPage.do";
	$(function(){
		classCombo(".classCombo");
		lessonGrid(".lessonGrid");
	});
	
	function doQuery(){
		var classId = $("#class_id").combobox("getValue");
		var lessonId = $("#lesson_id").combobox("getValue");
		var createDate = $("#create_date").datebox("getValue");
		var fmdata = {"lessonId":lessonId,"classId":classId,"createDate":createDate};
		$("#data").datagrid("load", fmdata);
	}
	
	function deleteSnapDetail(){
		var rows = $("#data").datagrid("getSelections");
		if(rows.length>0){
			$.messager.confirm("确认", "是否要删除快照?", function(r) {
				if (r) {
					var snapArray = [];
					for(var i=0;i<rows.length;i++){
						snapArray.push(rows[i].snapName);
					}
					$.post("/deleteSnapDetail.do",{"snapNames":snapArray.toString()},function(result){
						if(result.success){
							$("#data").datagrid("reload");
							$.messager.show({title: "成功",msg: result.msg});
						}else{
							$.messager.show({title: "失败",msg: result.msg});
						}
					},"json");
				}
			});
		}else{
			$.messager.alert("提示","请选择要删除的快照");
			return;
		}
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	function dateformate(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+""+(m<10?('0'+m):m)+""+(d<10?('0'+d):d);
	}
	function dateparse(s){
		if (!s) return new Date();
		var y = s.substr(0,4);
		var m = s.substr(4,2);
		var d = s.substr(6,2);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}
	
	function lessonSnap(value,row,index){
		return "<a href='/querySnapDetailInit.do?snapName="+value+"' style='color:#307DCA'>"+value+"</a>";
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
				答题时间：
				<input type="text" id="create_date" name="createDate" class="easyui-datebox" 
					data-options="editable:false,formatter:dateformate,parser:dateparse" style="width: 120px" />&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-remove'" onclick="deleteSnapDetail()">删除快照</a>&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-search'" onclick="doQuery()">查询</a>&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,toolbar:'#toolbar',
		pageSize:20,url:query_url">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true' "></th>
					<th data-options="field:'className',align:'center',width:120">班级</th>
					<th data-options="field:'lessonName',align:'center',width:260">课程</th>
					<th data-options="field:'snapName',align:'center',width:360,formatter:lessonSnap">快照名称</th>
				</tr>
			</thead>
	</table>
</body>
</html>