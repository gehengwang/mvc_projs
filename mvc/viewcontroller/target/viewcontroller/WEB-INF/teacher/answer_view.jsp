<!doctype html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*,education.model.*" pageEncoding="UTF-8" %>
<html>
<head>
<meta name="description" content="学生答题列表" />
<meta http-equiv="cleartype" content="on" />
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
table.gridtable {
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #ccc;
	border-collapse: collapse;
	width: 100%;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #ccc;
	background-color: #dedede;
}
table.gridtable td{
	border-color: #ccc;
	border-width: 1px;
	border-style: solid;
	font-size:56px ;
	font-weight:bold ;
	word-break:keep-all;
}
.fontStyle{
	font-size:56px;
	font-weight:bold;
}
</style>
</head>

<body>
             <table class="gridtable">
          <tr>
          	<th>学生</th>
          	<th>答题结果</th>
          	<th>学生答案</th>
          </tr>
          <% 
	       List<Answer> arrayList = (List<Answer>)request.getAttribute("listAnswer");
           if(arrayList!=null){
            for(int i=0;i<arrayList.size()-1;i++){
           %> 	
           	<tr>
           	<td><%= arrayList.get(i).getStuName() %></td>
           	<td ><% 
           		String answerResult = arrayList.get(i).getAnswerResult();
           		String answerResultName = arrayList.get(i).getAnswerResultName();
           		if("0".equals(answerResult)){
           			answerResultName = "<font color='red' class='fontStyle'>"+answerResultName+"</font>";
           		}else if("1".equals(answerResult)){
           			answerResultName = answerResultName;
           		}else{
           			answerResultName = "<font color='blue' class='fontStyle'>"+answerResultName+"</font>";
           		}
           		%><%=answerResultName %></td>
           	<td><div class="fontStyle" style="width:100%" contenteditable="true"> <% 
           		int[][] errorFlagShow = arrayList.get(i).getErrorFlagShow();
           		String stuAnswer = arrayList.get(i).getStuAnswer();
           		String fontStart = "<font color='red' class='fontStyle'>";//标注颜色
           		String fontEnd = "</font>";
    			for(int j=0;j<errorFlagShow.length;j++){
       				int flag0 = errorFlagShow[j][0];
       				int flag1 = errorFlagShow[j][1];
       				//按照指定位置截取字符串，并加上标注的html字符串
       				stuAnswer = stuAnswer.substring(0,flag0)
       						+fontStart
       						+stuAnswer.substring(flag0,flag1+1)
       						+fontEnd
       						+stuAnswer.substring(flag1+1)
       						;
       			}
           		%> <%=stuAnswer.replaceAll("\n","<br>") %></div>
           		</td>
           	</tr>
           	<%}%>
            <tr>
            	<td colspan="3"><font class='fontStyle' color="blue" style="font-style: italic;">
            			未完成：<%= arrayList.get(arrayList.size()-1).getStuName()%>
            	</font></td>
            </tr>
           <%}%>
		  		</table>
</body>
</html>