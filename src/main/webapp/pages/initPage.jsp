<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/uploadify3.2.1/uploadify.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.jqgrid.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/jqgrid/css/jquery.ui.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/blockUI/blockUI.css'/>">
<script type="text/javascript" src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/grid.locale-cn.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/jquery.jqGrid.src.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/jqgrid/jqgrid_extend.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/uploadify3.2.1/jquery.uploadify.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/script/blockUI/blockUI.js'/>" ></script>
<style>

</style>
</head>
<body>
	<div style='font-size: 12px;font-family: 宋体;'>
		抽奖活动名称:<input type='text' id='name' value='${name}' size=50/>
		<span style='font-size: 12px;color: red;' class='titlenotice'>本批次抽奖活动正在进行中，请谨慎修改!</span>
	</div>
	<div style="padding-top:25px">
		<table id="list"></table>
	</div>
	<div style='padding-top: 10px;font-size: 12px;font-family: 宋体;' class="numberpool">
		<div>尾号范围  从：<input id='suffixnumfrom' value='${suffixnumfrom}' type='text' style='width: 60px;' onchange='setNumOfSuffixnum()'>&nbsp;到&nbsp;<input id='suffixnumto' value='${suffixnumto}' type='text' style='width: 60px;' onchange='setNumOfSuffixnum()'> 不包含&nbsp;<input type='text' id='suffixnumexclude' value='${suffixnumexclude}' style='width: 220px;' onchange='validateSuffixnumexclude()'>共<span class='totalsuffixnum' style='display: inline-block;width: 40px;'></span>个&nbsp;<span style='color:#ccc'>发给参与现场抽奖人员的号码的尾号</span></div>
		<div>奖池范围 从：<input id='numberpoolfrom' value='${numberpoolfrom}' type='text' style='width: 60px;' onchange='setNumOfNumber()'>&nbsp;到&nbsp;<input id='numberpoolto' value='${numberpoolto}' type='text' style='width: 60px;' onchange='setNumOfNumber()'> 不包含&nbsp;<input type='text' id='numberpoolexclude' value='${numberpoolexclude}' style='width: 220px;' onchange='validateNumberpoolexclude()'>共<span class='totalnum' style='display: inline-block;width: 40px;'></span>个&nbsp;<span style='color:#ccc'>发给参与现场奖奖人员的号码</span></div>
	</div>
	<div style="padding-top:25px">
		<table id="list2"></table>
	</div>
	<div style="padding-top:25px;height:125px;" class="uploadform">
		<form action="${ctx}/uploadify/upload" method="post" enctype="multipart/form-data">
			<input id="uploadify" name="uploadify" type="file" />
			<span style='font-size: 12px;color: red;font-family: 宋体;'>注意：导入时系统将先清空全部抽奖人员，再导入新的抽奖人员。</span>
		</form>
	</div>
	<div class='usernumdiv'>
	</div>
	<div style="padding-top:5px">
		<table id="list3"></table>
	</div>
