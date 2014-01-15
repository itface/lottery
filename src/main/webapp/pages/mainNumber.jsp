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
/*basic reset*/
* {
	margin: 0;
	padding: 0;
}
body {
	background: black;
}
canvas {
	display: block;
}
.logo{ background:url(${ctx}/resources/images/logo.png) no-repeat; width:181px; height:68px; position:absolute; top:20px; left:20px;}
.left {
	position:absolute;
	top:120px;
	left:20px;
	width:470px;
	background:#000;
	font-size:30px;
	line-height:34px;
	color:#0F0;
	padding:20px;
}
.left li {
	list-style:none
}
.left li a {
	color:#0f0;
	text-decoration:none;
	float:left;
	padding:0px 10px
}
</style>
</head>
<body>
<canvas id="c"></canvas>
<div style="display:block" id="musicplayer"></div>
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
<div class="left">
</div>
<div style="position:absolute; top:100px; right:50px; ">
  <div class="actionend btn2"  style="visibility:hidden;"><a href="javascript:void(0);"></a></div>
  <div class="actionstart btn1" ><a href="javascript:void(0);"></a></div>
</div>
<div class="div_right">
  <div class="select">
    <form:select id="prizelist" class="select1"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizelistlabel"/>
  </div>
  <div class="prizeclass jingpin" style=""></div>
