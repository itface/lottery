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
			
			<div style="float:right;width:120px"><a class='initUser' href='javascript:void(0);'>初始化用户状态</a></div>
			<div style="float:right;width:80px"><a class='initAll' href='javascript:void(0);'>初始化</a></div>
			<div style="float:right;width:80px"><form:select id="prizelist"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizetype"/></div>
		</div>
		<div  style="clear:both;text-align:center;padding-top:30px;height:35px">
			<input class='actionstart' type='button' value='开始' style='width:250px;height:30px'/>
		</div>
		<div  style="clear:both;text-align:center;height:35px">
			<input class='actionend' type='button' value='停止' style='width:250px;height:30px;display:none;'/>
		</div>
		<div  class='userListTable' style='padding-top:20px;display:none'>
			<table class='userList' width="100%" border="1px solid #ccc" style='border-collapse: collapse;'>
			</table>
		</div>
	</div>
	<script>
	$(document).ready(function(){
		$('.initAll').bind('click',function(){
			$.ajax({
					url:'${ctx}/index/initall',
					success:function(obj){
						alert('初始化完毕！');
					}
			});
		});
		$('.initUser').bind('click',function(){
			$.ajax({
					url:'${ctx}/index/inituserstatus',
					success:function(obj){
						alert('用户状态初始化完毕！');
					}
			});
		});
		$('.actionstart').bind('click',function(){
			$('.actionstart').hide();
			$('.actionend').show();
			$('.userList').empty();
			$('.userListTable').hide();
		});
		$('.actionend').bind('click',function(){
			$('.actionstart').show();
			$('.actionend').hide();
			$('.userListTable').show();
			var s = '<tr>';
				s += '<td>序号</td>';
				s += '<td>员工编号</td>';
				s += '<td>员工帐号</td>';
				s += '<td>员工姓名</td>';
				s += '<td>业务单元</td>';
				s += '<td>地域</td>';
				s += '</tr>';
			$.ajax({
				url:'${ctx}/index/action/'+$('#prizelist').val(),
				async:false,
				success:function(obj){
					if(obj!=null&&obj.length>0){
						$(obj).each(function(i,v){
							s += '<tr>';
							s += '<td>'+(i+1)+'</td>';
							s += '<td>'+(v['usernumber']==null?'':v['usernumber'])+'</td>';
							s += '<td>'+(v['uid']==null?'':v['uid'])+'</td>';
							s += '<td>'+(v['username']==null?'':v['username'])+'</td>';
							s += '<td>'+(v['ywdy2']==null?'':v['ywdy2'])+'</td>';
							s += '<td>'+(v['region']==null?'':v['region'])+'</td>';
							s += '</tr>';
						});
						$('.userList').html(s);
					}
				}
			});
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
			    title:'',
			    close:function(){
			    	parent.location=parent.location;
			    }
			});
		});
	});
	</script>
  </body>
</html>