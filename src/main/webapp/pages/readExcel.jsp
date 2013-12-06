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
	<div>
		<form action="${ctx}/uploadify/upload" method="post" enctype="multipart/form-data">
			<input id="uploadify" name="uploadify" type="file" />
		</form>
	</div>
</body>
<script>
$(document).ready(function() {
	  $('#uploadify').uploadify({
	    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
	    'uploader'    : '${ctx}/uploadify/upload',
	    'fileTypeExts'	: '*.xls',
	    'fileObjName':'uploadify',
	    'buttonText' : '上传人员名单...',
	     'multi':false,
	     'onUploadComplete' : function(file) {
            alert('导入成功!');
        }
	  		
	  });
});
</script>
</html>