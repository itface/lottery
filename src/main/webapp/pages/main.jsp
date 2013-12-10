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
<body onload="$('#prizelist').trigger('change');">
	<div class="top">
		<div style="text-align:center;height:50px;font-weight: bolder;font-size: 30px;">
			${title}
		</div>
		<div class='tbar'>
			<!-- 
			<div style="float:right;width:80px"><a class='prizeSetting' href='javascript:void(0);'>设置奖项</a></div>
			<div style="float:right;width:80px"><a class='impExcel' href='javascript:void(0);'>导入excel</a></div>
			<div style="float:right;width:80px"><a class='initAll' href='javascript:void(0);'>初始化</a></div>
			-->
			<div style="float:right;width:100px"><a class='setting' href='javascript:void(0);'>系统设置</a></div>
			<div style="float:right;width:140px"><a class='endprize' href='javascript:void(0);'>结束本次抽奖活动</a></div>
			<div style="float:right;width:140px"><a class='initUser' href='javascript:void(0);'>初始化用户状态</a></div>
			
			<div style="float:right;width:140px"><a class='currentprize' href='javascript:void(0);'>查看现场中奖</a></div>
			<div style="float:right;width:140px"><a class='historyprize' href='javascript:void(0);'>查看历史中奖</a></div>
			
			<div style="float:right;width:140px"><a class='resultreport' href='javascript:void(0);'>查看中奖分布</a></div>
		</div>
		<div  style="clear:both;text-align:center;padding-top:30px;height:35px">
			<span><form:select id="prizelist"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizelistlabel"/></span>
			<span class='prizeclass'></span>
			<span style='width:160px'><input class='actionstart' type='button' value='开始' style='width:150px;height:30px'/></span>
			<span style='width:160px;margin-left:160px'><input class='actionend' type='button' value='停止' style='width:150px;height:30px;display:none'/></span>
		</div>
		<div style="text-align:center">		
			<img class='img' style='width:900px;height:400px;display:none;'/>	
		</div>
		<div  class='userListTable' style='clear:both;padding-top:20px;display:none'>
			<table class='userList' width="100%" border="1px solid #ccc" style='border-collapse: collapse;'>
			</table>
		</div>
	</div>
	<script>
	
	$(document).ready(function(){
		var initflag = ${initflag};
		var prizelistjson = ${prizelistjson};
		$('.endprize').bind('click',function(){
			if(confirm('结束抽奖后，您将只能在“查看历史中奖”中查看历史中奖记录，本批次导入的人员和初始化设置都将失效，请确认是否结束抽奖？')){
				$.ajax({
						url:'${ctx}/index/endprize',
						success:function(obj){
							alert('结束抽奖成功！');
						}
				});
			}
		});
		$('#prizelist').bind('change',function(){
			$('.userList').empty();
			$('.userListTable').hide();
			var id = $(this).val();
			if(prizelistjson!=null&&prizelistjson!=''&&prizelistjson!=[]){
				$('.img').show();
				$(prizelistjson).each(function(i,v){
					if(v['id']==id){
						var jppic = v['jppic'];
						if(jppic!=''){
							$('.img').attr('src','${ctx}/prizepic/'+jppic);
						}else{
							$('.img').attr('src','${ctx}/prizepic/default.png');
						}
						$('.prizeclass').html('中奖人数:'+v['prizenum']+"人，奖品："+v['jp']);
					}
				});
			}
		});
		$('.resultreport').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/resultreportpage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.currentprize').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/currentprizepage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.historyprize').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/historyprizepage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.setting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 800,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:false,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/initpage',
			    title:'',
			    close:function(){
			    	location=location;
			    }
			});
			$.dialog.data('dialog',dialog);
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
			if(initflag==false||initflag=='false'){
				alert('您还没有进行初始化设置，请到设置里进行初始化设置!');
				return false;
			}
			var prize = $('#prizelist').val();
			if(prize==null||prize==''||prize==undefined){
				alert('请选择要抽的奖项。');
				return false;
			}
			$('.actionstart').hide();
			$('.actionend').show();
			$('.userList').empty();
			$('.userListTable').hide();
			$('.img').hide();
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
				//async:false,
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
	});
	</script>
  </body>
</html>