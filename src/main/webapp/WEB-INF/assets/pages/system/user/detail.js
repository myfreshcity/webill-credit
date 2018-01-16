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
            	realName: {
                    required: true
                },
                idNo:{
                	required:true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
            	realName: {
                    required: "真实姓名不能为空！"
                },
                idNo: {
                    required: "身份证号不能为空！",
                    minlength: jQuery.validator.format("18")
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
     
    /*提交订单信息*/
    function save() {
        	$("input[name='pValue']").each(function() {
        		var pValue =$(this).val();
            	var detailId =$(this).prev("input[name='detailId']").val();
            	$(this).next("input[name='prmValueStr']").val(detailId+"|"+pValue);
    		});
        $.ajax({
            type: 'POST',
            url: BASE_PATH+'user/saveDetail',
            data: form3.serialize(),
            dataType: 'json',
            success: function (data) {
                console.debug(data.msg);
                if (data.success) {
                    //window.location.href = BASE_PATH+'staff/edit/'+data.msg;
                    if (data.success) {
                    /*	Orgnization.tagOrgization().then(function(data){
                          $('#form_sample_3 #orgId').select({
                              data: Orgnization.getOragnization()
                          });
                      });*/
                        App.alert({
                            type: 'danger',
                            icon: 'warning',
                            message: '保存成功',
                            container: $('#form_sample_3'),
                            place: 'prepend'
                        });
                    }
                    
                }else{
	                App.alert({
	                    type: 'danger',
	                    icon: 'warning',
	                    message:data.msg,
	                    container: $('#form_sample_3'),
	                    place: 'prepend'
	                });
                }
            },
            error: function () {
                alert("出错了,请重试!");
            }
        });
    }

})()