(function() {
    'use strict';

        var form3 = $('#form_sample_3');
        var error3 = $('.alert-danger', form3);
        var success3 = $('.alert-success', form3);

        form3.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
            	cpName: {
                    required: true
                },
                cpDesc: {
                	required: true
                },
                ruleName: {
                    required: true
                },
                cpValidDay: {
                	digits:true,
                	required: true
                },
                cpEndTime: {
                	required: true
                },
                planAmt: {
                	digits:true,
                	required: true
                },
                sendStartTimeStr: {
                	required: true
                },
                sendEndTimeStr: {
                	required: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
            	cpName: {
                    required: "优惠券名称不能为空！"
                },
                cpDesc: {
                    required: "优惠券说明不能为空！"
                },
                ruleName: {
                	required: "规则名称不能为空！"
                },
                cpValidDay: {
                	required: "有效天数不能为空！"
                },
                planAmt: {
                	required: "发放数量不能为空！"
                },
                sendStartTimeStr: {
                	required: "发放开始时间不能为空！"
                },
                sendEndTimeStr: {
                	required: "发放结束时间不能为空！"
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
                save();
            }

        });
        $('#form_sample_3 .form-actions').on('click', '#cancel', function (e) {
            e.preventDefault();
            window.location.href = BASE_PATH+'coupon/';
        });
        //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
        $('.select2me', form3).change(function () {
            form3.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });
        $('.select3me', form3).change(function () {
            form3.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });
     
	function save() {
	    	$.ajax({
	    		type: 'POST',
	    		url: BASE_PATH+'coupon/saveOrGet',
	    		data: form3.serialize(),
	    		dataType: 'json',
	    		success: function (data) {
	    			if(data.status==200){
	    				alert(data.msg);
	    				location.reload();
	    			}else{
	    				alert("提交失败！");
	    			}
	    		},
	    		error: function () {
	    			alert("错误");
	    		}
	    	});
	}
})()

