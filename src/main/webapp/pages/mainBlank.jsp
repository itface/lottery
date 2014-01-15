<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/blockUI/blockUI.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/zzsc/zzsc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/table/table.css'/>">
<script src="<c:url value='/resources/script/jquery-1.7.2.min.js'/>"  type="text/javascript"></script>
<script src="<c:url value='/resources/script/lhgdialog/lhgdialog.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/resources/script/blockUI/blockUI.js'/>" ></script>
<script src="<c:url value='/resources/script/audiojs/audio.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/zzsc/zzsc.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/script/zzsc/menu.js'/>" type="text/javascript"></script>
<style>

</style>
</head>
<body  style='background:url(${ctx}/resources/bg/${bgname})'>
<div style="display:block" id="musicplayer"></div>
<div class="title" style="display:none">${title}</div>
<ul id="jsddm">
  <li><a href="javascript:void(0);" style="background:url(${ctx}/resources/images/cog.png) no-repeat" >&nbsp;</a>
    <ul>
      <li><a href="javascript:void(0);" class='resultreport'>现场中奖分布图</a></li>
      <li><a href="javascript:void(0);" class='musicsetting'>设置音乐</a></li>
      <li><a href="javascript:void(0);" class='bgsetting'>设置背景图</a></li>
      <li><a href="javascript:void(0);" class='historyprize'>历史中奖纪录</a></li>
      <li><a href="javascript:void(0);" class='currentprize'>现场中奖纪录</a></li>
      <li><a href="javascript:void(0);" class='initUser'>重置人员状态</a></li>
      <li><a href="javascript:void(0);" class='endprize'>结束抽奖活动</a></li>
      <li><a href="javascript:void(0);" class='setting'>初始化设置</a></li>
      <li>&nbsp;</li>
    </ul>
</ul>



<div class="div_right">
  <div class="select">
    <form:select id="prizelist" class="select1"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizelistlabel"/>
  	
  </div>
  <div id="tagscloud"> 
  		
  </div>
</div>
	<script>
	
	$(document).ready(function(){
		var initflag = ${initflag};//是否进行了初始化，即Serialnum是否存在
		var prizelistjson = ${prizelistjson};//奖项
		init();
		function init(){
			$('#jsddm').easymenu();
			initLottery();
		}
		function initLottery(){
			$(document).lottery(new Array());
		}
		$('.bgsetting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 500,
		    	height: 200,
			    min:false,
			    max:false,
			    cancel:true,
			    ok:false,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/uploadify/uploadbgpage',
			    title:'设置背景图',
			    close:function(){
			    	location=location;
			    }
			});
		});
		$('.musicsetting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 600,
		    	height: 400,
			    min:false,
			    max:false,
			    cancel:true,
			    ok:false,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/uploadify/uploadmusicpage',
			    title:'设置音乐',
			    close:function(){
			    	location=location;
			    }
			});
		});
		$('.endprize').bind('click',function(){
			/*
			if(initflag==false||initflag=='false'){
				alert('亲，您还没有开始一次抽奖!');
				return false;
			}*/
			if(confirm('结束抽奖后，您将只能在“历史中奖记录”中查看历史中奖记录，本批次导入的人员和初始化设置都将失效，请确认是否结束抽奖？')){
				$(window).blockUI();
				$.ajax({
						url:'${ctx}/index/endprize',
						cache:false,
						async:false,
						success:function(obj){
							alert('本批次抽奖活动已结束！');
							location=location;
						}
				});
				$(window).blockUI('remove');
			}
		});
		$('#prizelist').bind('change',function(){
			//setPrizelist();
			var id = $('#prizelist').val();
			location.href="${ctx}/index?id="+id;
		});
		$('.resultreport').bind('click',function(){
			if(initflag==false||initflag=='false'){
				alert("亲，请现场抽奖后再查看现场中奖分布图。");
				return false;
			}
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/resultreportpage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.currentprize').bind('click',function(){
			if(initflag==false||initflag=='false'){
				alert("亲，请现场抽奖后再查看现场中奖记录。");
				return false;
			}
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:true,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/currentprizepage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.historyprize').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1200,
		    	height: 550,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/historyprizepage',
			    title:''
			});
			$.dialog.data('dialog',dialog);
		});
		$('.setting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 950,
		    	height: 900,
		    	top: '1%',
			    min:false,
			    max:false,
			    cancel:false,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/initpage',
			    title:'',
			    close:function(){
			    	location=location;
			    },
			    button:[{
					name: '保存',
					callback: function(){window.frames["dia"].saveData();return false;}
				},{
					name: '取消',
					callback: function(){this.close();},
				}]
			});
			$.dialog.data('dialog',dialog);
		});
		$('.initUser').bind('click',function(){
			/*
			if(initflag==false||initflag=='false'){
				alert('亲，您还没有开始一次抽奖!');
				return false;
			}*/
			if(confirm('是否确定要重置人员状态')){
				$.ajax({
						url:'${ctx}/index/inituserstatus',
						cache:false,
						success:function(obj){
							alert('抽奖人员状态重置完毕！');
							location=location;
						}
				});
			}
		});
	});
	</script>
  </body>
</html>