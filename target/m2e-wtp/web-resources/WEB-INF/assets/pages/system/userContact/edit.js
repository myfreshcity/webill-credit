(function() {
    'use strict';

    // advance validation
    // for more info visit the official plugin documentation:
    // http://docs.jquery.com/Plugins/Validation

        var form3 = $('#form_sample_3');
        var error3 = $('.alert-danger', form3);
        var success3 = $('.alert-success', form3);

        form3.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
            	weixinNick: {
                    required: true
                },
                contactName: {
                    required: true
                },
                mobile: {
                	minlength: 11,
                	maxlength:11,
                    required: true,
                    digits:true
                },
                email: {
                	email:true
                },
                address:{
                	required: true
                }
                
            },

            messages: { // custom messages for radio buttons and checkboxes
            	weixinNick: {
                    required: "微信昵称不能为空！"
                },
                contactName: {
                    required: "收件人姓名不能为空！"
                },
                mobile:{
                	required: "手机号不能为空！",
                	minlength:"手机号位数不正确!",
                	maxlength:"手机号位数不正确!",
                	digits:"手机号必须为整数"
                },
                email: {
                	email:"邮箱格式不正确！",
                },
                address:{
                	required:"地址不能为空！"
                }
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) {
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) {
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) {
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) {
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success3.hide();
                error3.show();
                App.scrollTo(error3, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

            submitHandler: function (form) {
                error3.hide();
                alert(1);
                save();
            }

        });
        $('#form_sample_3 .form-actions').on('click', '#cancel', function (e) {
            e.preventDefault();
            window.location.href = BASE_PATH+'car/';
        });
        //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
        $('.select2me', form3).change(function () {
            form3.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });
        $('.select3me', form3).change(function () {
            form3.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        //initialize datepicker
        $('.date-picker').datepicker({
            rtl: App.isRTL(),
            autoclose: true
        });
        $('.date-picker .form-control').change(function() {
            form3.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        })
     

    function save() {
        	var province = $('#loc_province').select2('data').text;
        	var city =  $('#loc_city').select2('data').text ;
        	var area = $('#loc_town').select2('data').text;
        	
        	$("#province").val(province);
        	$("#city").val(city);
        	$("#area").val(area);
    	    $.ajax({
    	        type: 'POST',
    	        url: BASE_PATH+'userContact/addUserContact',
    	        data: form3.serialize(),
    	        dataType: 'json',
    	        success: function (data) {
    	        	if(data.status==200){
    	        		alert(data.msg);
    	        		window.location.href=BASE_PATH+"user/contactList/"+data.obj;
    	        	}
    	        },
    	        error: function () {
    	            alert(data.msg);
    	        }
    	    });
    }

})()

