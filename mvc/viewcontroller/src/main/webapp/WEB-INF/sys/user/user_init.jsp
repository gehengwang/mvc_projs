<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/sys/taglib.jsp"%>
<%@ taglib prefix="sys" uri="/sys-tags"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>${curr_menu_name}</title>
		<BASE href="${basePath}" />
		<sys:resource />
		<script type="text/javascript">
	$(document).ready(function() {
		$.sys.bindEnter({
			def : doQuery,
			type : [ {
				id : "#dlg",
				handler : save_data
			} ]
		});
	});

	function doQuery() {
		var fmdata = $("#queryfm").form2json();
		$("#data1").datagrid("load", fmdata);
	}
	function doReset() {
		$("#queryfm").form("reset");

	}
	var url;
	function addUser() {
		$("#dlg").dialog("open").dialog("setTitle", "新增");
		$("#fm").form("reset");
		url = "sys/user_add.do";
	}
	function editUser(index) {
		if (index) {
			$("#data1").datagrid("selectRow", index);
		}
		var row = $("#data1").datagrid("getSelected");

		if (row) {
			$("#dlg").dialog("open").dialog("setTitle", "编辑");
			$("#fm").form("load", row);
			url = "sys/user_edit.do";
		} else {
			$.messager.alert("警告", "请选择要修改的数据");
		}
	}

	function save_data() {
		if ($("#fm").form("validate") == false)
			return;
		var datafm = $("#fm").form2json();
		$.post(url, datafm, function(result) {
			if (result.success) {
				$("#dlg").dialog("close");
				$("#data1").datagrid("reload");
				$.messager.show({
					title : "成功",
					msg : result.msg
				});
			} else
				$.messager.show({
					title : "错误",
					msg : result.msg
				});
		}, "json");

	}

	function delUser(index) {
		if (index) {
			$("#data1").datagrid("selectRow", index);
		}
		var row = $("#data1").datagrid("getSelected");
		if (row) {

			$.messager.confirm("确认", "是否删除用户[" + row.user_name + "]",
			 function(r) {
				if (r) {
					$.post("sys/user_del.do", row, function(result) {
						if (result.success) {
							$.ui.treegrid.reload("#data1", row.parent_group);
							$.messager.show({
								title : "成功",
								msg : result.msg
							});
						} else
							$.messager.show({
								title : "错误",
								msg : result.msg
							});
					}, "json");
				}
			});

		} else {
			$.messager.alert("警告", "请选择要删除的数据");
		}
	}

	function userRoles(index) {
		if (index) {
			$("#data1").datagrid("selectRow", index);
		}
		var row = $("#data1").datagrid("getSelected", index);
		if (row) {
			$("#roledlg").dialog("open");
			$("#roleBtn").linkbutton("enable");
			var url = "/tree_role/checked/role_user.do?id=0&user_id="
					+ row.user_id;
			$("#roleTree").tree({
				url : url,
				checkbox : true
			});

		} else {
			$.messager.alert("警告", "请选择一个用户");
		}

	}

	function saveRole() {

		var checked = $("#roleTree").tree("getChecked");
		var row = $("#data1").treegrid("getSelected");
		if (row) {
			user_id = row.user_id;
			user_name = row.user_name;
		}
		var roles = [];
		for (i in checked) {
			roles[i] = checked[i].id;
		}
		$("#roleBtn").linkbutton("disable");
		$.post("/sys/user_saveRole.do", {
			user_id : user_id,
			user_name : user_name,
			roles : roles
		}, function(result) {
			if (result.success) {
				$("#roledlg").dialog("close");
				$.messager.show({
					title : "成功",
					msg : result.msg
				});
			} else
				$.messager.show({
					title : "错误",
					msg : result.msg
				});
			$("#roleBtn").linkbutton("enable");
		}, "json");

	}

	function fmAction(value, row, index) {
		var blank = "&nbsp;&nbsp;";
		var str = "<span title='用户修改'  class='icon-edit spbtn'  onclick='editUser("
				+ index + ")'>" + blank + blank + "</span>" + blank;
		str = str
				+ "<span title='用户删除'  class='icon-remove spbtn'  onclick='delUser("
				+ index + ")'>" + blank + blank + "</span>" + blank;

		str = str
				+ "<span title='角色赋权'  class='icon-role spbtn'  onclick='userRoles("
				+ index + ")'>" + blank + blank + "</span>" + blank;
		str = str
				+ "<span title='用户组赋权'  class='icon-group spbtn'  onclick='userGroups("
				+ index + ")'>" + blank + blank + "</span>" + blank;
		return str;
	}
	
	function userGroups(index)
	{
		if (index) {
			$("#data1").datagrid("selectRow", index);
		}
		var row = $("#data1").datagrid("getSelected", index);
		if (row) {
			$("#groupdlg").dialog("open");
			$("#groupBtn").linkbutton("enable");
			var url = "/tree_group/checked/group_user.do?id=0&user_id="
					+ row.user_id;
			$("#groupTree").tree({
				url : url,
				checkbox : true
			});

		} else {
			$.messager.alert("警告", "请选择一个用户");
		}
	}
	
	function saveGroup()
	{
		var checked = $("#groupTree").tree("getChecked");
		var row = $("#data1").treegrid("getSelected");
		if (row) {
			user_id = row.user_id;
			user_name = row.user_name;
		}
		var groups = [];
		for (i in checked) {
			groups[i] = checked[i].id;
		}
		$("#groupBtn").linkbutton("disable");
		$.post("/sys/user_saveGroup.do", {
			user_id : user_id,
			user_name : user_name,
			groups : groups
		}, function(result) {
			if (result.success) {
				$("#groupdlg").dialog("close");
				$.messager.show({
					title : "成功",
					msg : result.msg
				});
			} else
				$.messager.show({
					title : "错误",
					msg : result.msg
				});
			$("#groupBtn").linkbutton("enable");
		}, "json");
		
	}
