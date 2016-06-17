var xy = (function(){
	//
	function Construct(){
		//校验工具
		this.validateTool =(function (){
			//
			function Construct(){
				this.forms = [];
				this.rules = {
						checkByRegexp : {
							validator : function(vals,params,rule){
								return params.regexp?new RegExp(params.regexp).test(vals.this_val):false;
							},
							trueMessage : "true",
							falseMessage : "false"
						},
						checkByAjax : {
							validator : function(vals,params,rule){
								return params.regexp?new RegExp(params.regexp).test(vals.this_val):false;
							},
							message : ""
						}
				};
			}
			return new Construct();
		})();
		//
		var vt = this.validateTool;
		//初始化校验
		this.initValidations = function (){
			var forms = vt.forms;
			$("form").each(function(i,e){
				var validations = [];
				var validation_element = $(e).find(".validation");
				var validations_flag = new Array(validation_element.length);
				var index = 0;
				for(index = 0;index<validations_flag.length;index++){
					validations_flag[index]=false;
				}
				validation_element.each(function(i,e){
					var je = $(e);
					var options = eval("("+je.attr("validator-options")+")");
					var msg_id;
					if(!(msg_id=options.msg_id)){
						msg_id = je.attr("id")+"_msg";
					}
					var vals;
					if(!(vals=options.vals)){
						vals = {};
					}
					var rule = vt.rules[options.type];
					//检测onChange事件，校验数据合法性
					je.change(function(change_event){
						$("#"+msg_id).removeClass("li4");
						$("#"+msg_id).addClass("li3");
						$("#"+msg_id).text("validating...");
						vals.this_val = je.val();
						var rs = rule["validator"].call(this,vals,options.params);
						validations_flag[i] = rs;
						if(rs){
							$("#"+msg_id).removeClass("li3");
							$("#"+msg_id).addClass("li4");
							$("#"+msg_id).text(rule.trueMessage);
						}else{
							$("#"+msg_id).removeClass("li4");
							$("#"+msg_id).addClass("li3");
							$("#"+msg_id).text(rule.falseMessage);
						}
					});
					//表单提交时，需要再次校验时，调用的函数
					validations.push(function(){
						$("#"+msg_id).removeClass("li4");
						$("#"+msg_id).addClass("li3");
						$("#"+msg_id).text("validating...");
						vals.this_val = je.val();
						var rs = rule["validator"].call(this,vals,options.params);
						if(rs){
							$("#"+msg_id).removeClass("li3");
							$("#"+msg_id).addClass("li4");
							$("#"+msg_id).text(rule.trueMessage);
						}else{
							$("#"+msg_id).removeClass("li4");
							$("#"+msg_id).addClass("li3");
							$("#"+msg_id).text(rule.falseMessage);
						}
						return rs;
					});
				});
				forms.push([e,validations,validations_flag]);
			});
		};
		//对表单中校验元素，重新进行校验，并返回最终校验结果
		this.validation = function (id){
			var formValidations;
			$(vt.forms).each(function(i,e){
				if(e[0].id==id){
					formValidations = e[1];
				}
			});
			if(!formValidations){
				return false;
			}else{
				var flag = true;
				for(var vf in formValidations){
					var formValidation = formValidations[vf];
					flag = formValidation.call() && flag;
				}
				return flag;
			}
		}
		//对表单中校验元素，根据已经进行的校验结果，返回最终校验结果
		this.validation_flag = function (id){
			var formValidations_flag;
			$(vt.forms).each(function(i,e){
				if(e[0].id==id){
					formValidations_flag = e[2];
				}
			});
			if(!formValidations_flag){
				return false;
			}else{
				var flag = true;
				for(var vf in formValidations_flag){
					var formValidation_flag = formValidations_flag[vf];
					flag = formValidation_flag && flag;
				}
				return flag;
			}
		}
	}
	return new Construct();
})();


