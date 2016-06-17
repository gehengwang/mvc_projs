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
	var query_url="/queryLessonPage.do";
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
	
	function editLesson(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$("#dlg").dialog("open").dialog("setTitle", "修改");
			$("#dlgFm").form("load", row);
		}else{
			$.messager.alert("警告","请选择要修改的课程");
			return;
		}
	}
	function updateLesson(){
		var row =  $("#data").datagrid("getSelected");
		var datafm = {"lessonId":row.lessonId,"lessonName":$("#lesson_name_edit").val(),
					  "lessonStatus":$("#lesson_status").combobox("getValue")};
		$.post("/updateLesson.do",datafm,function(result){
	        if (result.success){
	            $("#dlg").dialog("close");
				$("#data").datagrid("reload");
	            $.messager.show({title: "成功",msg: result.msg});
	        }else $.messager.show({title: "错误",msg: result.msg}); 
		 },"json");
	}
	
	function deleteLesson(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$.messager.confirm("确认", "是否要删除?", function(r) {
				if (r) {
					$.post("/deleteLesson.do", {"lessonId":row.lessonId}, function(result) {
						if (result.success) {
							$("#data").datagrid("reload");
							$.messager.show({title: "成功",msg: result.msg});
						} else {
							$.messager.show({title: "错误",msg: result.msg}); 
						}
					}, "json");
				}
			});
		} else {
			$.messager.alert("警告","请选择要删除的课件");
			return;
		}
	}
	
	function lessonFormat(value,row,index){
		return "<a href='/questionListInit.do?lessonId="+row.lessonId+"' style='color:#307DCA'>"+value+"</a>";
	}
	
	function uploadLesson(){
		$("#importDlg").dialog("open");
		$("#importFm").form("clear")
	}
	
	function doImport(){
		 //得到上传文件的全路径  
        if ($("#importFm").form("validate") == false)
			return;
		var fileName= $('#file').filebox('getValue');
		var lessonName = $("#lesson_name").val();
        //对文件格式进行校验  
        var d1=/\.[^\.]+$/.exec(fileName);
        if(d1==".xls"||d1==".xlsx"){
            fmSubmit("#importFm","/importLesson.do?lessonName="+lessonName,"#importBtn",function(){
        		$("#data").datagrid("load");
        	});
        }else{
             $.messager.alert('提示','请选择xls格式文件！','info');   
             $('#file').filebox('setValue',''); 
         }
	}
	
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				 	   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				课程:
				<input type="text" id="lesson_id" name="lessonId" class="lessonGrid" style="width:180px" 
					   data-options="onSelect:function(){doQuery();}" />
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
				&nbsp;&nbsp;&nbsp;
				<a href="/files/课件模板V20.xlsx" class="easyui-linkbutton" style='color:#0000FF'
					data-options="plain:true,iconCls:'icon-download'" >课件模板下载</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
					data-options="plain:true,iconCls:'icon-upload'" onclick="uploadLesson()">上传课件</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
					data-options="plain:true,iconCls:'icon-edit'" onclick="editLesson()">修改课程</a>
				&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
					data-options="plain:true,iconCls:'icon-remove'" onclick="deleteLesson()">删除课程</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,pagination:true,toolbar:'#toolbar',pageSize:20,url:query_url,singleSelect:true ">
			<thead>
				<tr>
					<!-- <th data-options="field:'ck',checkbox:true"></th> -->
					<th data-options="field:'techNameOwn',align:'center',width:100">课件上传老师</th>
					<th data-options="field:'lessonName',align:'center',width:260,formatter:lessonFormat">课程</th>
					<th data-options="field:'lessonStatus',align:'center',width:100">课程状态</th>
					<th data-options="field:'createDate',align:'center',width:160">导入时间</th>
					<th data-options="field:'modifyDate',align:'center',width:160">更新时间</th>
				</tr>
			</thead>
	</table>
	
	<div id="importDlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'上传课件',
				buttons:[
				{ text:'上传',iconCls:'icon-upload',	  id:'importBtn',  handler:function(){doImport();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#importDlg').dialog('close');}  }]"
			style="width: 500px; height: 260px; padding: 20px 20px;">

			<form id="importFm" method="post" enctype="multipart/form-data">
				<table cellpadding="5">
                <tr>
                    <td>课程名称:</td>
                    <td><input type="text" name="lessonName" id="lesson_name" class="easyui-validatebox" 
						data-options="required:true"/></input></td>
                </tr>
                <tr>
                    <td>上传文件:</td>
                    <td><input class="easyui-filebox" style="width:340px" name="file" id="file"
					data-options="required:true,buttonText:'选择Excel',buttonIcon:'icon-excel', prompt:'选择一个文件...' "></td>
                </tr>
           		</table>
			</form>
	</div>
	<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'题目信息',
				buttons:[
				{ text:'确定',iconCls:'icon-ok',	  id:'importBtn',  handler:function(){updateLesson();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 500px; height: 260px; padding: 20px 20px;">

			<form id="dlgFm" method="post" >
				<table cellpadding="5">
                <tr>
                    <td>课程名称:</td>
                    <td><input type="text" name="lessonName" id="lesson_name_edit" class="easyui-validatebox" 
						style="width:300px" data-options="required:true"/></input></td>
                </tr>
                 <tr>
                    <td>课件状态:</td>
                    <td><select name="lessonStatus" id="lesson_status"  style="width:300px" class="easyui-combobox">
                    	<option value="inuse">inuse</option>
                    	<option value="temp">temp</option>
                    	<option value="nouse">nouse</option>
                    </select></td>
                </tr>
           		</table>
			</form>
	</div>
</body>
</html>