</script>
	</head>

	<body>
		<div>
			<jsp:include page="/sys/header.jsp"></jsp:include>
			<sys:search curr_name="${curr_menu_name}" />
		</div>
		<div class="fixContainer">
			<div id="toolbar" style="padding: 10px;">
				<form id="queryfm" style="margin: 0">
					登录名称：
					<input type="text" name="user_name" />
					显示名称:
					<input type="text" name="nick_name" />
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
						onclick="doQuery()">查询</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-reload'"
						onclick="doReset()">重置</a>
					<hr />
					<a class="easyui-linkbutton" data-options="iconCls:'icon-add'"
						onclick="addUser()">增加</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'"
						onclick="editUser()">修改</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
						onclick="delUser()">删除</a>

					<a class="easyui-linkbutton" data-options="iconCls:'icon-role'"
						onclick="userRoles()">角色权限</a>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-group'"
						onclick="userGroups()">用户组权限</a>

				</form>
			</div>

			<table id="data1" class="easyui-datagrid"
				data-options="cls:'content',
     	fit:true,rownumbers:true,toolbar:'#toolbar',
     	url:'/sys/user_query.do',pagination:true,singleSelect:true">
				<thead>
					<tr class="header">

						<th data-options="field:'user_id',width:100">
							用户ID
						</th>
						<th data-options="field:'user_name',width:150">
							登录名称
						</th>
						<th data-options="field:'nick_name',align:'center',width:150">
							显示名称
						</th>
						<th data-options="field:'org_name',align:'center',width:150">
							所在组织
						</th>
						<th data-options="field:'dept_name',align:'center',width:150">
							所在部门
						</th>
						<th
							data-options="field:'action',align:'center',width:200,formatter:fmAction">
							功能
						</th>

					</tr>
				</thead>
			</table>


			<!-- 编辑窗口 -->
			<div id="dlg" class="easyui-dialog"
				data-options="closed:true,resizable:true,modal:true,
				buttons:[
				{ text:'保存',iconCls:'icon-ok',	    handler:function(){save_data();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
				style="width: 400px; height: 320px; padding: 30px 20px;">

				<form id="fm" method="post">
					<input type="hidden" name="user_id" value="" />
					<div class="form_item col1">
						<label>
							登录名称：
						</label>
						<input type="text" name="user_name" class="easyui-validatebox"
							style="width: 200px" />
					</div>
					<div class="form_item col1">
						<label>
							显示名称：
						</label>
						<input type="text" name="nick_name" class="easyui-validatebox"
							style="width: 200px" />
					</div>
					<div class="form_item col1">
						<label>
							所属组织：
						</label>
						<input type="text" name="org_id" class="easyui-validatebox"
							style="width: 200px" />
					</div>
					<div class="form_item col1">
						<label>
							所属部门：
						</label>
						<input type="text" name="dept_id" class="easyui-validatebox"
							style="width: 200px" />
					</div>

				</form>
			</div>


			<!-- 编辑窗口 -->
			<div id="roledlg" class="easyui-dialog"
				data-options="closed:true,resizable:true,modal:true,title:'用户所属角色',
				buttons:[
				{ text:'保存',iconCls:'icon-ok',	  id:'roleBtn',  handler:function(){saveRole();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#roledlg').dialog('close');}  }]"
				style="width: 400px; height: 320px; padding: 30px 20px;">

				<div id="roleTree">
				</div>
			</div>
			
			<!-- 编辑窗口 -->
			<div id="groupdlg" class="easyui-dialog"
				data-options="closed:true,resizable:true,modal:true,title:'用户所属组',
				buttons:[
				{ text:'保存',iconCls:'icon-ok',	  id:'groupBtn',  handler:function(){saveGroup();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#groupdlg').dialog('close');}  }]"
				style="width: 400px; height: 320px; padding: 30px 20px;">

				<div id="groupTree">
				</div>
			</div>
		</div>
	</body>
</html>