<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/uploadify3.2.1/uploadify.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/blockUI/blockUI.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify3.2.1/jquery.uploadify.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/blockUI/blockUI.js'/>" ></script>
<style>

</style>
</head>
<body>
	<div>
		<form action="${ctx}/uploadify/uploadbg" method="post" enctype="multipart/form-data">
			<input id="uploadify" name="uploadify" type="file" />
		</form>
	</div>
</body>
<script>
$(document).ready(function() {
	  $('#uploadify').uploadify({
	    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
	    'uploader'    : '${ctx}/uploadify/uploadbg',
	    'fileTypeExts'	: '*.gif; *.jpg; *.png',
	    'fileObjName':'uploadify',
	    'buttonText' : '上传...',
	     'multi':false,
        'onUploadStart':function(){
	     	$(window).blockUI();
	     },
        'onUploadSuccess' : function(file, data, response) {
         	
	     	if(response==true){
				$(window).blockUI('remove');
	     		alert('上传成功!');
	        }else{
	            	$(window).blockUI('remove');
    				alert('上传失败请检查文件是否合法!');
	         }
         }
	  });
});
</script>
</html>