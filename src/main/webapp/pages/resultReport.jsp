<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqplot/jquery.jqplot.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqplot/jquery.jqplot.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqplot/plugins/jqplot.pieRenderer.js'/>"></script>
<style>
body{
	font-size:12px;
}
</style>
</head>
<body>
	<div style='text-align:center;height:30px'>
		<span class='title'></span>
	</div>
	<div>
		<div id="chart3" style="margin-top:20px; margin-left:20px; width:400px; height:470px;float:left"></div>
		<div id="chart2" style="margin-top:20px; margin-left:20px; width:400px; height:470px;float:left"></div>
		<div id="chart1" style="margin-top:20px; margin-left:20px; width:400px; height:470px;float:left"></div>
	</div>
	<div>
		<div style="float:left;padding-top:20px;">
			<table border="1px solid #ccc" style="border-collapse:collapse;">
				<tr>
						<td width='100px'>业务单元分布</td>
						<c:forEach items="${ywdylist}" var="percentage">
								<td width='120px'>${percentage.name}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>抽奖人</td>
						<c:forEach items="${ywdylist}" var="percentage">
								<td>${percentage.percentOfAll}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>中奖人</td>
						<c:forEach items="${ywdylist}" var="percentage">
								<td>${percentage.percentOfPrize}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>差异</td>
						<c:forEach items="${ywdylist}" var="percentage">
								<td>${percentage.percentOfDiff}</td>
						</c:forEach>
				</tr>
			</table>
		</div>
		<div style="float:left;padding-top:20px;">
			<table border="1px solid #ccc" style="border-collapse:collapse;">
				<tr>
						<td width='80px'>地域分布</td>
						<c:forEach items="${dylist}" var="percentage">
								<td width='70px'>${percentage.name}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>抽奖人</td>
						<c:forEach items="${dylist}" var="percentage">
								<td>${percentage.percentOfAll}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>中奖人</td>
						<c:forEach items="${dylist}" var="percentage">
								<td>${percentage.percentOfPrize}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>差异</td>
						<c:forEach items="${dylist}" var="percentage">
								<td>${percentage.percentOfDiff}</td>
						</c:forEach>
				</tr>
			</table>
		</div>
		<div style="clear:both;padding-top:20px;">
			<table border="1px solid #ccc" style="border-collapse:collapse;">
				<tr>
						<td width='100px'>员工编号分布</td>
						<c:forEach items="${usernumberlist}" var="percentage">
								<td width='100px'>${percentage.name}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>抽奖人</td>
						<c:forEach items="${usernumberlist}" var="percentage">
								<td>${percentage.percentOfAll}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>中奖人</td>
						<c:forEach items="${usernumberlist}" var="percentage">
								<td>${percentage.percentOfPrize}</td>
						</c:forEach>
				</tr>
				<tr>
						<td>差异</td>
						<c:forEach items="${usernumberlist}" var="percentage">
								<td>${percentage.percentOfDiff}</td>
						</c:forEach>
				</tr>
			</table>
		</div>
	</div>
</body>
<script>
$(document).ready(function() {
	  $.ajax({
	  	url:'${ctx}/index/resultreport',
	  	cache:false,
	  	success:function(obj){
	  		if(obj!=null&&obj.length>0){
	  			$('.title').html('<span style="font-size:14px">参与抽奖的人共有${totaluser}，有'+obj.length+'个获奖人员</span>');
	  			var map = {};
	  			var map2 = {};
	  			var map3 = {};
	  			var arr = new Array();
	  			var arr2 = new Array();
	  			var arr3 = new Array();
	  			var pernum = 1000;
				$(obj).each(function(i,v){
					
					{	var usernum = v['usernumber'];
						while(usernum.indexOf('0')==0){
							usernum=usernum.substring(1);
						}
						var range = parseInt(usernum/pernum);
						var value= map[range];
						if(value!=null&&value!=''&&value!=undefined){
							map[range]=value+1;
						}else{
							map[range]=1;
						}
					}
					{
						var region = v['region'];
						var value= map2[region];
						if(value!=null&&value!=''&&value!=undefined){
							map2[region]=value+1;
						}else{
							map2[region]=1;
						}
					}
					{
						var ywdy = v['ywdy'];
						var value= map3[ywdy];
						if(value!=null&&value!=''&&value!=undefined){
							map3[ywdy]=value+1;
						}else{
							map3[ywdy]=1;
						}
					}
				});
				{
					for(var i in map){
						var tempArr = new Array();
						var start = (parseInt(i))*pernum+1;
						var end = (parseInt(i)+1)*pernum;
						var s = start+'-'+end;
						var num = map[i];
						tempArr.push(s);
						tempArr.push(num);
						arr.push(tempArr);
					}
					var plot1 = jQuery.jqplot('chart1', [arr],{ 
						  seriesDefaults: {
							renderer: jQuery.jqplot.PieRenderer, 
							rendererOptions: {
							  showDataLabels: true
							}
						  }, 
						  title:'中奖人员员工编号分布图',
						  legend: { show:true, location: 'e'}
						}
					  );
				}
				{
					for(var i in map2){
						var tempArr = new Array();
						tempArr.push(i);
						tempArr.push(map2[i]);
						arr2.push(tempArr);
					}
					var plot2 = jQuery.jqplot('chart2', [arr2],{ 
						  seriesDefaults: {
							renderer: jQuery.jqplot.PieRenderer, 
							rendererOptions: {
							  showDataLabels: true
							}
						  }, 
						  title:'中奖人员地域分布图',
						  legend: { show:true, location: 'e'}
						}
					  );
				}
				{
					for(var i in map3){
						var tempArr = new Array();
						tempArr.push(i);
						tempArr.push(map3[i]);
						arr3.push(tempArr);
					}
					var plot3 = jQuery.jqplot('chart3', [arr3],{ 
						  seriesDefaults: {
							renderer: jQuery.jqplot.PieRenderer, 
							rendererOptions: {
							  showDataLabels: true
							}
						  }, 
						  title:'中奖人员业务单元分布图',
						  legend: { show:true, location: 'e'}
						}
					  );
				}
			}
	  	}
	  });
});
</script>
</html>