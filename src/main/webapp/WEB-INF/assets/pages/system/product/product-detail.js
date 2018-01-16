(function() {
    'use strict';

    var handleDemo = function() {
    	var str = location.href
    	var prodId = str.substring(str.lastIndexOf("/") + 1);
    	
    	var elt = $('#object_tagsinput');
    	
    	elt.tagsinput({
          itemValue: 'value',
          itemText: 'text',
        });

        $('#object_tagsinput_add').on('click', function(){
        	
        	var labelValue = $('#labelValue').val();
        	if($('#object_tagsinput').val().indexOf(labelValue) < 0){
        		if (labelValue != null && labelValue != '') {
        			elt.tagsinput('add', { 
        				"value": $('#labelValue').val(), 
        				"text": $('#labelValue').find("option:selected").text() 
        			});
        		}
        	}
            console.log($('#object_tagsinput').val());
        });

        $.get(BASE_PATH+'product/getBusinessLabelByProdId/'+prodId, function(data){
    		if(data.status == 200){
    			for(var i=0; i<data.obj.length; i++){
    				elt.tagsinput('add', { "value": data.obj[i].labelId , "text": data.obj[i].labelName});
    			}
    		}
    	});
    }
    handleDemo();
    
})()
