/**
 * 滚动球
 */
(function($){
	 var radius = 410;
	 var d = 800;
	 var dtr = Math.PI / 180;
	 var mcList = [];
	 var lasta = 1;
	 var lastb = 1;
	 var distr = true;
	 var tspeed = 20;
	 var size = 200;
	 var mouseX = 0;
	 var mouseY = 10;
	 var howElliptical = 1;
	 var aA = null;
	 var oDiv = null;
	 function test(){
	 	d=700;
	 	tspeed=300;
	 }
	 function test2(){
	 	tspeed=0;
	 }
	 function update()
		{
			var a, b, c = 0;
		        a = (Math.min(Math.max(-mouseY, -size), size) / radius) * tspeed;
		        b = (-Math.min(Math.max(-mouseX, -size), size) / radius) * tspeed;
		        lasta = a;
		        lastb = b;
		        if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
		            return;
		        }
		        sineCosine(a, b, c);
		        for (var i = 0; i < mcList.length; i++) {
		            if (mcList[i].on) {
		                continue;
		            }
		            var rx1 = mcList[i].cx;
		            var ry1 = mcList[i].cy * ca + mcList[i].cz * (-sa);
		            var rz1 = mcList[i].cy * sa + mcList[i].cz * ca;

		            var rx2 = rx1 * cb + rz1 * sb;
		            var ry2 = ry1;
		            var rz2 = rx1 * (-sb) + rz1 * cb;

		            var rx3 = rx2 * cc + ry2 * (-sc);
		            var ry3 = rx2 * sc + ry2 * cc;
		            var rz3 = rz2;

		            mcList[i].cx = rx3;
		            mcList[i].cy = ry3;
		            mcList[i].cz = rz3;

		            per = d / (d + rz3);

		            mcList[i].x = (howElliptical * rx3 * per) - (howElliptical * 2);
		            mcList[i].y = ry3 * per;
		            mcList[i].scale = per;
		            var alpha = per;
		            alpha = (alpha - 0.6) * (10 / 6);
		            mcList[i].alpha = alpha * alpha * alpha - 0.2;
		            mcList[i].zIndex = Math.ceil(100 - Math.floor(mcList[i].cz));
		        }
		        doPosition();
		}
		function positionAll()
		{
			var phi = 0;
		    var theta = 0;
		    var max = mcList.length;
		    for (var i = 0; i < max; i++) {
		        if (distr) {
		            phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
		            theta = Math.sqrt(max * Math.PI) * phi;
		        } else {
		            phi = Math.random() * (Math.PI);
		            theta = Math.random() * (2 * Math.PI);
		        }
		        //坐标变换
		        mcList[i].cx = radius * Math.cos(theta) * Math.sin(phi);
		        mcList[i].cy = radius * Math.sin(theta) * Math.sin(phi);
		        mcList[i].cz = radius * Math.cos(phi);

		        aA[i].style.left = mcList[i].cx + oDiv.offsetWidth / 2 - mcList[i].offsetWidth / 2 + 'px';
		        aA[i].style.top = mcList[i].cy + oDiv.offsetHeight / 2 - mcList[i].offsetHeight / 2 + 'px';
		    }
		}
		function doPosition()
		{
			var l = oDiv.offsetWidth / 2;
		        var t = oDiv.offsetHeight / 2;
		        for (var i = 0; i < mcList.length; i++) {
		            if (mcList[i].on) {
		                continue;
		            }
		            var aAs = aA[i].style;
		            if (mcList[i].alpha > 0.1) {
		                if (aAs.display != '')
		                    aAs.display = '';
		            } else {
		                if (aAs.display != 'none')
		                    aAs.display = 'none';
		                continue;
		            }
		            aAs.left = mcList[i].cx + l - mcList[i].offsetWidth / 2 + 'px';
		            aAs.top = mcList[i].cy + t - mcList[i].offsetHeight / 2 + 'px';
		            //aAs.fontSize=Math.ceil(12*mcList[i].scale/2)+8+'px';
		            //aAs.filter="progid:DXImageTransform.Microsoft.Alpha(opacity="+100*mcList[i].alpha+")";
		            aAs.filter = "alpha(opacity=" + 100 * mcList[i].alpha + ")";
		            aAs.zIndex = mcList[i].zIndex;
		            aAs.opacity = mcList[i].alpha;
		        }
		}
		function sineCosine( a, b, c)
		{
			sa = Math.sin(a * dtr);
		    ca = Math.cos(a * dtr);
		    sb = Math.sin(b * dtr);
		    cb = Math.cos(b * dtr);
			sc = Math.sin(c * dtr);
			cc = Math.cos(c * dtr);
		}
		function createATag(arr,start,end){
			if(arr!=null&&arr.length>0){
				for(var i=start;i<end&&i<arr.length;i++){
					$("#tagscloud").append('<a href="javascript:void(0);" class="tagc'+(i%3+1)+'">'+arr[i]+'</a>');
				}
			}
		}
	$.fn.lottery = function(arr,param){
		if(typeof arr =="string"){
			return $.fn.lottery.methods[arr](param);
		}
		oDiv=document.getElementById('tagscloud');
		//totalaA=oDiv.getElementsByTagName('a');
		var userlistflag = 0;
		var maxuser=600;
		var userlistpernum = arr.length>maxuser?maxuser:arr.length;
		var setTimeoutTime=40;
		var perLoopTime=30000;
		var looptimes=perLoopTime/setTimeoutTime;
		var count=0;
		
		createATag(arr,0,userlistpernum);
		aA=oDiv.getElementsByTagName('a');
		var oTag=null;
		for(var i=0;i<aA.length;i++){
			oTag={};		
			aA[i].onmouseover = (function (obj) {
				return function () {
					obj.on = true;
					this.style.zIndex = 9999;
					this.style.color = '#fff';
					this.style.padding = '5px 5px';
					this.style.filter = "alpha(opacity=100)";
					this.style.opacity = 1;
				}
			})(oTag);
			aA[i].onmouseout = (function (obj) {
				return function () {
					obj.on = false;
					this.style.zIndex = obj.zIndex;
					this.style.color = '#fff';
					this.style.padding = '5px';
					this.style.filter = "alpha(opacity=" + 100 * obj.alpha + ")";
					this.style.opacity = obj.alpha;
					this.style.zIndex = obj.zIndex;
				}
			})(oTag);
			oTag.offsetWidth = aA[i].offsetWidth;
			oTag.offsetHeight = aA[i].offsetHeight;
			mcList.push(oTag);
		}
		sineCosine( 0,0,0 );
		positionAll();
		(function () {
				update();
				if(count>=looptimes){
					var start = userlistflag*userlistpernum;
					for(var i=0;i<aA.length;i++){
						if(start+i<arr.length){
							aA.item(i).innerHTML=arr[start+i];
						}else{
							start=0;
							aA.item(i).innerHTML=arr[start];
						}
					}
					if(start>=arr.length-1){
						userlistflag=0;
					}else{
						userlistflag++;
					}
					count=0;
				}
				setTimeout(arguments.callee, setTimeoutTime);
				count++;
	    })();
	}
	
	$.fn.lottery.methods={
			start:test,
			stop:test2,
			init:function(){
				 radius = 355;
				 d = 800;
				 dtr = Math.PI / 180;
				 mcList = [];
				 lasta = 1;
				 lastb = 1;
				 distr = true;
				 tspeed = 15;
				 size = 200;
				 mouseX = 0;
				 mouseY = 10;
				 howElliptical = 1;
				 aA = null;
				 oDiv = null;
			},
			run:function(){
				test();
			}
	};
})($);
/*

window.onload=function ()
{
	var i=0;
	var oTag=null;
	oDiv=document.getElementById('tagscloud');
	aA=oDiv.getElementsByTagName('a');
	for(i=0;i<aA.length;i++)
	{
		oTag={};		
		aA[i].onmouseover = (function (obj) {
			return function () {
				obj.on = true;
				this.style.zIndex = 9999;
				this.style.color = '#fff';
				this.style.padding = '5px 5px';
				this.style.filter = "alpha(opacity=100)";
				this.style.opacity = 1;
			}
		})(oTag)
		aA[i].onmouseout = (function (obj) {
			return function () {
				obj.on = false;
				this.style.zIndex = obj.zIndex;
				this.style.color = '#fff';
				this.style.padding = '5px';
				this.style.filter = "alpha(opacity=" + 100 * obj.alpha + ")";
				this.style.opacity = obj.alpha;
				this.style.zIndex = obj.zIndex;
			}
		})(oTag)
		oTag.offsetWidth = aA[i].offsetWidth;
		oTag.offsetHeight = aA[i].offsetHeight;
		mcList.push(oTag);
	}
	sineCosine( 0,0,0 );
	positionAll();
	(function () {
            update();
            setTimeout(arguments.callee, 40);
        })();
};
function update()
{
	var a, b, c = 0;
        a = (Math.min(Math.max(-mouseY, -size), size) / radius) * tspeed;
        b = (-Math.min(Math.max(-mouseX, -size), size) / radius) * tspeed;
        lasta = a;
        lastb = b;
        if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
            return;
        }
        sineCosine(a, b, c);
        for (var i = 0; i < mcList.length; i++) {
            if (mcList[i].on) {
                continue;
            }
            var rx1 = mcList[i].cx;
            var ry1 = mcList[i].cy * ca + mcList[i].cz * (-sa);
            var rz1 = mcList[i].cy * sa + mcList[i].cz * ca;

            var rx2 = rx1 * cb + rz1 * sb;
            var ry2 = ry1;
            var rz2 = rx1 * (-sb) + rz1 * cb;

            var rx3 = rx2 * cc + ry2 * (-sc);
            var ry3 = rx2 * sc + ry2 * cc;
            var rz3 = rz2;

            mcList[i].cx = rx3;
            mcList[i].cy = ry3;
            mcList[i].cz = rz3;

            per = d / (d + rz3);

            mcList[i].x = (howElliptical * rx3 * per) - (howElliptical * 2);
            mcList[i].y = ry3 * per;
            mcList[i].scale = per;
            var alpha = per;
            alpha = (alpha - 0.6) * (10 / 6);
            mcList[i].alpha = alpha * alpha * alpha - 0.2;
            mcList[i].zIndex = Math.ceil(100 - Math.floor(mcList[i].cz));
        }
        doPosition();
}
function positionAll()
{
	var phi = 0;
    var theta = 0;
    var max = mcList.length;
    for (var i = 0; i < max; i++) {
        if (distr) {
            phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
            theta = Math.sqrt(max * Math.PI) * phi;
        } else {
            phi = Math.random() * (Math.PI);
            theta = Math.random() * (2 * Math.PI);
        }
        //坐标变换
        mcList[i].cx = radius * Math.cos(theta) * Math.sin(phi);
        mcList[i].cy = radius * Math.sin(theta) * Math.sin(phi);
        mcList[i].cz = radius * Math.cos(phi);

        aA[i].style.left = mcList[i].cx + oDiv.offsetWidth / 2 - mcList[i].offsetWidth / 2 + 'px';
        aA[i].style.top = mcList[i].cy + oDiv.offsetHeight / 2 - mcList[i].offsetHeight / 2 + 'px';
    }
}
function doPosition()
{
	var l = oDiv.offsetWidth / 2;
        var t = oDiv.offsetHeight / 2;
        for (var i = 0; i < mcList.length; i++) {
            if (mcList[i].on) {
                continue;
            }
            var aAs = aA[i].style;
            if (mcList[i].alpha > 0.1) {
                if (aAs.display != '')
                    aAs.display = '';
            } else {
                if (aAs.display != 'none')
                    aAs.display = 'none';
                continue;
            }
            aAs.left = mcList[i].cx + l - mcList[i].offsetWidth / 2 + 'px';
            aAs.top = mcList[i].cy + t - mcList[i].offsetHeight / 2 + 'px';
            //aAs.fontSize=Math.ceil(12*mcList[i].scale/2)+8+'px';
            //aAs.filter="progid:DXImageTransform.Microsoft.Alpha(opacity="+100*mcList[i].alpha+")";
            aAs.filter = "alpha(opacity=" + 100 * mcList[i].alpha + ")";
            aAs.zIndex = mcList[i].zIndex;
            aAs.opacity = mcList[i].alpha;
        }
}
function sineCosine( a, b, c)
{
	sa = Math.sin(a * dtr);
    ca = Math.cos(a * dtr);
    sb = Math.sin(b * dtr);
    cb = Math.cos(b * dtr);
	sc = Math.sin(c * dtr);
	cc = Math.cos(c * dtr);
}
*/

/**
 * 设置菜单
 */
/*
var timeout         = 100;
var closetimer		= 0;
var ddmenuitem      = 0;

function jsddm_open()
{	jsddm_canceltimer();
	jsddm_close();
	ddmenuitem = $(this).find('ul').eq(0).css('visibility', 'visible');
	$('.gn_tips').css('visibility', 'hidden');
	
	}

function jsddm_close()
{	if(ddmenuitem) ddmenuitem.css('visibility', 'hidden');
	$('.gn_tips').css('visibility', 'visible');
}

function jsddm_timer()
{	closetimer = window.setTimeout(jsddm_close, timeout);}

function jsddm_canceltimer()
{	if(closetimer)
	{	window.clearTimeout(closetimer);
		closetimer = null;}}

$(document).ready(function()
{	$('#jsddm > li').bind('mouseover', jsddm_open);
	$('#jsddm > li').bind('mouseout',  jsddm_timer);});

document.onclick = jsddm_close;
*/





