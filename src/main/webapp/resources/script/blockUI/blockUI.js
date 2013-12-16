(function(){
	function getRandomNum(Min,Max){   
		var Range = Max - Min;   
		var Rand = Math.random();   
		return(Min + Math.round(Rand * Range));   
	}   
	function getRandomName(){
		var num = getRandomNum(100,1000000);
		return "blockUIOf"+num;
	}
	/**
	 * 判断元素是否为window、document、document
	 */
	function checkELType(container){
		if(container==window||container==document||container==document.body||container[0]==window||container[0]==document||container[0]==document.body){
			return false;
		}else{
			return true;
		}
	}
	function initBlockUIHtml(container){
		var randomName = getRandomName();
		var s = '<div class="transparent_class blockUI_class '+randomName+'" style="position:absolute;z-index:1000;background-color:rgb(220, 226, 241);cursor:wait;"></div>';
			s+='<div class="blockUI_progress '+randomName+'" style="position:absolute;z-index:1001;margin:0px;padding:0px;">';
			s+='</div>';
		$(document.body).append(s);
		if(checkELType(container)){
			$(container).attr('blockUIClassId',randomName);
		}
	}
	function initBlockUISize(container){
		var width = 0;
		var height = 0;
		var offsetTop = 0;
		var offsetLeft = 0;
		if(!checkELType(container)){
			width = $(window).width();
			height = $(window).height();
			$('.blockUI_class').css('top',0);
			$('.blockUI_class').css('left',0);
			$('.blockUI_class').css('padding',"0px");
			$('.blockUI_class').css('margin',"0px");
			$('.blockUI_class').css('width',"100%");
			$('.blockUI_class').css('height',"100%");
			$('.blockUI_progress').css('top',(height/2)+offsetTop);
			$('.blockUI_progress').css('left',(width/2)+offsetLeft);
		}else{
			width = $(container).width();
			height = $(container).height();
			offsetTop = $(container).offset().top;
			offsetLeft = $(container).offset().left;
			var margin = $(container).css("margin");
			var padding = $(container).css("padding");
			var blockUIClassId = $(container).attr('blockUIClassId');
			$('.blockUI_class.'+blockUIClassId).css('margin',margin);
			$('.blockUI_class.'+blockUIClassId).css('padding',padding);
			$('.blockUI_class.'+blockUIClassId).css('top',offsetTop);
			$('.blockUI_class.'+blockUIClassId).css('left',offsetLeft);
			$('.blockUI_class.'+blockUIClassId).css('width',width);
			$('.blockUI_class.'+blockUIClassId).css('height',height);
			$('.blockUI_progress.'+blockUIClassId).css('top',(height/2)+offsetTop);
			$('.blockUI_progress.'+blockUIClassId).css('left',(width/2)+offsetLeft);
		}
	}
	function removeBlockUI(container){
		if(!checkELType(container)){
			$('.blockUI_class').css('cursor','default');
			$('.blockUI_class').remove();
			$('.blockUI_progress').remove();
		}else{
			var blockUIClassId = $(container).attr('blockUIClassId');
			$('.'+blockUIClassId).remove();
		}
	}
	$.fn.blockUI = function(options, param){
		var container = this;
		//如果options是字符串，则说明是调用方法
		if (typeof options == 'string') {
			return $.fn.blockUI.methods[options](container, param);
		}
		options=$.extend({},$.fn.blockUI.defaults,options || {});
		//$.fn.blockUI.methods['init'](container, param);
		return container.each( function(i,v){$.fn.blockUI.methods['init'](v, param);});
		//return this.each( function() {});
	}
	/*
	*ruleEditor对象的公共方法
	*/
	$.fn.blockUI.methods={
		init:function(container,param){
			initBlockUIHtml(container);
			initBlockUISize(container);
		},
		remove:function(container){
			removeBlockUI(container);
		},
		test:function(container,param){
			alert(param);
		}
	};
	/**
	*初始化参数
	*/
	$.fn.blockUI.defaults = {
	};
})($);