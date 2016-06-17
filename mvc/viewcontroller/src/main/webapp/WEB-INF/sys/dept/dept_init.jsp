<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML">
<html>
	<head>
		<title>机构注册</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<sys:resource />
		<script type="text/javascript">
</script>

	</head>

	<body>
			<form id="fm" action="addDept.do" method="post"> 
				机构名称<input type="text" name="dept_name" id="dept_name"/>
				机构邮箱<input type="text" name="dept_email" id="dept_email"/>
				机构电话<input type="text" name="dept_mobile" id="dept_mobile">
				<input type="submit" value="注册"/>
			</form>
	</body>
</html>
