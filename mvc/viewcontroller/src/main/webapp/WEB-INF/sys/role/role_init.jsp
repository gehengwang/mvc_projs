<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/sys/taglib.jsp"%>
<%@ taglib prefix="sys" uri="/sys-tags"%>

<!DOCTYPE HTML">
<html>
<head>
<title>${curr_menu_name}</title>
<BASE href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<sys:resource />
<script type="text/javascript">

	$(document).ready(function() {

		$.ui.combogrid.role("#role_query", {
			afterEnter : function(e) {
				expendById();
			}
		});
		$.ui.combogrid.role("#parent_role");

		$.ui.combogrid.menu("#menuCombo", {
			afterEnter : function(e) {
				
				expendTree("#menuCombo","#menuRoleTree");
			}
		});

	});
    function expendTree(combogrid,tree){
    	var id = $(combogrid).combogrid("getValue");
		if (id) {
			var node = $(tree).tree("find", id);
			if (node)
				$(tree).tree("expandTo", node.target);
			    $(tree).tree("select", node.target);
		}
    }
	function expendById() {
		var id = $("#role_query").combogrid("getValue");
		if (id)
			$("#data1").treegrid("expandByPath", id);
	}

	var url;
	function addRole() {
		$("#dlg").dialog("open").dialog("setTitle", "新增");
		$("#fm").form("reset");
		url = "sys/role_add.do";
	}
	function editRole() {
		var row = $("#data1").treegrid("getSelected");
		if (row) {
			$("#dlg").dialog("open").dialog("setTitle", "编辑");
			$("#fm").form("load", row);

			var parent = $("#data1").treegrid("find", row.parent_role);
			var parent_name = "";
			if (parent && parent != null) {
				parent_name = parent.role_name;
			}
			$("#parent_role").combogrid("setText", parent_name);
			url = "sys/role_edit.do";
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
				$.ui.treegrid.reload("#data1", $("#parentId").val(),
						datafm.parent_role);
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

	function delRole() {
		var row = $("#data1").treegrid("getSelected");
		if (row) {
			$.post("sys/role_del.do", row, function(result) {
				if (result.success) {
					$.ui.treegrid.reload("#data1", row.parent_role);
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
		} else {
			$.messager.alert("警告", "请选择要删除的数据");
		}
	}

	function userPirvs(index) {
		if (index) {
			$("#data1").datagrid("selectRow", index);
		}
		var row = $("#data1").datagrid("getSelected", index);
		if (row) {
			$("#privdlg").dialog("open");
			$("#roleId4priv").val(row.role_id);
			var tab = $("#tt").tabs("getSelected");
			var index = $("#tt").tabs("getTabIndex",tab);
			onSelect("",index);
		} else {
			$.messager.alert("警告", "请选择一个角色");
		}
	}
	function onSelect(title,index){
		var role_id=$("#roleId4priv").val();
		if(role_id=="")return;
		switch (index) {
		case 0:
			var url = "/tree_menu/checked/menu_role.do?id=0&role_id="+role_id;
		    $("#menuRoleTree").tree({url : url,checkbox : true,cascadeCheck:false});
			break;
		case 1:
			var url = "/tree_rdata/checked/rdata_role.do?id=0&role_id="+role_id;
		    $("#rdataRoleTree").tree({url : url,checkbox : true,onlyLeafCheck:true}).tree("expandAll");

					
			break;
		default:
			break;
		}
	}
	function savePrivs(){
		var tab = $("#tt").tabs("getSelected");
		var index = $("#tt").tabs("getTabIndex",tab);
		var title=tab.panel("options").title;
		var role_id=$("#roleId4priv").val();

		 $.messager.confirm("确认","是否保存["+title+"]",function(r){
	  		    if (r){
	  		    	var url;
	  		    	var data;
	  		    	switch (index) {
	  				case 0:
	  					url="sys/role_saveMenu.do";
	  					var checked = $("#menuRoleTree").tree("getChecked");
	  					var menus = [];
	  					for (i in checked) {
	  						menus[i] = checked[i].id;
	  					}
	  					data={role_id:role_id,menus:menus};
	  					break;
	  				case 1:
	  					url="sys/role_saveRData.do";
	  					var checked = $("#rdataRoleTree").tree("getChecked");
	  					var rdatas = [];
	  					for (i in checked) {
	  						rdatas[i] = checked[i].id;
	  					}
	  					data={role_id:role_id,rdatas:rdatas};
	  					break;
	  				default:
	  					break;
	  				}
	  		    	$("#privBtn").linkbutton("disable");
	  		    	$.messager.progress({msg:"处理中.."});
	  		    	$.sys.dgPost(url, data, function(result) {
	  					if (result.success) {	
	  						$.messager.show({title : "成功",msg : result.msg});
	  					} else
	  						$.messager.show({title : "错误",msg : result.msg});
	  				}, function(){
	  			   
	  				$("#privBtn").linkbutton("enable");
	  				$.messager.progress("close");
	  		    }
	  		);}
		 });

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
				角色名称： <input type="text" id="role_query" style="width: 200px" /> <a
					class="easyui-linkbutton" data-options="iconCls:'icon-search'"
					onclick="expendById()">展开</a>

				<hr />
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add'"
					onclick="addRole()">增加</a> <a class="easyui-linkbutton"
					data-options="iconCls:'icon-edit'" onclick="editRole()">修改</a> <a
					class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
					onclick="delRole()">删除</a> <a class="easyui-linkbutton"
					data-options="iconCls:'icon-security'" onclick="userPirvs()">权限设置</a>
			</form>
		</div>

		<table id="data1" class="easyui-treegrid"
			data-options="cls:'content',expand_url:'/tree_role/expandPath.do',
     	fit:true,rownumbers:true,toolbar:'#toolbar',
     	url:'/sys/role_tgQuery.do',
     	idField: 'id',treeField: 'role_name'">
			<thead>
				<tr class="header">
					<th data-options="field:'role_name',width:300">角色名称</th>
					<th data-options="field:'role_id',align:'center',width:150">
						角色ID</th>
					<th data-options="field:'admin',align:'center',width:150">管理员
					</th>
					<th data-options="field:'order_id',align:'center',width:150">
						角色排序</th>
				</tr>
			</thead>
		</table>


		<!-- 编辑窗口 -->
		<div id="dlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,
				buttons:[
				{ text:'保存',iconCls:'icon-ok',	    handler:function(){save_data();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#dlg').dialog('close');}  }]"
			style="width: 400px; height: 300px; padding: 10px 10px;">

			<form id="fm" method="post"">
				<input type="hidden" name="role_id" value="" /> <input
					type="hidden" name="parentId" id="parentId" />
				<div class="form_item col1">
					<label> 角色名称： </label> <input type="text" name="role_name"
						class="easyui-validatebox" style="width: 200px" />
				</div>
				<div class="form_item col1">
					<label> 角色上级： </label> <input type="text" name="parent_role"
						class="easyui-validatebox" style="width: 200px" id="parent_role" />
				</div>

				<div class="form_item col1">
					<label> 角色排序： </label> <input type="text" name="order_id"
						style="width: 200px" class="my-input" />
				</div>

			</form>
		</div>



		<!-- 编辑窗口 -->
		<div id="privdlg" class="easyui-dialog"
			data-options="closed:true,resizable:true,modal:true,title:'权限配置',
				buttons:[
				{ text:'保存',iconCls:'icon-ok',	  id:'privBtn',  handler:function(){savePrivs();}                   },
				{ text:'取消',iconCls:'icon-cancel',	handler:function(){$('#privdlg').dialog('close');}  }]"
			style="width: 600px; height: 420px;">
            <input id="roleId4priv" type="text" style="display:none;">
			<div id="tt" class="easyui-tabs" data-options="fit:true,border:false,onSelect:onSelect">
				<div title="菜单权限" data-options="iconCls:'icon-menu'"
					style="padding:20px;">
					<input id="menuCombo" type="text" style="width: 200px"  />
					<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
					onclick="expendTree('#menuCombo','#menuRoleTree')">展开</a>
					<div id="menuRoleTree"></div>
				</div>
				<div title="数据权限" data-options="iconCls:'icon-data'"
					style="padding:20px;">
					<div id="rdataRoleTree"></div>
                </div>
				<div title="元素权限" data-options="iconCls:'icon-element'" 
				    style="padding:20px;">建设中..</div>
				<div title="访问权限" data-options="iconCls:'icon-url'"
				    style="padding:20px;">建设中..</div>
			</div>

		</div>

	</div>
</body>
</html>