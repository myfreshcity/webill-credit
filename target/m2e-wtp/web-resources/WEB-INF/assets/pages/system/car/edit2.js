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
            	licenseNo: {
                    minlength: 7,
                    required: true
                },
                carOwner: {
                    required: true
                },
                ownerPhone: {
                	required: true,
                	maxlength: 11,
                	minlength: 11,
                },
                frameNo: {
                	required: true
                },
                engineNo: {
                	required: true
                },
                brandName: {
                	required: true
                },
                seatCount: {
                	digits:true
                },
                purchasePrice: {
                	number:true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
            	licenseNo: {
                    required: "车牌号不能为空！"
                },
                carOwner: {
                    required: "车主姓名不能为空！",
                },
                ownerPhone: {
                	required: "车主号码不能为空！",
                	maxlength: "车主号码位数不正确！",
                	minlength: "车主号码位数不正确！",
                },
                frameNo: {
                	required: "车架号不能为空！",
                },
                engineNo: {
                	required: "发动机号不能为空！",
                },
                brandName: {
                	required: "品牌型号不能为空！",
                },
                seatCount: {
                	digits:"车座数必须为整数！",
                },
                purchasePrice: {
                	number:"车辆价格必须为数字！",
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

        })
        $('#form_sample_3 .form-actions').on('click', '#cancel', function (e) {
            e.preventDefault();
            window.location.href = BASE_PATH+'user/carList/';
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
        $.ajax({
            type: 'POST',
            url: BASE_PATH+'car/saveCarInfo',
            data: form3.serialize(),
            dataType: 'json',
            success: function (data) {
                if(data.status=="200"){
                	alert(data.msg);
                	window.location.href=BASE_PATH+"user/carList/"+data.obj.curUserId;
                }else{
                	alert(data.msg);
                }
            },
            error: function () {
                alert("出错了,请重试!");
            }
        });
    }

})()