$.extend(xy.validateTool.rules,{
	//校验分两种，一种是自身校验，长度、非法字符等；一种是业务校验，需要和数据库中数据比对校验
	
	//第一种：校验合法性
	alpha:{     
         validator:function(vals,params,rule){     
             var regx =/^[a-zA-z\u00A1-\uFFFF]*$/;  
             return new RegExp(regx).test(vals.this_val);     
         },     
        trueMessage : "",
 		falseMessage:'只能输入字母.'     
     },
     alphanum:{     
         validator:function(vals,params,rule){     
             var regx = /^([a-zA-Z0-9])*$/;  
             return new RegExp(regx).test(vals.this_val);     
         },     
         trueMessage : "",
  		 falseMessage:'只能输入字母和数字.'     
     },     
     positive_int:{     
         validator:function(vals,params,rule){     
             var regx =/^[0-9]*[1-9][0-9]*$/;
             return new RegExp(regx).test(vals.this_val);
         },     
         trueMessage : "",
  		 falseMessage:'只能输入正整数.'     
     },     
     numeric:{     
         validator:function(vals,params,rule){     
             var regx = /^[0-9]*(\.[0-9]+)?$/;
             return new RegExp(regx).test(vals.this_val);     
         },     
         trueMessage : "",
  		 falseMessage:'只能输入数字.'     
     },
     number: {
         validator: function (vals,params,rule) {
             var regx = /^\d+$/;
             return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '请输入数字'
     },     
     chinese:{     
         validator:function(vals,params,rule){     
              var regx =/[^\u4E00-\u9FA5]/; 
              return new RegExp(regx).test(vals.this_val);    
         },     
     	trueMessage : "",
		falseMessage:'只能输入中文'     
     },
     CHS: {
         validator: function (vals,params,rule) {
        	 var regx = /^[\u0391-\uFFE5]+$/;
        	 return new RegExp(regx).test(vals.this_val); 
         },
         trueMessage : "",
 		 falseMessage: '请输入汉字'
     },
     ZIP: {
         validator: function (vals,params,rule) {
             var regx = /^[1-9]\d{5}$/;
             return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '邮政编码不存在'
     },
     QQ: {
         validator: function (vals,params,rule) {
             var regx = /^[1-9]\d{4,10}$/;
             return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: 'QQ号码不正确'
     },
     email : {
 		validator : function(vals,params,rule){
 			var regx = /^[_\-\.a-zA-Z0-9]+@([_\-a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,3}$/;
 			return new RegExp(regx).test(vals.this_val);
 		},
 		trueMessage : "",
 		falseMessage : "请填写正确的邮箱地址"
 	},
     mobile: {
         validator: function (vals,params,rule) {
             var regx = /^13\d{9}|14[57]\d{8}|15[012356789]\d{8}|18[01256789]\d{8}$/;
             return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '手机号码不正确'
     },
     /**
      匹配格式：
     11位手机号码
     3-4位区号，7-8位直播号码，1－4位分机号
      如：12345678901、1234-12345678-1234
     **/
     phone:{
         validator: function (vals,params,rule) {
         	var regx = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
         	return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '电话号码不正确'
         
     },
     loginName: {
         validator: function (vals,params,rule) {
             var regx =  /^[\u0391-\uFFE5\w]+$/;
             return new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '登录名称只允许汉字、英文字母、数字及下划线。'
     },
     /* 密码由字母和数字组成，至少6位 */
     safepass: {
         validator: function (vals,params,rule) {
        	 var regx = /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/;
        	 return !new RegExp(regx).test(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage: '密码由字母和数字组成，至少6位'
     },
     idcard: {
         validator: function (vals,params,rule) {
             return idCard(vals.this_val);
         },
         trueMessage : "",
 		 falseMessage:'请输入正确的身份证号码'
     },
	//第二种校验，前台校验，是前台调用后台方法，后台返回数据给前台告知是否合法；
	//		      后台校验，是前台提交数据到后，后台调用相关方法校验
	checkMobile : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.mobile.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkMobile.falseMessage=xy.validateTool.rules.mobile.falseMessage;
				return false;
			}else{
				flag = checkMobile();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	/*checkDept : {
		validator : function(vals,params,rule){
			var flag = checkDept();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	},*/
	checkStudent : {
		validator : function(vals,params,rule){
			var flag = checkStudent();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkSchoolName : {
		validator : function(vals,params,rule){
			var flag = checkSchoolName();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkPasswd : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.safepass.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkPasswd.falseMessage=xy.validateTool.rules.safepass.falseMessage;
				return false;
			}else{
				flag = checkPasswd();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkRePasswd : {
		validator : function(vals,params,rule){
			var flag = checkRePasswd();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkDeptPasswd : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.safepass.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkDeptPasswd.falseMessage=xy.validateTool.rules.safepass.falseMessage;
				return false;
			}else{
				flag = checkDeptPasswd();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkDeptRePasswd : {
		validator : function(vals,params,rule){
			var flag = checkDeptRePasswd();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkDeptName : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.loginName.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkDeptName.falseMessage=xy.validateTool.rules.loginName.falseMessage;
				return false;
			}else{
				flag = checkDeptName();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkDeptMobile : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.mobile.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkDeptMobile.falseMessage=xy.validateTool.rules.mobile.falseMessage;;
				return false;
			}else{
				flag = checkDeptMobile();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkDeptEmail : {
		validator : function(vals,params,rule){
			var flag = xy.validateTool.rules.email.validator(vals,params,rule);
			if(!flag){
				xy.validateTool.rules.checkDeptEmail.falseMessage=xy.validateTool.rules.email.falseMessage;
				return false;
			}else{
				flag = checkDeptEmail();
				return flag;
			}
		},
		trueMessage : "",
		falseMessage : ""
	},
	checkClass : {
		validator : function(vals,params,rule){
			var flag = checkClass();
			return flag;
		},
		trueMessage : "",
		falseMessage : ""
	}
});


var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
};

//时间格式化
function formatDate(date){
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	month = (month<10)?'0'+month:month;
	var day = date.getDate();
	day = (day<10)?'0'+day:day;
	var hour = date.getHours();
	hour = (hour<10)?'0'+hour:hour;
	var minute = date.getMinutes();
	minute = (minute<10)?'0'+minute:minute;
	var second = date.getSeconds();
	second = (second<10)?'0'+second:second;

	return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
}

//用户注册的机构
function deptCombo(selector){
	$(selector).combobox({
		url:'/deptCombo.do',
		valueField:'deptId',
		textField:'deptNameame'
	});
}

//机构绑定的班级
function classCombo(selector,newValue){
	$(selector).combobox({
		url:'/queryClassesList.do',
		queryParams:{"deptId":newValue},
		valueField:'classId',
		textField:'className'
	});
}

//机构绑定的老师
function techCombo(selector,newValue){
	$(selector).combobox({
		url:'/techCombo.do',
		queryParams:{"classId":newValue},
		valueField:'techId',
		textField:'techName'
	});
}

//老师发布的课程
function lessonGrid(selector,newValue,callback){
	$(selector).combogrid({
		panelWidth: 360,
		mode:'remote',
		multiple:false,
		idField:'lessonId',
		textField:'lessonName',
		url:'/queryLessonList.do',
		columns:[[
					{field:'lessonName',title:'课程名称',width:220},
					{field:'techNameOwn',title:'课件上传者',width:100}
				]],
		fitColumns: true,
		onLoadSuccess : function(data) {
			if (callback) {
				callback();
			}}
	});
}


/**
 * 通用异步form 提交
 * @param fmSelector form 选择器
 * @param urls       执行url 
 * @param callback   执行成功时的回调函数
 * @param btnID   按钮ID 暂时不可用 提交后恢复
 */
function fmSubmit(fmSelector,urls,btnID,complete){
	var hasBtn=false;
	if(btnID){
		hasBtn=true;
		$(btnID).linkbutton("disable");
	}
	
	// $(fmSelector).ajaxSubmit({
	//.form("submit",{
    $(fmSelector).form("submit",{
    		  type:'post',
    		  url: urls,
			  onSubmit: function(){
				  var flag=$(this).form("validate");
				  if(!flag&&hasBtn)$(btnID).linkbutton("enable");
				  if(flag)$.messager.progress({msg:"处理中.."}); 
				  return flag;
			  },
			 success: function(rs){
				 try {
					    var result = eval("("+rs+")");
					    if (result.success){
							$.messager.show({title: "成功",msg: result.msg});
							$('#importDlg').dialog('close');
						} else {
							$.messager.show({title: "失败",msg: result.msg});
						}
					
				} catch (e) {
					$.messager.show({title: "错误",msg: e.message});
				} finally{
					if(complete)complete();
					$.messager.progress("close");
					if(hasBtn)$(btnID).linkbutton("enable");
				}
			}
		 });
}

function fmSubmitXY(fmSelector,urls){
    $(fmSelector).form("submit",{
    		  type:'post',
    		  url: urls,
			  onSubmit: function(){
				  var flag=$(this).form("validate");
				  if(flag)$.messager.progress({msg:"处理中.."}); 
				  return flag;
			  },
			 success: function(rs){
				 try {
					  var result = eval("("+rs+")");
					  if (result.success){
						  $.messager.alert("提示",result.msg,"info",function(){
							window.location.href="/login/index.do"; 
						  });
					   } else {
						  $.messager.alert("警告",result.msg);
					 }
				} catch (e) {
					$.messager.show({title: "错误",msg: e.message});
				} finally{
					$.messager.progress("close");
				}
			}
		 });
}

/**
 * 通用AJAX POST 提交
 * 
 * @param url
 *            执行url
 * @param data
 *            参数
 * @param success
 *            成功回调函数
 */
function dgPost(url, data,success,complete) {		
	$.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType: "json",
        success: function (result) {
        	if(success)success(result);
        },
        complete: function (xhr) {
        	if(complete)complete(xhr);
        }
    });
}

/**=============================================================================
 * FOR FORM
 * 将FORM中值转化为JSON
 **=============================================================================*/
$.fn.form2json = function() {
	var serializedParams = this.serialize();
	var obj = paramString2obj(serializedParams);
	return obj;
};
$.fn.form2param = function() {
	var serializedParams = this.serialize();
	var obj = paramString2Param(serializedParams);
	return obj;
};

//将形如param1=x1&param2=x2&obj.name=name的串转化为JSON {param1:x1,param2:x2,obj:{name:name}}
function paramString2obj(serializedParams) {
	var obj = {};
	function evalThem(str) {
		var attributeName = str.split("=")[0];
		var attributeValue = str.split("=")[1];
		if (!attributeValue) {
			return;
		}
		var array = attributeName.split(".");
		for ( var i = 1; i < array.length; i++) {
			var tmpArray = Array();
			tmpArray.push("obj");
			for ( var j = 0; j < i; j++) {
				tmpArray.push(array[j]);
			}

			var evalString = tmpArray.join(".");
			evalString=decodeURIComponent(evalString);
			if (!eval(evalString)) {
				eval(evalString + "={};");
			}
		};
		var attr=attributeValue.replace(/\+/g, '%20');
		attr=decodeURIComponent(attr);
		attr=attr.replace(/\n/g, '\\n');
		attr=attr.replace(/\r/g, '\\r');
		var expr="obj." + attributeName + "='" + attr + "';";
		eval(expr);
	};
	var properties = serializedParams.split("&");
	for ( var i = 0; i < properties.length; i++) {
		evalThem(properties[i]);
	};
	return obj;
}

//将形如param1=x1&param2=x2&obj.name=name的串转化为JSON {param1:x1,param2:x2,obj.name:name}}
function paramString2Param(serializedParams) {
	var obj = {};
	function evalThem(str) {
		var attributeName = str.split("=")[0];
		//attributeName=attributeName.replace(/\./g, "\\\\.");// obj.name 转换为obj\\.name=
		var attributeValue = str.split("=")[1];
		if (!attributeValue) {
			return;
		}
		obj[attributeName]=decodeURIComponent(attributeValue.replace(/\+/g, '%20'));
	};
	var properties = serializedParams.split("&");
	for ( var i = 0; i < properties.length; i++) {
		evalThem(properties[i]);
	};
	return obj;
}