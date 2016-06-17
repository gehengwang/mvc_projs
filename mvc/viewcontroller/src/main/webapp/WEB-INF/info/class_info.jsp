<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级信息</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	var query_url="/queryClassPage.do?";
	$(function(){
		classCombo(".classCombo");
		//老师自己选择班级上课
		if('${sessionScope.USER_INFO.roles[0].roleId}'!=3){
			$("#class_choose").css("display","none");
		}//管理员管理班级
		else if('${sessionScope.USER_INFO.roles[0].roleId}'!=2){
			$("#add_class").css("display","none");
			$("#edit_class").css("display","none");
		}
	});
	
	function doQuery(){
		var classId = $("#class_id").combobox("getValue");
		var fmdata = {"classId":classId};
		$("#data").datagrid("load", fmdata);
	}
	
	function doReset()
	{
	  $("#fm").form("reset");
	  doQuery();
	}
	
	function addClass(){
		$("#dlg").dialog("open");
		$("#dlgFm").form("clear")
	}
	
	function editClass(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$("#dlg").dialog("open").dialog("setTitle", "修改");
			$("#dlgFm").form("load", row);
		}else{
			$.messager.alert("警告","请选择要修改的班级");
			return;
		}
	}
	
	function saveClass(){
		var row =  $("#data").datagrid("getSelected");
		var datafm = "";
		if(row==null){
			datafm = {"className":$("#class_name").val(),
				"classStatus":$("#class_status").combobox("getValue")}
		}else{
			datafm = {"classId":row.classId,"className":$("#class_name").val(),
				"classStatus":$("#class_status").combobox("getValue")}
		}
		$.post("/saveClass.do",datafm,function(result){
	        if (result.success){
	            $("#dlg").dialog("close");
				$("#data").datagrid("reload");
	            $.messager.show({title: "成功",msg: result.msg});
	        }else $.messager.show({title: "错误",msg: result.msg}); 
		 },"json");
	}
	
	function addClassLesson(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$("#lesson_data").datagrid("options").url="/classLessonPage.do?classId="+row.classId;
			$('#lesson_data').datagrid({"onLoadSuccess":function(dataExists){
				$("#lesson_choose").datagrid({"onLoadSuccess":function(dataAll){
					$("#dlg_lesson").dialog("open");
					
					for(var i=0;i<dataExists.rows.length;i++){
						for(var j=0;j<dataAll.rows.length;j++){
							if(dataAll.rows[j].lessonId==dataExists.rows[i].lessonId){
								$("#lesson_choose").datagrid('selectRow',j);
							}
						}
					}
				  }
			  });
			}});
		}else{
			$.messager.alert("警告","请先选择班级");
			return;
		}
	}

	function saveClassLesson(){
		 var rowsClass =  $("#data").datagrid("getSelections");
		 var rowsLesson =  $("#lesson_choose").datagrid("getSelections");
		 
		 var classIdArray = [];
		 for ( var i = 0; i < rowsClass.length; i++) {
			 classIdArray.push(rowsClass[i].classId);
		 }
		 var lessonIdArray = [];
		 if(rowsLesson.length==0){
			 $.messager.alert("警告","请至少选择一个班级!");
			 return;
		 }else{
			 for ( var i = 0; i < rowsLesson.length; i++) {
				 lessonIdArray.push(rowsLesson[i].lessonId);
			 }
		 }
		 var fmdata = {"classIds":classIdArray.toString(),"lessonIds":lessonIdArray.toString()};
		 $.post("/saveClassLesson.do",fmdata,function(result){
			 if(result.success==true){
				 $("#dlg_lesson").dialog("close");
				 $("#data").datagrid("reload");
				 $.messager.show({title: "提示",msg: result.msg});
			 }else{
				 $.messager.show({title: "错误",msg: result.msg});
			 }
		 },"json");
	}
	
	function classDetail(value,row,index){
		return "<a href='javascript:openWin("+row.classId+")' style='color:#307DCA'>"+value+"</a>";
	}
	
	function openWin(classId){
		$('#win_lesson').window('open');
		$("#lesson_data").datagrid("options").url="/classLessonPage.do?classId="+classId;
		$('#lesson_data').datagrid({"onLoadSuccess":function(data){}});//重新加载成功后方法,不然会跳到之前加载成功的dialog
	}

	
