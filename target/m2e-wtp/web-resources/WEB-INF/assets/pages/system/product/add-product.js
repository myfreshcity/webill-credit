(function() {
    'use strict';

    var handleDemo = function() {
        var elt = $('#object_tagsinput');
        
        elt.tagsinput({
          itemValue: 'value',
          itemText: 'text',
        });

        $('#object_tagsinput_add').on('click', function(){
        	var labelValue = $('#labelValue').val();
        	if (labelValue != null && labelValue != '') {
        		elt.tagsinput('add', { 
        			"value": $('#labelValue').val(), 
        			"text": $('#labelValue').find("option:selected").text() 
        			//"continent": $('#object_tagsinput_continent').val()    
        		});
			}
            console.log($('#object_tagsinput').val());
        });

        /*elt.tagsinput('add', { "value": 1 , "text": "Amsterdam"   , "continent": "Europe"    });
        elt.tagsinput('add', { "value": 4 , "text": "Washington"  , "continent": "America"   });
        elt.tagsinput('add', { "value": 7 , "text": "Sydney"      , "continent": "Australia" });
        elt.tagsinput('add', { "value": 10, "text": "Beijing"     , "continent": "Asia"      });
        elt.tagsinput('add', { "value": 13, "text": "Cairo"       , "continent": "Africa"    });*/
    }
    handleDemo();
    
    var handleValidation = function() {
            var form1 = $('#addProduct');
            var error1 = $('.alert-danger', form1);
            var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                messages: {
                    select_multi: {
                        maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                        minlength: jQuery.validator.format("At least {0} items must be selected")
                    }
                },
                rules: {
                	labelId: {
                        required: true
                    },
                    secondCatId: {
                		required: true
                	}
                },
                messages: { // custom messages for radio buttons and checkboxes
                	labelId: {
                        required: "导航标签不能为空！"
                    },
                    secondCatId: {
	                	required: "商品分类不能为空！"
	                }
                },
                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success1.hide();
                    error1.show();
                    App.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
                },
                
                success: function (label) {
                    label.closest('.form-group').removeClass('has-error'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success1.show();
                    error1.hide();
                }
            });
    }
    handleValidation();
})()
