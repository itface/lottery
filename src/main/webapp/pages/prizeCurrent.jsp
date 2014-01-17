<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/table/table.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<style>

</style>
</head>
<body>
	<div>
		抽奖活动：2014金山年会（批号）
	</div>
	<div>
		三等奖 第一次 尾号：9
	</div>
	<div>
		三等奖 第一次 尾号：7
	</div>
	<div>
		二等奖 第一次 
		1234  5678  8970  6767  5443
		1234  5678  8970  6767  5443
	</div>
	<div class="wr_table" style="width:1150px;">
		<table style='border-collapse: collapse;' border="0" cellspacing="0" cellpadding="0" width="100%">
			<thead class="wr_table_header">
		          <tr class="wr-table-hd-inner">
		           	<td width='5%'>序号</td>
					<td width='10%'>奖项名称</td>
					<td width='10%'>奖品</td>
					<td width='7%'>抽奖次数</td>
					<td width='10%'>姓名</td>
					<td width='10%'>员工编号</td>
					<td width='7%'>地域</td>
					<td width='15%'>业务单元</td>
					<td width='20%'>抽奖活动批号</td>
		          </tr>
	        </thead>
	        <!-- 
			<tr>
				<td width='5%'>序号</td>
				<td width='10%'>抽奖活动批号</td>
				<td width='7%'>抽奖次数</td>
				<td width='10%'>姓名</td>
				<td width='10%'>员工编号</td>
				<td width='10%'>帐号</td>
				<td width='7%'>地域</td>
				<td width='15%'>业务单元</td>
				<td width='10%'>奖项名称</td>
				<td width='10%'>奖品</td>
			</tr>
			 -->
			<c:forEach items="${prizelist}" var="prize" varStatus="status">
				<tr class="wr-table-td-inner wr-table-tr-row">
					<td >${status.index+1}</td>
					<td >${prize.prizename}</td>
					<td >${prize.jp}</td>
					<td >${prize.indexordername}</td>
					<td >${prize.username}</td>
					<td >${prize.usernumber}</td>
					<td >${prize.region}</td>
					<td >${prize.ywdy}</td>
					<td >${prize.prizeserialnum}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
<script>
$(document).ready(function() {
	  
});
</script>
</html>