</body>
<script>

		 
		 var pnum=${pnum};//活动批次号
		 var uploadshow = ${uploadshow};
		 var usernum=0;
		 var prizeuserofnum=${prizeuserofnum};
		 init();
		 function init(){
		 	initUplodify();
		 	initNotice();
		 	setUserNum();
		 	setRangeReadonly();
		 }
		 function initNotice(){
		 	if(uploadshow==true){
		 		$('.titlenotice').hide();
		 	}else{
		 		$('.titlenotice').show();
		 	}
		 }
		 function setRangeReadonly(){
		 	if(prizeuserofnum==true){
		 		$('.numberpool input').attr('readonly','readonly');
		 	}else{
		 		$('.numberpool input').removeAttr('readonly');
		 	}
		 }
		  function initUplodify(){
		  	if(uploadshow==true){
		  		$('.uploadform').show();
			  	 $('#uploadify').uploadify({
				    'swf'  : '${ctx}/resources/script/uploadify3.2.1/uploadify.swf',
				    'uploader'    : '${ctx}/uploadify/upload',
				    'fileTypeExts'	: '*.xls',
				    'buttonText' : '上传抽奖人员名单...',
				    'fileObjName':'uploadify',
				    'removeCompleted' : false,
				     'multi':false,
				     'onUploadStart':function(){
				     	$(window).blockUI();
				     },
			        'onUploadSuccess' : function(file, data, response) {
			        	if(response==true){
				      		 alert('导入成功!');
				            $(window).blockUI('remove');
			           }else{
			           		alert('导入失败，请检查excel内容格式！');
				            $(window).blockUI('remove');
			           }
			           setUserNum();
			         }
				  });
				  
		  	}else{
		  		$('.uploadform').hide();
		  	}
		  }
		  function setUserNum(){
		  	$.ajax({
				url:'${ctx}/index/getusernum',
				type:'GET',
				async:false,
				cache:false,
				success:function(num){
					usernum=num;
					if(uploadshow==true){
						$('#uploadify').css('float','left');
						$('.usernum').remove();
					  	$('#uploadify').after("<div class='usernum' style='padding: 10px 10px 10px 150px;font-size: 12px;font-family: 宋体;'>人员数： "+num+"</div>");
					  	$('#uploadify-queue').css('clear','both');
					}else{
						$('.usernum').remove();
						$('.usernumdiv').append("<div class='usernum' style='padding:10px;font-size: 12px;font-family: 宋体;'>人员数 ："+num+"</div>");
					
					}
				}
			});
		  }
		  function validateNumberpoolexclude(){
		  	var v = $('#numberpoolexclude').val();
		  	var r=/^\d+(;\d+)*$/;
		  	if(v!=''&&!r.test(v)){
		  		alert('奖池范围的【不包含】必须是1个数字或者多个数字以;号分隔');
		  		return false;
		  	}
		  	return true;
		  }
		  function validateSuffixnumexclude(){
		  	var v = $('#suffixnumexclude').val();
		  	var r=/^\d(;\d)*$/;
		  	if(v!=''&&!r.test(v)){
		  		alert('尾号范围的【不包含】必须是0到9之间的一个数字或者多个数字以;号分隔');
		  		return false;
		  	}
		  	return true;
		  }
		  function setNumOfSuffixnum(){
		  	var suffixnumfrom = $('#suffixnumfrom').val();
			var suffixnumto = $('#suffixnumto').val();
			var r = /^\d+$/;//非负整数
			if(suffixnumfrom!=''&&suffixnumto!=''){
				if(r.test(suffixnumfrom)&&r.test(suffixnumto)&&suffixnumfrom>=0&&suffixnumfrom<=9&&suffixnumto>=0&&suffixnumto<=9&&suffixnumfrom<=suffixnumto){
					var num = parseInt(suffixnumto)-parseInt(suffixnumfrom)+1;
					$('.totalsuffixnum').html(num);
				}
			}
		  }
		  function setNumOfNumber(){
		    var numberpoolfrom = $('#numberpoolfrom').val();
			var numberpoolto = $('#numberpoolto').val();
			var r = /^\d+$/;//非负整数
			if(numberpoolfrom!=''&&numberpoolto!=''){
				if(r.test(numberpoolfrom)&&r.test(numberpoolto)&&numberpoolfrom<=numberpoolto){
					var num = parseInt(numberpoolto)-parseInt(numberpoolfrom)+1;
					$('.totalnum').html(num);
				}
			}
		  }
		function saveData(){
			var pname = $('#name').val();
			var suffixnumfrom = $('#suffixnumfrom').val();
			var suffixnumto = $('#suffixnumto').val();
			var suffixnumexclude = $('#suffixnumexclude').val();
			var numberpoolfrom = $('#numberpoolfrom').val();
			var numberpoolto = $('#numberpoolto').val();
			var numberpoolexclude = $('#numberpoolexclude').val();
			var r = /^\d+$/;//非负整数
			if(suffixnumfrom!=''&&(!r.test(suffixnumfrom)||suffixnumfrom>9)){
					alert('尾号范围必须是0到9之间的整数');
					return false;
			}
			if(suffixnumto!=''&&(!r.test(suffixnumto)||(suffixnumto>9)||(suffixnumfrom>suffixnumto))){
				alert('【尾号范围到】必须是大于等于【尾号范围从】的整数');
				return false;
			}
			if((suffixnumfrom!=''&&suffixnumto=='')||(suffixnumfrom==''&&suffixnumto!='')){
				alert('【尾号范围】设置不完整');
				return false;
			}
			
			if(numberpoolfrom!=''&&(!r.test(numberpoolfrom))){
				alert('奖池范围必须是整数');
				return false;
			}
			if(numberpoolto!=''&&(!r.test(numberpoolto)||(numberpoolfrom>numberpoolto))){
				alert('【奖池范围到】必须是大于等于【奖池范围从】的整数');
				return false;
			}
			if((numberpoolto!=''&&numberpoolfrom=='')||(numberpoolto==''&&numberpoolfrom!='')){
				alert('【尾号范围】设置不完整');
				return false;
			}
			if(!validateNumberpoolexclude()){
				return false;
			}
			if(!validateSuffixnumexclude()){
				return false;
			}
			if((suffixnumfrom!=''&&suffixnumto!='')&&(numberpoolfrom==''&&numberpoolto=='')){
				alert('设置了【尾号范围】时【奖池范围】不能为空');
				return false;
			}
			var arr = $('#list').jqGrid('getRowData');
			if(arr.length>0&&(numberpoolfrom==''||numberpoolto=='')){
				alert('设置了现场奖项，【奖池范围】不能为空');
				return false;
			}
			suffixnumfrom=suffixnumfrom==''?-1:suffixnumfrom;
			suffixnumto=suffixnumto==''?-1:suffixnumto;
			numberpoolfrom=numberpoolfrom==''?-1:numberpoolfrom;
			numberpoolto=numberpoolto==''?-1:numberpoolto;
			if(pname!=null&&pname!=''&&pname!=undefined){
				var dataLength =$('#list').jqGrid('getRowData').length;
				if(dataLength<1){
					alert('请设置奖项。');
					return false;
				}
				if(usernum<=0){
					alert('请上传抽奖人员名单。');
					return false;
				}
				$.ajax({
					url:'${ctx}/index/saveprizeserial',
					type:'POST',
					cache:false,
					async:false,
					data:{name:pname,pnum:pnum,suffixnumfrom:suffixnumfrom,suffixnumto:suffixnumto,suffixnumexclude:suffixnumexclude,numberpoolfrom:numberpoolfrom,numberpoolto:numberpoolto,numberpoolexclude:numberpoolexclude},
					success:function(){
						parent.location=parent.location;
					}
				});
			}else{
				alert('请输入抽奖活动名称。');
			}
		}
		function updateSelectOptions(type){
			var selectedValue = $('#indexorder').val();
			var selectHtml = '';
			$.ajax({
				url:'${ctx}/prizesetting/getOrderSelection?indexorder='+selectedValue,
				type:'GET',
				async:false,
				cache:false,
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
		function setTotalprizenumDis(v){
			if(v=='号码'){
				$('#tr_prizenum').show();
				$('#tr_totalprizenum').show();
			}else{
				$('#tr_prizenum').hide();
				$('#tr_totalprizenum').hide();
			}
		}
		var basePath = '${ctx}/prizesetting';
		jQuery("#list").jqGrid({
			url:basePath+"?type=num",
		   	ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		    datatype: 'json',
		    mtype: 'GET',
		    jsonReader:{
		    	repeatitems:false
		    },
			autowidth: false,
		    width:920,
		   	height:110,
		   colNames: ['ID','状态','奖项类别','奖项名称','总中奖数','每次中奖人数','奖品','奖品照片文件名','顺序','说明'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true, width:1,key:true},
		   		{name:'status',index:'status',hidden:true},
		   		{name:'prizetype',index:'prizetype',editable:true,edittype:'select', editoptions:{value:"尾号:尾号，每次在0-9之中抽一个数;号码:号码，每人的抽奖票上有一个唯一的数字编号",dataInit:function(el){$(el).bind('change',function(){setTotalprizenumDis($(el).val());});}}},
		   		{name:'prizename',index:'prizename',editable:true,editrules:{required:true}},
		   		{name:'totalprizenum',index:'totalprizenum',editable:true,editrules:{integer:true},formoptions:{elmsuffix:"<span style='color:red'>为空，不限制抽奖次数</span>" }},
		   		{name:'prizenum',index:'prizenum',editable:true,editrules:{integer:true},formoptions:{elmsuffix:"<span style='color:red'>总中奖数必须是每次中奖数的倍数</span>" }},
		   		{name:'jp',index:'jp',editable:true,editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>" }}},
		   		{name:'jppic',index:'jppic',width:200,editable:true},
		   		{name:'indexorder',index:'indexorder',editable:true,edittype:'custom',editoptions:{custom_element:my_input,custom_value:my_value},editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>"}}},
		   		{name:'sm',index:'sm',editable:true}
		   	],
		   	pginput:false,
		   	toppager: true,
		    rownumbers:true,
		    viewrecords: true,
		    multiselect:true,
		    editurl:'',
		    caption:'设置现场奖项(以下奖项不重复中奖)',
			multiselect: true,
			ondblClickRow:function(rowid,iCol,cellcontent,e){
		    	//editData为添加的参数，是为了让参数能正常的put到后台
		    	$('#list').jqGrid('editGridRow',rowid,{editData:{_method:'put'},top:150,left:200,width:435,reloadAfterSubmit:true,modal:true,closeAfterEdit:true,recreateForm:true,mtype: "POST", url: basePath,viewPagerButtons:false,afterComplete:function(){jQuery("#list2").trigger("reloadGrid");},beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},afterShowForm:function(){updateSelectOptions();setTotalprizenumDis($('#prizetype').val());}});//var postdata=jQuery('#monitorGrid').jqGrid('getGridParam','postData');
		    }
		});
		jQuery("#list").jqGrid('navGrid','',{edit:false,cloneToTop:true},{},{mtype: "POST",top:150,left:200,width:435,recreateForm:true,closeAfterAdd:true,afterComplete:function(){jQuery("#list2").trigger("reloadGrid");},beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},reloadAfterSubmit:true,modal:true,url:basePath,viewPagerButtons:false,afterShowForm:function(){updateSelectOptions();setTotalprizenumDis($('#prizetype').val());},onclickSubmit:function(){return {list_id:0,id:0,pnum:pnum};}},{url:basePath,reloadAfterSubmit:true,jqModal:false});
		var topPagerDiv = $("#list_toppager")[0];
		$("#edit_list_top", topPagerDiv).remove();
		$("#list_toppager_center", topPagerDiv).remove();
		$("#search_list_top").remove();
		$(".ui-jqgrid-titlebar-close").remove();
		$(".ui-paging-info", topPagerDiv).remove();//
		
		
		var basePath2 = '${ctx}/prizesetting';
		jQuery("#list2").jqGrid({
			url:basePath2+"?type=user",
		   	ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		    datatype: 'json',
		    mtype: 'GET',
		    jsonReader:{
		    	repeatitems:false
		    },
			autowidth: false,
		    width:920,
		   	height:70,
		   colNames: ['ID','状态','奖项类别','奖项名称','总中奖数','每次中奖人数','奖品','奖品照片文件名','顺序','说明'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true, width:1,key:true},
		   		{name:'status',index:'status',hidden:true},
		   		{name:'prizetype',hidden:true,index:'prizetype',editable:true,editoptions:{value:"用户"}},
		   		{name:'prizename',index:'prizename',editable:true,editrules:{required:true}},
		   		{name:'totalprizenum',index:'totalprizenum',editable:true,editrules:{integer:true},formoptions:{elmsuffix:"<span style='color:red'>为空，不限制抽奖次数</span>" }},
		   		{name:'prizenum',index:'prizenum',editable:true,editrules:{integer:true},formoptions:{elmsuffix:"<span style='color:red'>总中奖数必须是每次中奖数的倍数</span>" }},
		   		{name:'jp',index:'jp',editable:true,editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>" }}},
		   		{name:'jppic',index:'jppic',width:200,editable:true},
		   		{name:'indexorder',index:'indexorder',editable:true,edittype:'custom',editoptions:{custom_element:my_input,custom_value:my_value},editrules:{required:true,formoptions:{elmsuffix:"  <font color=red>*</font>"}}},
		   		{name:'sm',index:'sm',editable:true}
		   	],
		   	pginput:false,
		   	toppager: true,
		    rownumbers:true,
		    viewrecords: true,
		    multiselect:true,
		    editurl:'',
		    caption:'设置人员奖项(以下奖项不重复中奖，可以跟上面的现场奖项重复中奖。)',
			multiselect: true,
			ondblClickRow:function(rowid,iCol,cellcontent,e){
		    	//editData为添加的参数，是为了让参数能正常的put到后台
		    	$('#list2').jqGrid('editGridRow',rowid,{editData:{_method:'put'},top:150,left:200,width:435,reloadAfterSubmit:true,modal:true,closeAfterEdit:true,recreateForm:true,mtype: "POST",afterComplete:function(){jQuery("#list").trigger("reloadGrid");}, url: basePath2,viewPagerButtons:false,beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},afterShowForm:function(){updateSelectOptions();}});//var postdata=jQuery('#monitorGrid').jqGrid('getGridParam','postData');
		    }
		});
		jQuery("#list2").jqGrid('navGrid','',{edit:false,cloneToTop:true},{},{mtype: "POST",top:150,left:200,width:435,recreateForm:true,closeAfterAdd:true,afterComplete:function(){jQuery("#list").trigger("reloadGrid");},beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},reloadAfterSubmit:true,modal:true,url:basePath2,viewPagerButtons:false,afterShowForm:function(){updateSelectOptions();},onclickSubmit:function(){return {list2_id:0,id:0,pnum:pnum};}},{url:basePath2,reloadAfterSubmit:true,jqModal:false});
		var topPagerDiv = $("#list2_toppager")[0];
		$("#edit_list2_top", topPagerDiv).remove();
		$("#list2_toppager_center", topPagerDiv).remove();
		$("#search_list2_top").remove();
		$(".ui-jqgrid-titlebar-close").remove();
		$(".ui-paging-info", topPagerDiv).remove();
		
		var basePath3 = '${ctx}/index/balancerule';
		jQuery("#list3").jqGrid({
			url:basePath3,
		   	ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		    datatype: 'json',
		    mtype: 'GET',
		    jsonReader:{
		    	repeatitems:false
		    },
			autowidth: false,
		    width:920,
		   	height:110,
		   colNames: ['ID','业务单元','地域','下限','已抽下限'],
		   	colModel:[
		   		{name:'id',index:'id',hidden:true, width:1,key:true},
		   		{name:'ywdy',index:'ywdy',editable:true,edittype:'select',editoptions:{value:getYwdySelecctString()}},
		   		{name:'region',index:'region',editable:true,edittype:'select',editoptions:{value:getRegionSelectString()}},
		   		{name:'min',index:'min',editable:true,editrules:{integer:true}},
		   		{name:'ycxx',index:'ycxx',hidden:true}
		   	],
		   	pginput:false,
		   	toppager: true,
		    rownumbers:true,
		    viewrecords: true,
		    multiselect:true,
		    editurl:'',
		    caption:'设置分组均衡规则(按条件分组并设置每组中奖人数下限)',
			multiselect: true,
			ondblClickRow:function(rowid,iCol,cellcontent,e){
		    	//editData为添加的参数，是为了让参数能正常的put到后台
		    	$('#list3').jqGrid('editGridRow',rowid,{editData:{_method:'put'},top:350,left:200,width:435,reloadAfterSubmit:true,modal:true,closeAfterEdit:true,recreateForm:true,mtype: "POST", url: basePath3,viewPagerButtons:false,beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},afterShowForm:function(){updateSelectOptions();}});//var postdata=jQuery('#monitorGrid').jqGrid('getGridParam','postData');
		    }
		});
		jQuery("#list3").jqGrid('navGrid','',{edit:false,cloneToTop:true},{},{mtype: "POST",top:350,left:200,width:435,recreateForm:true,closeAfterAdd:true,beforeSubmit:function(postdata, formid){if(!postdata.totalprizenum){postdata.totalprizenum=0;}if(!postdata.prizenum){postdata.prizenum=0;}return[true,''];},reloadAfterSubmit:true,modal:true,url:basePath3,viewPagerButtons:false,afterShowForm:function(){updateSelectOptions();},onclickSubmit:function(){return {list3_id:0,id:0,pnum:pnum};}},{url:basePath3,reloadAfterSubmit:true,jqModal:false});
		var topPagerDiv = $("#list3_toppager")[0];
		$("#edit_list3_top", topPagerDiv).remove();
		$("#list3_toppager_center", topPagerDiv).remove();
		$("#search_list3_top").remove();
		$(".ui-jqgrid-titlebar-close").remove();
		$(".ui-paging-info", topPagerDiv).remove();
		function getYwdySelecctString(){
			var select ='';
			$.ajax({
				url:'${ctx}/index/getYwdySelecctString',
				type:'GET',
				async:false,
				cache:false,
				success:function(s){
					select=s;
				}
			});
			return select;
		}
		function getRegionSelectString(){
			var select ='';
			$.ajax({
				url:'${ctx}/index/getRegionSelectString',
				type:'GET',
				async:false,
				cache:false,
				success:function(s){
					select=s;
				}
			});
			return select;
		}
		
</script>
</html>