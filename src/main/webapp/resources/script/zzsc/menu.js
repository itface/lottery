(function($){
	var timeout         = 100;
	var closetimer		= null;
	var ddmenuitem      = null;
	function jsddm_open(){	
		jsddm_close();
		ddmenuitem = $(this).find('ul').eq(0).css('visibility', 'visible');
	}

	function jsddm_close(){	
		if(ddmenuitem) 
			ddmenuitem.css('visibility', 'hidden');
	}

	function jsddm_timer(){	
		closetimer = window.setTimeout(jsddm_close, timeout);
	}
	$.fn.easymenu = function(){
		$('li',this).bind('mouseover',jsddm_open);
		$('li',this).bind('mouseout',jsddm_close);
		//$(document).bind('click',jsddm_close);
		//$('#jsddm > li').bind('mouseover', jsddm_open);
	}
	$.fn.easymenu.defaults={
	};
})($);