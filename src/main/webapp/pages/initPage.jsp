<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/uploadify3.2.1/uploadify.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify3.2.1/jquery.uploadify.min.js'/>"></script>
<style>

</style>
</head>
<body>
	<div>
		本次抽奖名称:<input type='text' id='name' value='${name}'/>
	</div>
	<div style="padding-top:25px">
		<table id="list"></table>
	</div>
	<div style="padding-top:25px;height:100px;">
		<form action="${ctx}/uploadify/upload" method="post" enctype="multipart/form-data">
			<input id="uploadify" name="uploadify" type="file" />
		</form>
	</div>
	<div>
		<div style='bottom:5px;right:10px;position:absolute;'>
			<input class='initBtn' type='button' value='保存'/>
			<input class='cancelBtn' type='button' value='取消'/>
		</div>
	</div>
</body>
<script>
$(document).ready(function() {
		 init();
		 var pnum=${pnum};
		 function init(){
		 	initUplodify();
		 }
		  function initUplodify(){
			  $('#uploadify').uploadify({
			    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
			    'uploader'    : '${ctx}/uploadify/upload',
			    'fileTypeExts'	: '*.xls',
			    'buttonText' : '上传人员名单...',
			    'fileObjName':'uploadify',
			     'multi':false,
			     'onUploadComplete' : function(file) {
		            alert('导入成功!');
		        }
			  });
		  }
		function closeWin(){
			var api = frameElement.api;
			var  W = api.opener; 
			var dia = W.$.dialog.data('dialog');
			dia.close();
		}
		$('.cancelBtn').bind('click',function(){
			 closeWin();
		});
		$('.initBtn').bind('click',function(){
			var pname = $('#name').val();
			if(pname!=null&&pname!=''&&pname!=undefined){
				$.ajax({
					url:'${ctx}/index/saveprizeserial',
					type:'POST',
					data:{name:pname,pnum:pnum},
					success:function(){
						alert('保存成功.');
						parent.location=parent.location;
					}
				});
			}else{
				alert('抽奖名称不能为空');
			}
		});
		function updateSelectOptions(){
			var selectedValue = $('#indexorder').val();
			var selectHtml = '';
			$.ajax({
				url:'${ctx}/prizesetting/getOrderSelection?indexorder='+selectedValue,
				type:'GET',
				async:false,
				success:function(num){
					$('#indexorder').html(num);
				}
			});
		}
		function my_input(value, options) {
			return $("<select><option value='"+value+"'>"+value+"</select>");
		}
		function my_value(value) {
			return value.val();
		}
		var basePath = '${ctx}/prizesetting';
		jQuery("#list").jqGrid({
			url:basePath,
		   	ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		    datatype: 'json',
		    mtype: 'GET',
		    jsonReader:{
		    	repeatitems:false
		    },
			autowidth: false,
		    width:770,
		   	height:240,
		   colNames: ['ID','状态','名称','中奖人数','奖品','奖品照片名称','顺序','说明'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true, width:1,key:true},
		   		{name:'status',index:'status',hidden:true},
		   		{name:'prizename',index:'prizename',editable:true,editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>" }}},
		   		{name:'prizenum',index:'prizenum',editable:true,editrules:{integer:true,required:true,formoptions:{elmsuffix:"  <font color=red>*</font>" }}},
		   		{name:'jp',index:'jp',editable:true,editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>" }}},
		   		{name:'jppic',index:'jppic',editable:true},
		   		{name:'indexorder',index:'indexorder',editable:true,edittype:'custom',editoptions:{custom_element:my_input,custom_value:my_value},editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>"}}},
		   		{name:'sm',index:'sm',editable:true,editrules:{required:true}}
		   	],
		   	pginput:false,
		   	toppager: true,
		    rownumbers:true,
		    viewrecords: true,
		    multiselect:true,
		    editurl:'',
		    caption:'设置奖项',
			multiselect: true,
			ondblClickRow:function(rowid,iCol,cellcontent,e){
		    	//editData为添加的参数，是为了让参数能正常的put到后台
		    	$('#list').jqGrid('editGridRow',rowid,{editData:{_method:'put'},top:150,left:200,width:400,reloadAfterSubmit:true,modal:true,closeAfterEdit:true,recreateForm:true,mtype: "POST", url: basePath,viewPagerButtons:false,afterShowForm:function(){updateSelectOptions();}});//var postdata=jQuery('#monitorGrid').jqGrid('getGridParam','postData');
		    }
		});
		jQuery("#list").jqGrid('navGrid','',{edit:false,cloneToTop:true},{},{mtype: "POST",top:150,left:200,width:400,recreateForm:true,closeAfterAdd:true,reloadAfterSubmit:true,modal:true,url:basePath,viewPagerButtons:false,afterShowForm:function(){updateSelectOptions();},onclickSubmit:function(){return {list_id:0,id:0,pnum:pnum};}},{url:basePath,reloadAfterSubmit:true,jqModal:false});
		var topPagerDiv = $("#list_toppager")[0];
		$("#edit_list_top", topPagerDiv).remove();
		$("#list_toppager_center", topPagerDiv).remove();
		$("#search_list_top").remove();
		$(".ui-jqgrid-titlebar-close").remove();
		$(".ui-paging-info", topPagerDiv).remove();//
});
</script>
</html>