</div>
	<script>
	
	$(document).ready(function(){
		var initflag = ${initflag};
		var prizelistjson = ${prizelistjson};
		var prizeid = ${prizeid};
		var bgmusic='${bgmusic}';
		var startmusic='${startmusic}';
		var stopmusic='${stopmusic}';
		//效果所用到的参数
		var drops = [];
		var font_size = 20;
		var chinese = "348034823482304832048470147014714017428239452393274369147104734724237413470".split("");
		init();
		function init(){
			window.onload=setPrizelist;//设置当前奖项的名称
			initdrawnum();
			setInterval(dodrawnum, 90);
			$('#jsddm').easymenu();
			setPrizeuser();//设置获奖名单
			setMusic(bgmusic);
		}
		function initdrawnum(){
			var c = document.getElementById("c");
			var ctx = c.getContext("2d");
			c.height = window.innerHeight;
			c.width = window.innerWidth;
			
			
			var columns = c.width / font_size; //number of columns for the rain
			for (var x = 0; x < columns; x++)
				drops[x] = 1;
		}
		function dodrawnum() {
			var c = document.getElementById("c");
			var ctx = c.getContext("2d");
			ctx.fillStyle = "rgba(0, 0, 0, 0.05)";
			ctx.fillRect(0, 0, c.width, c.height);
			ctx.fillStyle = "#0F0"; 
			ctx.font = font_size + "px arial";
			for (var i = 0; i < drops.length; i++) {	
				var text = chinese[Math.floor(Math.random() * chinese.length)];		
				ctx.fillText(text, i * font_size, drops[i] * font_size);
				if (drops[i] * font_size > c.height && Math.random() > 0.975)
					drops[i] = 0;
				drops[i]++;
			}
		}
		function speed1(){
			setInterval(dodrawnum, 3);
		}
		function setPrizeuser(){
			$.ajax({
				url:'${ctx}/index/prizeuserlist?type=num',
				async:false,
				cache:false,
				success:function(obj){
					if(obj!=null&&obj.length>0){
						var tempindex = 0;
						var s = "";
						var suffixnums={};
						$(obj).each(function(i,v){
							if(v.indexorder!=tempindex){
								tempindex=v.indexorder;
								//s+='<li><a href="javascript:void(0);" style="cursor: default;">&nbsp;</a></li>';
								if(i!=0){
									s+='<li>&nbsp;</li>';
								}else{
									//设置奖项为上一次抽过的奖项
									//$('#prizelist').val(v.prizeid);
									//setPrizelist();
								}
				                s+='<li><a href="javascript:void(0);"  style="cursor: default;clear:both; width:400px;"><span style="display:inline-block; ">'+v.prizename+'第'+v.indexorder+'次</span></a></li>';
							}
							if(v.prizetype=='尾号'){
								var suffixnum = v.uid.substring(v.uid.length-1);
								if(suffixnums[suffixnum]!=suffixnum){
									suffixnums[suffixnum]=suffixnum;
									s+='<li><a href="javascript:void(0);" style="cursor: default;">'+suffixnum+'</a></li>';
								}
							}else{
								s+='<li><a href="javascript:void(0);" style="cursor: default;">'+v.username+'</a></li>';
							}
						});
						$('.left').html(s);
					}
				}
			});
		}
		function setMusic(musicname){
			if($.browser.msie){
				if(document.getElementById('sound')){
					document.getElementById('sound').loop=0;
					document.getElementById('sound').src="${ctx}/resources/music/"+musicname;
					document.getElementById('sound').loop=-1;
				}else{
					var  sound=document.createElement("bgsound");
			         sound.id="sound";
			         document.body.appendChild(sound); 
			         sound.src="${ctx}/resources/music/"+musicname;
			         sound.loop=-1;
				}
			}else{
				if(document.getElementById('sound')){
					//document.getElementById('sound').loop=0;
					document.getElementById('sound').src="${ctx}/resources/music/"+musicname;
					//document.getElementById('sound').loop=-1;
				}else{
					var  sound=document.createElement("audio");
			         sound.id="sound";
			         document.getElementById('musicplayer').appendChild(sound); 
			         sound.src="${ctx}/resources/music/"+musicname;
			         sound.loop='loop';
			         sound.autoplay='autoplay';
				}
			}
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
		function setPrizelist(){
			//$('.userList').empty();
			//$('.userListTable').hide();
			var id = prizeid;
			$("#prizelist").val(id);
			if(prizelistjson!=null&&prizelistjson!=''&&prizelistjson!=[]){
				$('.img').show();
				$(prizelistjson).each(function(i,v){
					if(v['id']==id){
						/*
						var jppic = v['jppic'];
						if(jppic!=''){
							$('.img').attr('src','${ctx}/resources/prizepic/'+jppic);
						}else{
							$('.img').attr('src','${ctx}/resources/prizepic/default.png');
						}*/
						$('.prizeclass').html(v['prizename']);
						//$('.prizeclass').html(v['prizename']+'中奖：<span id="prizenum">'+v['prizenum']+"</span>人<br>奖品："+v['jp']);
					}
				});
			}
		}
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
		$('.actionstart').bind('click',function(){
			/*
			if(initflag==false||initflag=='false'){
				alert('亲，请到【初始化设置】里设置奖项和人员!');
				return false;
			}*/
			var prize = $('#prizelist').val();
			if(prize==null||prize==''||prize==undefined){
				alert('请选择要抽的奖项。');
				return false;
			}
			speed1();
			setMusic(startmusic);
			$('.actionstart').css('visibility','hidden');
			$('.actionend').css('visibility','visible');
		});
		$('.actionend').bind('click',function(){
			$('.actionstart').css('visibility','visible');
			$('.actionend').css('visibility','hidden');
			$.ajax({
				url:'${ctx}/index/action/'+$('#prizelist').val(),
				async:false,
				cache:false,
				success:function(obj){
					var s = "<div class='wr_table userListTable'><table class='userList' border='0' cellspacing='0' cellpadding='0' width='100%'>";
					if(obj!=null&&obj.length>0){
						$(obj).each(function(i,v){
							if(i==0){
									s += "<tr class='wr-table-hd-inner'><td colspan='6'>"+v.prizename+"——"+v.indexordername+"</td></tr>";
									s += '<tr class="wr-table-hd-inner">';
									s += '<td width="50px">序号</td>';
									s += '<td width="100px">姓名</td>';
									s += '<td width="100px">员工编号</td>';
									s += '<td width="100px">地域</td>';
									s += '<td width="150px">业务单元</td>';
									
									s += '</tr>';
							}
							s += '<tr class="wr-table-td-inner wr-table-tr-row">';
							s += '<td>'+(i+1)+'</td>';
							s += '<td>'+(v['username']==null?'':v['username'])+'</td>';
							s += '<td>'+(v['usernumber']==null?'':v['usernumber'])+'</td>';
							s += '<td>'+(v['region']==null?'':v['region'])+'</td>';
							s += '<td>'+(v['ywdy2']==null?'':v['ywdy'])+'</td>';
							s += '</tr>';
						});
						s+="</table></div>";
						//$('.userList').html(s);
						var dialog = $.dialog({
					 		id:'dia',
						    lock: true,
						    width: 900,
					    	height: 400,
						    min:false,
						    max:false,
						    cancel:true,
						    background: '#FFF',
						    opacity: 0.5,
						    content: s,
						    title:'获奖名单',
						    close:function(){
						    	location=location;
						    }
						});
					}else{
						alert("抱歉，没有抽到符合要求的奖，请检查奖项设置是否正确,或者所抽的奖项是否已经抽完。");
					}
					//setPrizeuser();
					setMusic(stopmusic);
				}
			});
		});
	});
	</script>
  </body>
</html>