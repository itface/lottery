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
		<div style="padding-left: 25px;padding-top: 30px;">
			抽奖名称:<form:select id="prizeSerials" value="${prizeSerialid}" style='width: 200px;'  name="prizeSerials" path="prizeSerials"  items="${prizeSerials}" itemValue="num" itemLabel="name"/><button/>查询</button>
		</div>
		<div class="wr_table userListTable">
			${resulthtml}
		</div>
	</form>
	
</body>
<script>
$(document).ready(function() {
	  
});
</script>
</html>