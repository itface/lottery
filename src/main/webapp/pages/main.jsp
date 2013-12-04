<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"  type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<style>
.top{
	height:40px;
}
.tbar{
	text-align:right;
}
</style>
</head>
<body>
	<div class="top">
		<div class='tbar'>
			<div style="float:right;width:80px"><a class='prizeSetting' href='javascript:void(0);'>设置奖项</a></div>
			<div style="float:right;width:80px"><a class='impExcel' href='javascript:void(0);'>导入excel</a></div>
			<div style="float:right;width:80px"><form:select id="prizelist"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizetype"/></div>
		</div>
		<div style="clear:both;text-align:center;padding-top:30px;"><input class='action' type='button' value='开始' style='width:550px;height:30px'/></div>
		<div  class='userListTable' style='padding-top:20px;display:none'>
			<table class='userList' width="100%" border="1px solid #ccc" style='border-collapse: collapse;'>
			</table>
		</div>
	</div>
	<script>
	$(document).ready(function(){
		$('.action').bind('click',function(){
			var v = $('.action').val();
			var header = '<tr>';
				header += '<td>序号</td>';
				header += '<td>员工编号</td>';
				header += '<td>员工帐号</td>';
				header += '<td>员工姓名</td>';
				header += '<td>业务单元</td>';
				header += '<td>地域</td>';
				header += '</tr>';
			$('.userListTable').hide();
			$('.userList').html('');
			if(v=='停止'){
				var temp = (v=='开始'?$('.action').val('停止'):$('.action').val('开始'));
				$('.userList').html(header);
				$.ajax({
					url:'${ctx}/index/action/'+$('#prizelist').val(),
					async:false,
					success:function(obj){
						if(obj!=null&&obj.length>0){
							$(obj).each(function(i,v){
								var s = '<tr>';
								s += '<td>'+(i+1)+'</td>';
								s += '<td>'+v['usernumber']+'</td>';
								s += '<td>'+v['uid']+'</td>';
								s += '<td>'+v['username']+'</td>';
								s += '<td>'+v['ywdy2']+'</td>';
								s += '<td>'+v['region']+'</td>';
								s += '</tr>';
								$('.userList').append(s);
							});
						}
					}
				});
				$('.userListTable').show();
			}else{
				var temp = (v=='开始'?$('.action').val('停止'):$('.action').val('开始'));
			}
		});
		$('.impExcel').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 500,
		    	height: 250,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:pages/readExcel.jsp',
			    title:''
			});
		});
		$('.prizeSetting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 800,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:pages/prizeSetting.jsp',
			    title:''
			});
		});
	});
	</script>
  </body>
</html>