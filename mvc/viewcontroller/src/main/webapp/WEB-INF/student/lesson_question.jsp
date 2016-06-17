<!doctype html>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>答题</title>
<link rel="stylesheet" type="text/css" href="/css/wrap.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
textarea 
{ 
width:100%; 
height:100%; 
}
</style>
<script type="text/javascript">
	var data = "";
	var classId = ${param.classId};
	$(function(){
		$.post("/questionList.do",{"lessonId":'${param.lessonId}',"classId":'${param.classId}'},function(result){
			data = result;
			//html把该标签的内容全部取出来，包含里面的html标签;
			//text和htm相似,但是不包含里面的html标签;
			//val取出表单元素的value值;
			$("#quesCount").html(data.length);
			$("#questionType").html(data[0].questionType);
			$("#question_no").val(data[0].questionNo);//json数据格式下标从0开始
			$("#question").val(1+"、"+data[0].question);
			$("#stu_answer").val(data[0].stuAnswer);
		},"json");
	});
	
	//上一题
	function doLast(){
		var index= parseInt($("#question_no").val());
		if(index==1){
			$.messager.alert('提示','已经是第一题了');
			return false;
		}
		$("#question_no").val(data[index-2].questionNo);
		$("#question").val(data[index-2].questionNo+"、"+data[index-2].question);
		$("#stu_answer").val(data[index-2].stuAnswer);
	}
	
	function doNext(){
		var index= parseInt($("#question_no").val());
		var stuAnswer = $.trim($("#stu_answer").val());

		if(stuAnswer!=null&&stuAnswer!=""){
			data[index-1].stuAnswer=stuAnswer;
			data[index-1].classId=classId;
			$.post("/saveStuAnswer.do",data[index-1],function(result){},"json");
		}
		
		
		if(index==data.length){
			$.messager.alert('提示','已经到最后一题');
			return false;
		}
		$("#question_no").val(data[index].questionNo);
		$("#question").val(data[index].questionNo+"、"+data[index].question);
		$("#stu_answer").val(data[index].stuAnswer);
	}
	
	function selectQuestion(){
		var index = parseInt($("#question_no").val());
		
		if(isNaN(index)){
			$.messager.alert('提示','请填入题号');
			return false;
		}
		if(index > data.length){
			$.messager.alert('提示','已超出最后一题,请重新选择');
			return false;
		}
		if(index < 0){
			$.messager.alert('提示','题号必须大于0');
			return false;
		}
		$("#question_no").val(data[index-1].questionNo);
		$("#question").val(data[index-1].questionNo+"、"+data[index-1].question);
		$("#stu_answer").val(data[index-1].stuAnswer);
	}
	
	//换行跳下一题
	/* function getKey(event)  
	{  
		//DOM事件：IE属性：keyCode、returnValue;标准event属性、方法：preventDefault
		if(event.keyCode==13){ 
			if ((navigator.userAgent.indexOf('MSIE') >= 0) 
				    && (navigator.userAgent.indexOf('Opera') < 0)){
				    event.returnValue = false;
				    doNext();
			}else{
				event.preventDefault();
			    doNext();
			}
		} 
	} */
</script>
</head>

<body>
<div class="body">
	<div class="header">
    	<h1><label  id="questionType"></label>	
    	开始答题：共<label id="quesCount"></label>题</h1>
		<a class="l-link" href="javascript:history.back();"><span>返回</span></a>
    </div>

 	<div class="main">
			<form id="fm">
               <div class="formbox">
               		<textarea style="border:5px solid #e6e6e6;resize:none;font-size:22px;font-weight:bold;" rows="3" cols="2"
               		name="question" id="question" readonly="readonly"></textarea>
               </div>
               <div class="formbox" >
                	<textarea style="border:5px solid #e6e6e6;resize:none;font-size:22px;font-weight:bold;" rows="3" cols="2"
                	name="stu_answer" id="stu_answer" placeholder="请输入答案"></textarea>
               </div>
            </form>   
             <div style="text-align:center;">
            	<input type="button" class="btn-f" value="上一题"   onclick="javascript:doLast();"/>
            	<input type="button" class="btn-f" value="下一题"   onclick="javascript:doNext();"/>
            	跳转到<input style="width:15%;border:2px solid #e6e6e6;" type="text" name="question_no" id="question_no"/>题
            	<input type="button" class="btn-f" value="GO"   onclick="javascript:selectQuestion();"/>
            </div>
        </div>
    </div>
</body>
</html>