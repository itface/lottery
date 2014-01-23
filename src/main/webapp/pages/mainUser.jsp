<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="inc/header.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/blockUI/blockUI.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/script/zzsc/zzsc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/table/table_userprizeofuser.css'/>">
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
<div class="div_left" style="">

</div>


<div class="div_right">
  <div class="select">
    <form:select id="prizelist" class="select1"  name="prizelist" path="prizelist"  items="${prizelist}" itemValue="id" itemLabel="prizelistlabel"/>
  	
  </div>
  <div class="prizeclass jingpin" style=""></div>
  <div class="actionend btn2" style="visibility:hidden;z-index:888;position: relative;"><a href="javascript:void(0);" ></a></div>
  <div class="actionstart btn1" style='z-index:888;position: relative;visibility:${actionstartbtnshow}'><a href="javascript:void(0);" ></a></div>
  <div id="tagscloud"> 
  		
  </div>
</div>
	<script>
	var lotterystatus="${lotterystatus}";
	$(document).ready(function(){
		var initflag = ${initflag};
		var prizelistjson = ${prizelistjson};
		var prizeid = ${prizeid};
		var userlist = '${userlist}';
		var bgmusic='${bgmusic}';
		var startmusic='${startmusic}';
		var stopmusic='${stopmusic}';
		init();
		function init(){
			window.onload=setPrizelist;
			$('#jsddm').easymenu();
			initLottery();
			setPrizeuser();
			setMusic(bgmusic);
		}
		function initLottery(){
			if(userlist!=null&&userlist!=""&&userlist!="null"&&userlist!="[]"){
				var arr = (userlist.replace("[","").replace("]","")).split(",");
				$(document).lottery(arr);
			}else{
				$(document).lottery(new Array());
			}
		}
		function setPrizeuser(){
			$.ajax({
				url:'${ctx}/index/prizeuserlist?prizeid='+prizeid,
				async:false,
				cache:false,
				success:function(obj){
					if(obj!=null&&obj.length>0){
						var tempindex = 0;
						var s = "";
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
				                s+='<li><a href="javascript:void(0);" class="prizeindex" style="cursor: default;font-size:20px;font-size: 28px;color:yellow;">'+v.prizename+'&nbsp;第'+v.sameprizeindexorder+'次</a></li>';
							}
							s+='<li><a href="javascript:void(0);" style="cursor: default;"><span style="font-size:28px;font-weight:bold">'+v.username+'</span><span style="font-size:22px">&nbsp;'+v.usernumber+'</span><span style="font-size:22px">&nbsp;'+v.region+'</span><span style="font-size:22px;">&nbsp;'+v.ywdy+'</span></a></li>';
						});
						$('.div_left').html(s);
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
				/*
				var s = "<audio src='${ctx}/resources/music/"+musicname+"' loop  autoplay></audio>";
				$('.musicplayer').empty();
				 $('.musicplayer').show();
				$('.musicplayer').append(s);
				if ( $.browser.mozilla)
		 				audiojs.createAll();
				 $('.musicplayer').hide();
				 */
			}
			//var audio = a[0];
			 //audio.playPause();
			 
			/*
			var a = audiojs.createAll();
			var audio = a[0];
			 audio.playPause();
			 */
			
		}
		/*
		$(".btn2").click(function(){
			 //$('#tagscloud').hide(),
			  setUserlistFlag="stop";
			  $('.wr_table').fadeIn(1000);
		}),
		 $(".btn1").click(function(){
			 //$('#tagscloud').show(),
			 setUserlistFlag="run";
			 $('.wr_table').hide()
		})
		*/
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
			 $('.ui_title_bar').hide();
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
			 $('.ui_title_bar').hide();
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
							location='${ctx}/index';
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
				$(prizelistjson).each(function(i,v){
					if(v['id']==id){
						$('.prizeclass').html(v['prizename']);
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
			 $('.ui_title_bar').hide();
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
			    width: 900,
		    	height: 750,
			    min:false,
			    max:true,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/currentprizepage',
			    title:''
			});
			 $('.ui_title_bar').hide();
			$.dialog.data('dialog',dialog);
		});
		$('.historyprize').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 900,
				height: 750,
			    min:false,
			    max:false,
			    cancel:true,
			    background: '#FFF',
			    opacity: 0.5,
			    content: 'url:${ctx}/index/historyprizepage',
			    title:''
			});
			 $('.ui_title_bar').hide();
			$.dialog.data('dialog',dialog);
		});
		$('.setting').bind('click',function(){
			var dialog = $.dialog({
		 		id:'dia',
			    lock: true,
			    width: 1050,
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
			 $('.ui_title_bar').hide();
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
		$(document).bind('keypress',function(e){
			//空格键开始，回车键结束
			if(lotterystatus=='init'&&e.which==32){
				$('.actionstart').trigger('click');
				return;
			}
			if(lotterystatus=='run'&&e.which==13){
				$('.actionend').trigger('click');
				return;
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
			$(document).lottery("start");
			setMusic(startmusic);
			$('.actionstart').css('visibility','hidden');
			$('.actionend').css('visibility','visible');
			lotterystatus='run';
			/*
			var prizenum = $('#prizenum').html();
			if(prizenum!=null&&prizenum!=''&&parseInt(prizenum)>0){
				$.ajax({
					url:'${ctx}/index/checkuser?prizelength='+prizenum,
					async:false,
					cache:false,
					success:function(obj){
						$(document).lottery("start");
						setUserlistFlag="run";
						setMusic(startmusic);
						$('.actionstart').css('visibility','hidden');
						$('.actionend').css('visibility','visible');
						//$('.userList').empty();
						//$('.userListTable').hide();
						$('.img').hide();
					}
				});
			}else{
				alert("奖项的中奖名额必须大于0。");
				return;
			}*/
		});
		$('.actionend').bind('click',function(){
			$('.actionstart').css('visibility','visible');
			$('.actionend').css('visibility','hidden');
			//$('.userListTable').show();
			$.ajax({
				url:'${ctx}/index/action/'+$('#prizelist').val(),
				async:false,
				cache:false,
				success:function(obj){
					var s = "<div class='wr_table userListTable' style='margin-top: 40px;'><table class='userList' border='0' cellspacing='0' cellpadding='0' width='100%'>";
					$(document).lottery("init");
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
						    width: 1300,
					    	height: 500,
						    min:false,
						    max:false,
						    cancel:true,
						    background: '#FFF',
						    opacity: 0,
						    content: s,
						    title:'获奖名单',
						    close:function(){
						    	location=location;
						    }
						});
						 $('.ui_title_bar').hide();
					}else{
						alert("抱歉，没有抽到符合要求的奖，请检查奖项设置是否正确,或者所抽的奖项是否已经抽完。");
					}
					//setPrizeuser();
					lotterystatus='stop';
					setMusic(stopmusic);
				}
			});
		});
	});
	</script>
  </body>
</html>