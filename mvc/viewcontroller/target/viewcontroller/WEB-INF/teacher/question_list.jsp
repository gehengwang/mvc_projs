<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,education.model.*"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题目列表</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/js/easyui-common.js"></script>
<script type="text/javascript">
	
	var query_url="/questionPage.do?lessonId="+${param.lessonId};
	
	function addQuestion(){
		$("#lesson_id").val('${param.lessonId}');
		$("#dlg").dialog("open");
		$("#fm").form("clear")
	}
	
	function editQuestion(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$("#dlg").dialog("open").dialog("setTitle", "编辑");
			var regx = /<br>/g;
			row.techAnswer = row.techAnswer.replace(regx,"\n");
			row.equivalence = row.equivalence.replace(regx,"\n");
			$("#fm").form("load", row);
		}else{
			$.messager.alert("警告","请选择要修改的题目");
			return;
		}
	}
	
	function saveQuestion(){
		var row =  $("#data").datagrid("getSelected");
		if ($("#fm").form("validate") == false)
			return;
		//var datafm = $("#fm").form2json();
		var datafm = {"lessonId":'${param.lessonId}',
					  "questionNo":$("#question_no").val(),
					  "questionType":$("#question_type").combobox("getValue"),
					  "knowledge":$("#knowledge").val(),
					  "question":$("#question").val(),
					  "techAnswer":$("#tech_answer").val(),
					  "equivalence":$("#equivalence").val(),
					  "typicalFault":$("#typical_fault").val(),
					  "missKeyWord":$("#miss_key_word").val()};
		var techAnswer = $("#tech_answer").val();
		if(techAnswer==""||techAnswer==null){
			$.messager.alert("提示","标准答案不能为空");
			return;
		}else{
			$.post("/saveQuestion.do",datafm,function(result){
	        if (result.success){
	            $("#dlg").dialog("close");
				$("#data").datagrid("reload");
				if(result.msg!="更新成功"){
					$.messager.confirm("确认", result.msg, function(r) {
						if (r) {
							addQuestion(); 
						}
					});
				}else $.messager.show({title: "提示",msg: result.msg});
				
	        }else $.messager.show({title: "错误",msg: result.msg}); 
		    },"json");
		}
	}
	
	function deleteQuestions(){
		var row =  $("#data").datagrid("getSelected");
		if (row) {
			$.messager.confirm("确认", "是否要删除?", function(r) {
				if (r) {
					var datas = {"lessonId":row.lessonId,"questionNo":row.questionNo};
					$.post("/deleteQuestion.do", datas, function(result) {
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
			$.messager.alert("警告","请选择要删除的题目");
			return;
		}
	}
</script>
</head>
<body >
	<div id="toolbar" style="padding:5px;display:none">
		<a href="/lessonTechInit.do" class="easyui-linkbutton" 
			data-options="plain:true,iconCls:'icon-back'" >返回</a>
			&nbsp;&nbsp;&nbsp;
		<a href="###" class="easyui-linkbutton" 
			data-options="plain:true,iconCls:'icon-add'" onclick="addQuestion()">增加题目</a>
			&nbsp;&nbsp;&nbsp;
		<a href="###" class="easyui-linkbutton" 
			data-options="plain:true,iconCls:'icon-edit'" onclick="editQuestion()">修改题目</a>
			&nbsp;&nbsp;&nbsp;
		<a href="###" class="easyui-linkbutton" 
			data-options="plain:true,iconCls:'icon-remove'" onclick="deleteQuestions()">删除题目</a>
	</div>
	
	<table id="data" class="easyui-datagrid" 
		data-options="fit:true,singleSelect:true,toolbar:'#toolbar',url:query_url,nowrap:false ">
			<thead>
				<tr>
					<th data-options="field:'questionNo',align:'center',width:'80px'">题号</th>
					<th data-options="field:'question',align:'center',width:'260px'">题目</th>
					<th data-options="field:'techAnswer',align:'center',width:'260px'">答案</th>
					<th data-options="field:'questionType',align:'center',width:'120px'">题型</th>
					<th data-options="field:'knowledge',align:'center',width:'150px'">知识点</th>
					<th data-options="field:'equivalence',align:'center',width:'150px'">等价</th>
					<th data-options="field:'typicalFault',align:'center',width:'120px'">典型错误</th>
					<th data-options="field:'missKeyWord',align:'center',width:'120px'">缺少关键字</th>
				</tr>
			</thead>
	</table>
	
	<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'题目信息',
				buttons:[
				{ text:'确定',iconCls:'icon-ok',	  id:'importBtn',  handler:function(){saveQuestion();}},
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 680px; height:80%px; padding: 0 50px 0 50px;">

			<form id="fm" method="post" >
				<table cellpadding="5">
                <tr>
                    <td>题号:</td>
                    <td><input type="text" name="questionNo" id="question_no" class="easyui-validatebox" 
						style="width:300px" data-options="required:true"/></input></td>
                </tr>
                 <tr>
                    <td>题型:</td>
                    <td>
                    	<select name="questionType" id="question_type" class="easyui-combobox" data-options="required:true" style="width:300px">
                    		<option value="多行答案任一">多行答案任一</option>
                    		<option value="多行答案顺序">多行答案顺序</option>
                    		<option value="多行答案无序">多行答案无序</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td>知识点:</td>
                    <td><input type="text" name="knowledge" id="knowledge" class="easyui-validatebox" 
						style="width:300px" data-options=""/></input></td>
                </tr>
                <tr>
                    <td>题目:</td>
                    <td><input type="text" name="question" id="question" class="easyui-validatebox" 
						style="width:300px" data-options="required:true"/></input></td>
                </tr>
                <tr>
                    <td>标准答案:</td>
                    <td>
                   	 	<textarea rows="4" cols="46" name="techAnswer" id="tech_answer"  
                    			  data-options="required:true">
                   		</textarea>
                   	</td>
                </tr>
                <tr>
                    <td>等价:</td>
					<td>
                   	 	<textarea rows="3" cols="46" name="equivalence" id="equivalence"></textarea>
                   	</td>
                </tr>
                <tr>
                    <td>典型错误:</td>
                    <td><input type="text" name="typicalFault" id="typical_fault" class="easyui-validatebox" 
						style="width:300px" data-options=""/></input></td>
                </tr>
                <tr>
                    <td>缺少关键字:</td>
                    <td><input type="text" name="missKeyWord" id="miss_key_word" class="easyui-validatebox" 
						style="width:300px" data-options=""/></input></td>
                </tr>
           		</table>
			</form>
	</div>
</body>
</html>