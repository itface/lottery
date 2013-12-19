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
	<div style="height:100px">
		<form action="${ctx}/uploadify/uploadmusic1" method="post" enctype="multipart/form-data">
			<input id="uploadify1" name="uploadify1" type="file" />
		</form>
	</div>
	<div style="height:100px">
		<form action="${ctx}/uploadify/uploadmusic1" method="post" enctype="multipart/form-data">
			<input id="uploadify2" name="uploadify2" type="file" />
		</form>
	</div>
	<div style="height:100px">
		<form action="${ctx}/uploadify/uploadmusic3" method="post" enctype="multipart/form-data">
			<input id="uploadify3" name="uploadify3" type="file" />
		</form>
	</div>
</body>
<script>
$(document).ready(function() {
	  $('#uploadify1').uploadify({
	    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
	    'uploader'    : '${ctx}/uploadify/uploadmusic1',
	    'fileTypeExts'	: '*.mp3; *.wav; *.mid',
	    'fileObjName':'uploadify1',
	    'buttonText' : '上传背景音乐...',
	     'multi':false,
	     'onUploadComplete' : function(file) {
            alert('上传成功!');
        }
	  		
	  });
	  $('#uploadify2').uploadify({
	    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
	    'uploader'    : '${ctx}/uploadify/uploadmusic2',
	    'fileTypeExts'	: '*.mp3; *.wav; *.mid',
	    'fileObjName':'uploadify2',
	    'buttonText' : '上传抽奖音乐...',
	     'multi':false,
	     'onUploadComplete' : function(file) {
            alert('上传成功!');
        }
	  		
	  });
	   $('#uploadify3').uploadify({
	    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
	    'uploader'    : '${ctx}/uploadify/uploadmusic3',
	    'fileTypeExts'	: '*.mp3; *.wav; *.mid',
	    'fileObjName':'uploadify3',
	    'buttonText' : '上传中奖音乐...',
	     'multi':false,
	     'onUploadComplete' : function(file) {
            alert('上传成功!');
        }
	  		
	  });
});
</script>
</html>