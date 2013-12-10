<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/uploadify3.2.1/uploadify.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify3.2.1/jquery.uploadify.min.js'/>"></script>
<style>

</style>
</head>
<body>
	<form action='${ctx}/index/historyprizepage'>
		<div>
			抽奖名称:<form:select id="prizeSerials"  name="prizeSerials" path="prizeSerials"  items="${prizeSerials}" itemValue="num" itemLabel="name"/><button/>查询</button>
		</div>
		<div>
			<table class='prizeclass' border="1px solid #ccc" style='border-collapse: collapse;' width='1150px'>
				<tr>
					<td width='10%'>抽奖顺序</td>
					<td width='15%'>抽奖批次</td>
					<td width='10%'>奖项名称</td>
					<td width='10%'>奖品</td>
					<td width='10%'>中奖人姓名</td>
					<td width='5%'>中奖人帐号</td>
					<td width='5%'>中奖人员工编号</td>
					<td width='10%'>部门</td>
					<td width='5%'>地域</td>
					<td width='10%'>业务单元</td>
					<td width='10%'>业务单元2</td>
				</tr>
				<c:forEach items="${prizelist}" var="prize">
					<tr>
						<td>${prize.indexordername}</td>
						<td>${prize.prizeserialnum}</td>
						<td>${prize.prizename}</td>
						<td>${prize.jp}</td>
						<td>${prize.username}</td>
						<td>${prize.uid}</td>
						<td>${prize.usernumber}</td>
						<td>${prize.dept}</td>
						<td>${prize.region}</td>
						<td>${prize.ywdy}</td>
						<td>${prize.ywdy2}</td>
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