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
	<form action='${ctx}/index/historyprizepage'>
		<div>
			抽奖名称:<form:select id="prizeSerials" value="${prizeSerialid}"  name="prizeSerials" path="prizeSerials"  items="${prizeSerials}" itemValue="num" itemLabel="name"/><button/>查询</button>
		</div>
		<div class="wr_table" style="width:1150px;">
			<table style='border-collapse: collapse;' border="0" cellspacing="0" cellpadding="0" width="100%">
				<thead class="wr_table_header">
		          <tr class="wr-table-hd-inner">
					<td width='5%'>序号</td>
					<td width='11%'>抽奖活动批号</td>
					<td width='6%'>抽奖次数</td>
					<td width='6%'>姓名</td>
					<td width='6%'>员工编号</td>
					<td width='5%'>帐号</td>
					<td width='20%'>部门</td>
					<td width='4%'>地域</td>
					<td width='10%'>业务单元</td>
					<td width='10%'>业务单元2</td>
					<td width='10%'>奖项名称</td>
					<td width='8%'>奖品</td>
				</tr>
				<c:forEach items="${prizelist}" var="prize" varStatus="status">
					<tr class="wr-table-td-inner wr-table-tr-row">
						<td>${status.index+1}</td>
						<td>${prize.prizeserialnum}</td>
						<td>${prize.indexordername}</td>
						<td>${prize.username}</td>
						<td>${prize.usernumber}</td>
						<td>${prize.uid}</td>
						<td>${prize.dept}</td>
						<td>${prize.region}</td>
						<td>${prize.ywdy}</td>
						<td>${prize.ywdy2}</td>
						<td>${prize.prizename}</td>
						<td>${prize.jp}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form>
	
</body>
<script>
$(document).ready(function() {
	  
});
</script>
</html>