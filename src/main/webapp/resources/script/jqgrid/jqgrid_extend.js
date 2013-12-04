$(function(){
	var tempPostUrlObj={};
	$.extend($.jgrid.del, {
	    mtype: "DELETE",
	    serializeDelData: function () {
	        return ""; // don't send and body for the HTTP DELETE
	    },
	   onclickSubmit: function (params, postdata) {
	        params.url += '?param=' + encodeURIComponent(postdata);
	    }
	});
	$.extend($.jgrid.edit, {
	   onclickSubmit: function (params, postdata) {
	    	var dataId = this.id+'_id';
	    	if(postdata[dataId]!='_empty'){
	    		if(tempPostUrlObj[dataId]!=null&&tempPostUrlObj[dataId]!=''&&tempPostUrlObj[dataId]!=''){
	    			params.url = tempPostUrlObj[dataId]+'/' + encodeURIComponent(postdata[dataId]);
	    		}else{
		    		tempPostUrlObj[dataId]=params.url;
		    		params.url += '/' + encodeURIComponent(postdata[dataId]);
	    		}
	    	}
	    }
	});
});