</script>
</head>
<body>

	<div id="toolbar" style="padding:5px;display:none">
		<form id="fm" method="post">
				班级:
				<input type="text" id="class_id" name="classId" class="classCombo" 
				 	   data-options="onSelect:function(){doQuery();}" />&nbsp;&nbsp;&nbsp;
				<a id="add_class"href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-add'" onclick="addClass()">新增班级</a>&nbsp;&nbsp;&nbsp;
				<a id="edit_class" href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-edit'" onclick="editClass()">修改班级</a>&nbsp;&nbsp;&nbsp;
				<a  id="class_choose" href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-gears'" onclick="addClassLesson()">班级选课</a>&nbsp;&nbsp;&nbsp;
				<a href="###" class="easyui-linkbutton" 
				data-options="plain:true,iconCls:'icon-reload'" onclick="doReset()">清除查询条件</a>
		</form>
	</div>
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,rownumbers:true,pagination:true,toolbar:'#toolbar',
		pageSize:20,url:query_url,nowrap:false,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true'"></th>
					<th data-options="field:'deptName',align:'center',width:120">机构名称</th>
					<th data-options="field:'className',align:'center',width:120">班级名称</th>
					<th data-options="field:'classStatusName',align:'center',width:100">班级状态</th>						
					<th data-options="field:'lessonName',align:'center',width:220,formatter:classDetail">选课详情</th>
				</tr>
			</thead>
	</table>
	
	<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'班级信息',
				buttons:[
				{ text:'确定',iconCls:'icon-ok', handler:function(){saveClass();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 360px; height: 260px; padding: 20px 20px;">

			<form id="dlgFm" method="post" >
				<table cellpadding="5">
                <tr>
                    <td>班级名称:</td>
                    <td><input type="text" name="className" id="class_name" class="easyui-validatebox" 
                    	data-options="required:true" style="width:160px"/></input></td>
                </tr>
                <tr>
                    <td>班级状态:</td>
                    <td>
						<select name="classStatus" id="class_status" class="easyui-combobox" 
							data-options="required:true" style="width:160px">
							<option value="0">停用</option>
							<option value="1" selected="selected">启用</option>
						</select>
					</td>
                </tr>
           		</table>
			</form>
	</div>
	
	<!-- 课程选择 -->
	<div id="dlg_lesson" class="easyui-dialog"
			data-options="iconCls:'icon-gears',closed:true,resizable:true,modal:true,title:'课程配置',
				buttons:[
				{ text:'保存',iconCls:'icon-upload',	handler:function(){saveClassLesson();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg_lesson').dialog('close');}  }]"
			style="width:520px; height:70%;">

				<table id="lesson_choose" class="easyui-datagrid"
   					   data-options="fit:true,nowrap:false,url:'/queryLessonList.do'">
				<thead>
				<tr>
					<th data-options="field:'ck',checkbox:'true'"></th>
					<th data-options="field:'lessonId',align:'center',width:80">课件号</th>
					<th data-options="field:'lessonName',align:'center',width:220">课程名称</th>
					<th data-options="field:'techNameOwn',align:'center',width:100">上传老师</th>
				</tr>
				</thead>
				</table>
	</div>
	
	<!-- 课件详情 -->
    <div id="win_lesson" class="easyui-window" title="课件详情" style="width:680px;height:80%"
        data-options="iconCls:'icon-save',modal:true,closed:true">
   		<table id="lesson_data" class="easyui-datagrid" 
   			data-options="fit:true,pagination:true,singleSelect:true,nowrap:false">
			<thead>
				<tr>
					<th data-options="field:'lessonId',align:'center',width:80">课件号</th>
					<th data-options="field:'lessonName',align:'center',width:260">课程名称</th>
					<th data-options="field:'techNameUse',align:'center',width:80">使用老师</th>
					<th data-options="field:'techNameOwn',align:'center',width:80">上传老师</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>